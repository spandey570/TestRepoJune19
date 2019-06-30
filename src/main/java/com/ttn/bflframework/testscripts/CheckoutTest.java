package com.ttn.bflframework.testscripts;

import com.ttn.bflframework.pages.PageClassObjects;
import com.ttn.bflframework.utils.BaseUtils;
import com.ttn.bflframework.utils.UIUtils;
import com.ttn.bflframework.utils.VerifyUtils;
import com.ttn.bflframework.utils.WaitUtils;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;

public class CheckoutTest extends BaseUtils {

    public PageClassObjects pageObjects;
    @BeforeMethod
    public void test() throws IOException {
        UIUtils utils = new UIUtils(driver,testReport);
        WaitUtils wUtils = new WaitUtils(driver);
        VerifyUtils vUtils= new VerifyUtils(driver,testReport);
        pageObjects= new PageClassObjects(utils,wUtils,vUtils);

    }

    @Test
    public void checkoutAsRegisteredUser()
    {


        pageObjects.common.countrySelection("United Arab Emirates");
        pageObjects.home.mouseHoverOnProfileIcon();
        pageObjects.home.clickSignIn();
        pageObjects.signIn.enterUsermail("srikant8@mailnator.com");
        pageObjects.signIn.enterPassword("12345678");
        pageObjects.signIn.clickSignInBtn();

        pageObjects.header.clickNew();
        String details= pageObjects.plp.getFirstProductDescription();
        String price= pageObjects.plp.getFirstProductPrice();
        pageObjects.plp.mousehoverOnFirstProduct();
        pageObjects.plp.clickQuickLookBtn();
        pageObjects.ql.clickOnSeeProductDetails();
        pageObjects.pdp.selectAvailableItemSize();
        pageObjects.pdp.clickAddToCartBtn();
        pageObjects.header.clickOnCart();
        pageObjects.cart.verifyProductDescription(details);
        pageObjects.cart.verifyProductPrice(price);
        pageObjects.cart.getItemSize();
        String deliveryOption= pageObjects.cart.getDeliveryType();
        pageObjects.cart.getTotalPrice();
        pageObjects.cart.clickCheckoutBtn();
     //   pageObjects.checkout.verifyStandardDeliveryOption(deliveryOption);
        pageObjects.checkout.addUserNewAddress();
        pageObjects.address.enterFirstName("Sri");
        pageObjects.address.enterLastName("Pandey");
        pageObjects.address.selectCity();
        pageObjects.address.selectArea();
        pageObjects.address.enterFullAddress("Kanpur");
        pageObjects.address.selectStateCode();
        pageObjects.address.enterPhoneNumber("1234567");
        pageObjects.address.selectDefaultAddressChkBox();
        pageObjects.address.clickShipToAddressBtn();
        pageObjects.checkout.clickContinueBtn();
        pageObjects.checkout.clickCashOnDeliveryOption();
        pageObjects.checkout.enterVoucherCode("Disc30");
        pageObjects.checkout.clickVoucherApplyBtn();
        pageObjects.checkout.verifyProductDetailsOnCheckout(details);
        pageObjects.checkout.totalPrice();



    }

}
