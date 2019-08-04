package com.ttn.bflframework.testscripts;

import com.relevantcodes.extentreports.LogStatus;
import com.ttn.bflframework.pages.PageClassObjects;
import com.ttn.bflframework.utils.BaseUtils;
import com.ttn.bflframework.utils.UIUtils;
import com.ttn.bflframework.utils.VerifyUtils;
import com.ttn.bflframework.utils.WaitUtils;
import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;

public class SignInTest extends BaseUtils {

    public final static Logger log = Logger.getLogger(SignInTest.class);

    public PageClassObjects pageObjects;
    @BeforeMethod
    public void test() throws IOException {
        UIUtils utils = new UIUtils(driver,testReport);
        WaitUtils wUtils = new WaitUtils(driver);
        VerifyUtils vUtils= new VerifyUtils(driver,testReport);
        pageObjects= new PageClassObjects(utils,wUtils,vUtils);

    }

    @Test
    public void signInWithValidUser()
    {
        pageObjects.common.countrySelection("Oman");
        pageObjects.common.verifyDomain("oman");
        pageObjects.home.mouseHoverOnProfileIcon();
        pageObjects.home.clickSignIn();
        pageObjects.signIn.enterUsermail("srikant8@mailnator.com");
        pageObjects.signIn.enterPassword("12345678");
        pageObjects.signIn.clickSignInBtn();
        pageObjects.home.mouseHoverOnProfileIcon();
        String actual=pageObjects.home.getUserName();
        Assert.assertEquals(actual, "Hello SrikantTest");
        testReport.log(LogStatus.PASS,"Successfully validated Username");
        System.out.println("Assertion successful");

    }
}
