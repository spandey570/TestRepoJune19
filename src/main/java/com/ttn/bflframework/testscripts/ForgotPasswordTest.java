package com.ttn.bflframework.testscripts;

import com.ttn.bflframework.pages.PageClassObjects;
import com.ttn.bflframework.utils.BaseUtils;
import com.ttn.bflframework.utils.UIUtils;
import com.ttn.bflframework.utils.VerifyUtils;
import com.ttn.bflframework.utils.WaitUtils;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;

public class ForgotPasswordTest extends BaseUtils {

    public PageClassObjects pageObjects;
    @BeforeMethod
    public void test() throws IOException {
        UIUtils utils = new UIUtils(driver,testReport);
        WaitUtils wUtils = new WaitUtils(driver);
        VerifyUtils vUtils= new VerifyUtils(driver,testReport);
        pageObjects= new PageClassObjects(utils,wUtils,vUtils);

    }

    @Test
    public void verifyForgotPasswordEndToEndFlow()
    {
        pageObjects.common.countrySelection("Oman");
        pageObjects.common.verifyDomain("oman");
        pageObjects.home.mouseHoverOnProfileIcon();
        pageObjects.home.clickSignIn();
        pageObjects.signIn.clickForgotPassword();
        pageObjects.signIn.enterUsermail("srikant.pandey@tothenew.com");
        pageObjects.signIn.clickSubmitBtn();
        pageObjects.signIn.clickCloseBtn();
        pageObjects.signIn.resetPassword();
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        pageObjects.resetpass.enterNewPassword("123456");
        pageObjects.resetpass.enterConfirmPassword("123456");
        pageObjects.resetpass.clickSubmitBtn();
        pageObjects.resetpass.verifyResetPasswordUrl("registration/thank-you/");
        pageObjects.resetpass.clickSignInBtn();
        pageObjects.signIn.enterUsermail("srikant.pandey@tothenew.com");
        pageObjects.signIn.enterPassword("123456");
        pageObjects.signIn.clickSubmitBtn();




    }
}
