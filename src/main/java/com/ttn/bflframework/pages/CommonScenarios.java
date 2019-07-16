package com.ttn.bflframework.pages;

import com.ttn.bflframework.utils.GenericUtils;
import com.ttn.bflframework.utils.UIUtils;
import com.ttn.bflframework.utils.VerifyUtils;
import com.ttn.bflframework.utils.WaitUtils;
import org.openqa.selenium.By;

import java.io.IOException;

public class CommonScenarios {

    public UIUtils utils;
    public WaitUtils wUtils;
    public VerifyUtils vUtils;

    public CommonScenarios(UIUtils utils, WaitUtils wUtils, VerifyUtils vUtils) throws IOException {
        this.utils = utils;
        this.wUtils = wUtils;
        this.vUtils = vUtils;
    }

    public String country;
    private By countrySelectionDrpdwn= By.xpath("(//*[@class='country_selector']//div)[1]");
    private By selectCountry= By.xpath("//*[@class='country_selector']//span[text()='"+country+"']");
    private By currentSelectedCountry= By.xpath("//*[@class='country_selector']//span");
    private By applyButton= By.xpath("//*[@class='country_selector']/..//button");
    private By closeBtn= By.xpath("//*[@class='cross_btn']");

    public void moveBack()
    {
        utils.moveBack();
    }

    public void countrySelection(String country)
    {
        wUtils.eWaitForElementVisible(currentSelectedCountry,90);
        String currentCountry= utils.getText(currentSelectedCountry,"Current selected country");
        if(currentCountry.equalsIgnoreCase(country))
        {
            utils.click(closeBtn,"Stay on United Arab Emirates");
        }
        else
        {
            utils.click(countrySelectionDrpdwn,"Click on country selection dropdown");
            wUtils.iWaitForSeconds(30);
            utils.click(By.xpath("//*[@class='country_selector']//span[text()='"+country+"']"),"Application is going to switch in "+country+" domain");
        }
    }

    public void  verifyDomain(String domain){
         String currentUrl= utils.getUrl();

            String defaultUrl= GenericUtils.getDataFromConfig("url");
            String s1= defaultUrl.substring(0,8);
            String s2= defaultUrl.substring(31);
            String expectedUrl= s1+domain+s2+GenericUtils.getDataFromConfig("language");
            String actualUrl= utils.getUrl();
            vUtils.verifyStringEquals(actualUrl,expectedUrl,"Verify that user is on "+expectedUrl,true);

    }

    public void visualValidation(String apiKey,String appName, String testName, String windowName)
    {
        vUtils.verifyPageUI(apiKey,appName,testName,windowName);
    }

    public void pdfValidation(String apiKey,String filepath,String description)
    {
        vUtils.validatePDF(apiKey,filepath,description);
    }


}
