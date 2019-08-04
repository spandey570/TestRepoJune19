package com.ttn.bflframework.utils;


import com.applitools.eyes.selenium.Eyes;
import com.applitools.eyes.selenium.StitchMode;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import sun.nio.ch.IOUtil;


import java.io.IOException;

public class VerifyUtils {

    private WebDriver driver;
    private ExtentTest testReport;
    public final static Logger log = Logger.getLogger(VerifyUtils.class);

    public VerifyUtils(WebDriver driver, ExtentTest testReport)
    {
        this.driver=driver;
        this.testReport=testReport;
    }


    public void verifyStringEquals(String actual, String expected, String description, boolean takeScreenshot) {
        try {
            Assert.assertEquals(actual, expected);
            log.info("Assertion "+description+" is successful");
            testReport.log(LogStatus.PASS,description);
            System.out.println("Assertion successful");
        }
        catch (AssertionError e)
        {
            reportTestStepFailure(description, e,takeScreenshot);
            log.info("Assertion Failure: "+e.getMessage());
            Assert.fail(e.getMessage());

        }
    }

    public void verifyIntEquals(int actual, int expected, String description, boolean takeScreenshot) {
        try {
            Assert.assertEquals(actual,expected);
            log.info("Assertion "+description+" is successful");
            testReport.log(LogStatus.PASS,description);
            System.out.println("Assertion successful");
        }
        catch (AssertionError e)
        {
            reportTestStepFailure(description, e,takeScreenshot);
            log.info("Assertion Failure: "+e);
        }
    }


    private void reportTestStepFailure(String description, AssertionError e, boolean takeScreenshot) {
        if (takeScreenshot) {
            String screenshotName = null;
            try {
                screenshotName = GenericUtils.takeScreenshot(driver);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            testReport.log(LogStatus.FAIL, description + "<br><b>Failed: </b>"
                    + e.getMessage().replace("\n", "<br>") + "<br><b>Snapshot:</b><br>"
                    + testReport.addScreenCapture(screenshotName));
        } else {
            testReport.log(LogStatus.FAIL, description + "<br><b>Failed: </b>"
                    + e.getMessage().replace("\n", "<br>"));
        }
    }


    public void verifyPageUI(String apiKey,String appName,String testName,String windowName)
    {
        Eyes eyes = new Eyes();
        eyes.setApiKey(apiKey);

        try {
            eyes.open(driver, appName, testName);
            eyes.setForceFullPageScreenshot(true);
            eyes.setStitchMode(StitchMode.CSS);
            eyes.checkWindow(windowName);
            eyes.close();
            testReport.log(LogStatus.PASS,testName);
        } catch (Exception e) {
            System.out.println(e);
        }
        finally {
            eyes.abortIfNotClosed();
        }
    }

    public void validatePDF(String apiKey,String filepath,String description)
    {
        String command = String.format("java -jar D:\\pdfValidation\\ImageTester.jar -k %s -f %s",apiKey,filepath);
        try {
            Process proc = Runtime.getRuntime().exec(command);
            proc.waitFor();
            String result = IOUtils.toString(proc.getInputStream(), "UTF-8");
            System.out.println(result);
            if(result.contains("Mismatch"))
            {
                testReport.log(LogStatus.FAIL,description+" : "+result);
            }
            else
            {
                testReport.log(LogStatus.PASS,description);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
