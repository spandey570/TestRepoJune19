package com.ttn.bflframework.pages;

import com.ttn.bflframework.utils.*;
import org.openqa.selenium.By;

import java.io.IOException;

public class AddressPage {

    public UIUtils utils ;
    public WaitUtils wUtils;
    public VerifyUtils vUtils;


    String usrDirectory= System.getProperty("user.dir");
    String filePath=usrDirectory+"\\src\\main\\java\\com\\ttn\\bflframework\\testdata";
    String fileName= GenericUtils.getDataFromConfig("FileName");
    String sheetName="AddressPage";

    private By firstname = By.name(ExcelUtils.getCellValue(filePath, fileName, sheetName, "firstname", "Locator"));
    private By lastname = By.name(ExcelUtils.getCellValue(filePath, fileName, sheetName, "lastname", "Locator"));
    private By city = By.xpath(ExcelUtils.getCellValue(filePath, fileName, sheetName, "city", "Locator"));
    private By area = By.xpath(ExcelUtils.getCellValue(filePath, fileName, sheetName, "area", "Locator"));
    private By firstCity = By.xpath(ExcelUtils.getCellValue(filePath, fileName, sheetName, "firstCity", "Locator"));
    private By firstArea = By.xpath(ExcelUtils.getCellValue(filePath, fileName, sheetName, "firstArea", "Locator"));
    private By fullAddress = By.name(ExcelUtils.getCellValue(filePath, fileName, sheetName, "fullAddress", "Locator"));
    private By stateCode = By.xpath(ExcelUtils.getCellValue(filePath, fileName, sheetName, "stateCode", "Locator"));
    private By phoneNumber = By.name(ExcelUtils.getCellValue(filePath, fileName, sheetName, "phoneNumber", "Locator"));
    private By setAsDefaultAddressChkBox = By.name(ExcelUtils.getCellValue(filePath, fileName, sheetName, "setAsDefaultAddressChkBox", "Locator"));
    private By shipToAddressBtn = By.xpath(ExcelUtils.getCellValue(filePath, fileName, sheetName, "shipToAddressBtn", "Locator"));
    private By cancelBtn = By.xpath(ExcelUtils.getCellValue(filePath, fileName, sheetName, "cancelBtn", "Locator"));
    private By selectedStateCode = By.xpath(ExcelUtils.getCellValue(filePath, fileName, sheetName, "selectedStateCode", "Locator"));


    public AddressPage(UIUtils utils,WaitUtils wUtils,VerifyUtils vUtils) throws IOException {
        this.utils=utils;
        this.wUtils =wUtils;
        this.vUtils = vUtils;
    }

    public void enterFirstName(String input)
    {
        wUtils.eWaitForElementVisible(firstname,30);
        utils.Type(firstname,"Enter First Name",input);
    }

    public void enterLastName(String input)
    {
        wUtils.eWaitForElementVisible(lastname,30);
        utils.Type(lastname,"Enter Last Name",input);
    }

    public void enterFullAddress(String input)
    {
        wUtils.eWaitForElementVisible(fullAddress,30);
        utils.Type(fullAddress,"Enter Full Address",input);
    }

    public void enterPhoneNumber(String input)
    {
        wUtils.eWaitForElementVisible(phoneNumber,30);
        utils.Type(phoneNumber,"Enter First Name",input);
    }

    public void selectArea()
    {
        wUtils.eWaitForElementVisible(area,30);
        utils.click(area,"Click on area dropdown");
        wUtils.eWaitForElementVisible(firstArea,30);
        utils.click(firstArea,"Click on first area option from the list");
    }

    public void selectCity()
    {
        wUtils.eWaitForElementVisible(city,30);
        utils.click(city,"Click on city dropdown");
        wUtils.eWaitForElementVisible(firstCity,30);
        utils.click(firstCity,"Click on first city option from the list");
    }

    public void selectStateCode()
    {
        wUtils.eWaitForElementVisible(stateCode,30);
        utils.click(stateCode,"Click on state-code dropdown");
        wUtils.eWaitForElementVisible(selectedStateCode,30);
        utils.click(selectedStateCode,"Click on first state-code option from the list");
    }

    public void selectDefaultAddressChkBox()
    {
        wUtils.eWaitForElementVisible(setAsDefaultAddressChkBox,30);
        utils.click(setAsDefaultAddressChkBox,"Click on Set as default delivery address checkbox");
    }

    public void clickShipToAddressBtn()
    {
        wUtils.eWaitForElementVisible(shipToAddressBtn,30);
        utils.click(shipToAddressBtn,"Click on ship to address btn");
    }

    public void clickCancelBtn()
    {
        wUtils.eWaitForElementVisible(cancelBtn,30);
        utils.click(cancelBtn,"Click on cancel btn");
    }
}
