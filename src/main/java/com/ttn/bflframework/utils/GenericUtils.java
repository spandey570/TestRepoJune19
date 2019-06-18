package com.ttn.bflframework.utils;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import javax.mail.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Calendar;
import java.util.Properties;

public class GenericUtils {

    static String usrDirectory= System.getProperty("user.dir");

    public static String getDataFromConfig(String key) throws IOException {

        Properties prop = new Properties();
        File f = new File(usrDirectory+"\\src\\main\\resources\\config.properties");

        FileInputStream fip = new FileInputStream(f);

         prop.load(fip);
         String value= prop.getProperty(key);
         return value;
    }

    public static String takeScreenshot(WebDriver driver) throws IOException {

        String screenshotName = "image" + System.currentTimeMillis() + ".png";
        TakesScreenshot scrShot =((TakesScreenshot)driver);
        File SrcFile=scrShot.getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(SrcFile, new File(usrDirectory+"\\src\\main\\resources\\ScreenShots\\"+screenshotName));
        return screenshotName;

    }


    public static Properties getMailProp() {
    //    log.info("Setting properties file to pick properties for the email config");
        Properties properties = new Properties();

        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.socketFactory.port", "465");
        properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.port", "465");
        return properties;
    }

    public static Folder connectEmailInbox(Store store) throws MessagingException {
        String host = "smtp.gmail.com";


        store.connect(host, "srikant.pandey@tothenew.com"
                ,"TTN081192");
        System.out.println("create the folder object and open the inbox");
        Folder inbox = store.getFolder("INBOX");
        inbox.open(Folder.READ_WRITE);
        return inbox;
    }

    public static String getMessageBody(Message message) {
        try {
            if (message.isMimeType("text/plain")) {

                return message.getContent().toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }


    private static String getMailContentsUsingTimeout() throws MessagingException, InterruptedException {
        int polling = 1;

        try {

            System.out.println("Starting email session with given parameters");
            Session emailSession = Session.getDefaultInstance(getMailProp(), null);
            System.out.println("create the store object and connect with the server");
            Store store = emailSession.getStore("imaps");
            //connect to mailbox and fetch the inbox
            Folder inbox = null;
            try {
                inbox = connectEmailInbox(store);
            } catch (MessagingException e1) {
                e1.printStackTrace();
            }

            //each polling counter takes around 4sec to complete traverse of recent 10 mails
            // while (polling < 500) {
            System.out.println("Retrieve the messages from the folder in an array. Polling " + polling);
            Message[] mailList = new Message[0];
            try {
                mailList = inbox.getMessages();
            } catch (MessagingException e1) {
                e1.printStackTrace();
            }

            for (int i = mailList.length - 1; i > mailList.length - 100; i--) {
                Message message = mailList[i];
                if (message.getSubject().contains("reset your password") &&
                        message.getReceivedDate().toString().contains(Calendar.getInstance().getTime().toString().substring(0, 10))) {

                    System.out.println("Got email successfully. " + message.getSubject());
                    System.out.println("Subject: " + message.getSubject());


                    return getMessageBody(message);
                }

                Thread.sleep(2000);
                polling += 1;
            }
            System.out.println("close the store and folder objects");
            inbox.close(true);
            store.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("No email found, throwing exception: " + e.getMessage());
        }
        return null;
    }

    public static String getLinkFromEmail( ) throws MessagingException, InterruptedException {
        String msgBody = getMailContentsUsingTimeout();
        System.out.println("Email message body: " + msgBody);
        if(msgBody.contains("bfltest-web-client.qa3.tothenew.net/en/reset-password")){
            return "reset password link found";

        }
        if (msgBody != null) {
            try {
                return org.jsoup.Jsoup.parse(msgBody).select("a").first().attr("href");
            } catch (NullPointerException npe) {
                try {
                    return org.jsoup.Jsoup.parse(msgBody).getElementsByAttributeValue("style", "margin-bottom:20px").first().text();
                } catch (NullPointerException np) {
                    return org.jsoup.Jsoup.parse(msgBody).select("p").first().text();
                }
            }
        } else {
            return "No Email Found Yet";
        }
    }



    }