package com.ttn.bflframework.pages;

import com.ttn.bflframework.utils.*;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;

import javax.mail.MessagingException;
import java.io.IOException;

public class SignInPage {

    public UIUtils utils;
    public WaitUtils wUtils;
    public VerifyUtils vUtils;


    String usrDirectory= System.getProperty("user.dir");
    String filePath=usrDirectory+"\\src\\main\\java\\com\\ttn\\bflframework\\testdata";
    String fileName = GenericUtils.getDataFromConfig("FileName");
    String sheetName = "SignInPage";


    public SignInPage(UIUtils utils, WaitUtils wUtils, VerifyUtils vUtils) throws IOException {
        this.utils = utils;
        this.wUtils = wUtils;
        this.vUtils = vUtils;
    }

    private By emailTxt = By.xpath(ExcelUtils.getCellValue(filePath, fileName, sheetName, "emailTxt", "Locator"));
    private By pwdTxt = By.xpath(ExcelUtils.getCellValue(filePath, fileName, sheetName, "pwdTxt", "Locator"));
    private By forgotPasswordLink = By.xpath(ExcelUtils.getCellValue(filePath, fileName, sheetName, "forgotPasswordLink", "Locator"));
    private By signInBtn = By.xpath(ExcelUtils.getCellValue(filePath, fileName, sheetName, "signInBtn", "Locator"));
    private By fbLink = By.xpath(ExcelUtils.getCellValue(filePath, fileName, sheetName, "fbLink", "Locator"));
    private By gmailLink = By.xpath(ExcelUtils.getCellValue(filePath, fileName, sheetName, "gmailLink", "Locator"));
    private By closeBtn = By.xpath(ExcelUtils.getCellValue(filePath, fileName, sheetName, "closeBtn", "Locator"));
    private By submitBtn = By.xpath(ExcelUtils.getCellValue(filePath, fileName, sheetName, "submitBtn", "Locator"));


    public void enterUsermail(String userName) {
        wUtils.eWaitForElementVisible(emailTxt, 30);
        utils.Type(emailTxt, "Enter username", userName);
    }

    public void enterPassword(String password) {
        wUtils.eWaitForElementVisible(emailTxt, 30);
        utils.Type(pwdTxt, "Enter password", password);
    }

    public void clickSignInBtn() {
        wUtils.eWaitForElementVisible(emailTxt, 30);
        utils.click(signInBtn, "Click on Sign-In button");
        wUtils.checkForPageLoad(60);
    }

    public void clickForgotPassword() {
        wUtils.eWaitForElementVisible(forgotPasswordLink, 30);
        utils.click(forgotPasswordLink, "Click on forgot password button");
        wUtils.checkForPageLoad(60);
    }

    public void clickCloseBtn() {
        wUtils.eWaitForElementVisible(closeBtn, 30);
        utils.click(closeBtn, "Click on close button");
    }

    public void resetPassword()
    {
        String username = null;
        String password= null;
        String forgotPasswordLink;
        try {

            wUtils.iWaitForMinutes(2);
            forgotPasswordLink = GenericUtils.getLinkFromEmail();
            try {
              username=  GenericUtils.getDataFromConfig("securityUserName");
              password= GenericUtils.getDataFromConfig("securityPassword");
            } catch (IOException e) {
                e.printStackTrace();
            }
            utils.navigateTo(forgotPasswordLink);
            utils.verifyUrlExist("reset-password");


        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void clickSubmitBtn() {
        wUtils.eWaitForElementVisible(submitBtn, 30);
        utils.click(submitBtn, "Click on submit button");
    }

}


