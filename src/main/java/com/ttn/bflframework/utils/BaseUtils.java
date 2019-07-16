package com.ttn.bflframework.utils;


import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import javassist.runtime.Inner;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;

public class BaseUtils {
    public WebDriver driver;
    public static ExtentReports extent;
    public ExtentTest testReport;
    Logger log = Logger.getLogger(BaseUtils.class);
    public String url;
    public String envkey;
    public String browserkey;


    public UIUtils utils;

    String usrDirectory = System.getProperty("user.dir");


    @BeforeSuite
    public void config() {
        //Extent Report Setup in BaseLib
        extent = new ExtentReports(usrDirectory + "\\src\\main\\AutomationReport\\AutomationReport.html", true);
        extent.addSystemInfo("HostName", "Srikant Pandey");
        extent.addSystemInfo("Environment", "Web");
        extent.loadConfig(new File(usrDirectory + "\\src\\main\\resources\\extent-config.xml"));

        //Logger Setup in BaseLib
        PropertyConfigurator.configure(usrDirectory + "\\src\\main\\resources\\log4j.properties");
    }

    @BeforeMethod
    protected void startReporting(Method method) {
        String testName;

        testName = this.getClass().getSimpleName() + " : " + method.getName();
        System.out.println(method.getAnnotation(Test.class).description());
        testReport = extent.startTest(testName, method.getAnnotation(Test.class).description());

        log.info("Extent report logging started for " + testName);
        System.out.println(">>>>> Execution started: " + testName);
        testReport.log(LogStatus.INFO, "Test execution started.");
    }



    @BeforeMethod()

    public void setUpMultiInstance(Method method) throws IOException {

            browserkey = GenericUtils.getDataFromConfig("browser");
            driverInitilization(method,browserkey);
            envkey = GenericUtils.getDataFromConfig("environment");
            url = getEnvironmentURL(envkey);
            driver.get(url);
            driver.manage().deleteAllCookies();
            driver.manage().window().maximize();
        }

        private String getEnvironmentURL (String environment) throws IOException {
            switch (environment.toUpperCase()) {
                case "TEST":
                    return "https://bflweb:bflwebttn14@@bfltest-web-client.qa3.tothenew.net/" + GenericUtils.getDataFromConfig("language");
                case "DEV":
                    return "https://bflweb:bflwebttn14@@www.bfl-web-client.qa3.tothenew.net/" + GenericUtils.getDataFromConfig("language");
                case "UAT":
                    return "https:/bflweb:bflwebttn14@@/bfluat-web-client.qa3.tothenew.net/" + GenericUtils.getDataFromConfig("language");

                default:
                    return GenericUtils.getDataFromConfig("url");
            }
        }


        public void driverInitilization (Method method,String browser) throws IOException {
            String USERNAME = GenericUtils.getDataFromConfig("saucelabUsername");
            String ACCESS_KEY = GenericUtils.getDataFromConfig("saucelabAccesskey");
            String URL = "https://" + USERNAME + ":" + ACCESS_KEY + "@ondemand.saucelabs.com:443/wd/hub";

            System.out.println(browser + " is going to launch");
            log.info(browser + "browser instance is launching");
            switch (browser.toUpperCase()) {
                case "CHROME":
                        if (GenericUtils.getDataFromConfig("executionMode").equalsIgnoreCase("local")) {
                            System.setProperty("webdriver.chrome.driver", usrDirectory + "\\src\\main\\Drivers\\chromedriver.exe");
                            driver = new ChromeDriver();
                        } else if (GenericUtils.getDataFromConfig("executionMode").equalsIgnoreCase("cloud")) {

                            remoteWebDriverCapabilities(method,"chrome",GenericUtils.getDataFromConfig("executionPlatform"),GenericUtils.getDataFromConfig("browserVersion"),GenericUtils.getDataFromConfig("saucelabBuildName"),URL);
                        }


                    break;

                case "FIREFOX":
                    System.setProperty("webdriver.gecko.driver", usrDirectory + "\\src\\main\\Drivers\\geckodriver.exe");
                    driver = new FirefoxDriver();
                    break;

                case "EDGE":
                    System.setProperty("webdriver.edge.driver", "");
                    driver = new EdgeDriver();

                case "SAFARI":
                    System.setProperty("webdriver.safari.driver", "");
                    driver = new SafariDriver();


                default:
                    log.info("Unable to find browser. Initializing chromedriver");
                    System.setProperty("webdriver.chrome.driver", usrDirectory + "\\src\\main\\Drivers\\chromedriver.exe");
                    driver = new ChromeDriver();
                    log.info(browser + "browser instance is launching");
            }
        }

        public void remoteWebDriverCapabilities(Method method, String browser, String platform, String version, String build, String URL)
        {

            DesiredCapabilities caps = new DesiredCapabilities();
            caps.setCapability("browserName",browser);
            caps.setCapability("name", method.getName());
            caps.setCapability("platform", platform);
            caps.setCapability("version", version);
            caps.setCapability("build", build);
            try {
                driver = new RemoteWebDriver(new URL(URL), caps);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }


        @AfterMethod
        protected void reportClosure (Method method){
            String testName = this.getClass().getSimpleName() + " : " + method.getName();
            System.out.println(">>>>> Execution ended: " + testName);
            testReport.log(LogStatus.INFO, "Test execution completed.");
            extent.endTest(testReport);


        }


        @AfterMethod()

        protected void tearDownMultiInstance (ITestResult result) throws IOException {
            if (GenericUtils.getDataFromConfig("executionMode").equalsIgnoreCase("cloud")) {
                JavascriptExecutor jse = (JavascriptExecutor) driver;
                System.out.println(result.isSuccess());
                jse.executeScript("sauce:job-result=" + (result.isSuccess() ? "passed" : "failed"));
            }
                driver.quit();
            }



        @AfterSuite(alwaysRun = true)
        protected void endReporting () {
            extent.flush();
            extent.close();


        }
}



