package com.ttn.bflframework.pages;

import com.ttn.bflframework.utils.*;
import org.openqa.selenium.By;

import java.io.IOException;

public class ResetPasswordPage {

    public UIUtils utils;
    public WaitUtils wUtils;
    public VerifyUtils vUtils;


    String usrDirectory= System.getProperty("user.dir");
    String filePath=usrDirectory+"\\src\\main\\java\\com\\ttn\\bflframework\\testdata";
    String fileName = GenericUtils.getDataFromConfig("FileName");
    String sheetName = "ResetPasswordPage";


    public ResetPasswordPage(UIUtils utils, WaitUtils wUtils, VerifyUtils vUtils) throws IOException {
        this.utils = utils;
        this.wUtils = wUtils;
        this.vUtils = vUtils;
    }

    private By password = By.id(ExcelUtils.getCellValue(filePath, fileName, sheetName, "password", "Locator"));
    private By confirmPassword = By.id(ExcelUtils.getCellValue(filePath, fileName, sheetName, "confirmPassword", "Locator"));
    private By submitBtn = By.xpath(ExcelUtils.getCellValue(filePath, fileName, sheetName, "submitBtn", "Locator"));
    private By signInBtn = By.xpath(ExcelUtils.getCellValue(filePath, fileName, sheetName, "signInBtn", "Locator"));

    public void enterNewPassword(String newPass)
    {
        wUtils.eWaitForElementVisible(password, 30);
        utils.Type(password, "Enter new password", newPass);
    }

    public void enterConfirmPassword(String confirmPass)
    {
        wUtils.eWaitForElementVisible(confirmPassword, 30);
        utils.Type(confirmPassword, "Enter confirm password", confirmPass);
    }

    public void clickSubmitBtn()
    {
        wUtils.eWaitForElementVisible(submitBtn, 30);
        utils.click(submitBtn,"Click on submit button");
    }

    public void clickSignInBtn()
    {
        wUtils.eWaitForElementVisible(signInBtn, 30);
        utils.click(signInBtn,"Click on Sign-In button");
    }

    public void verifyResetPasswordUrl(String url)
    {
        wUtils.eWaitForElementVisible(signInBtn, 30);
        utils.verifyUrlExist(url);
    }

}
