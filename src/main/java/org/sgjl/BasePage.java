package org.sgjl;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.sgjl.BrowserSetUp.getGlobalProperties;

public class BasePage {

    public static WebDriver driver;
    public static WebDriverWait wait;

    private static String seleBrowser=null;
    private static String seleEnv=null;
    private static String seleHeadless=null;

    public static final int TIME_OUT=30;

    public static void envSetUp(){

        seleEnv=System.getProperty("env");
        seleBrowser=System.getProperty("brw");
        seleHeadless=System.getProperty("headless");

        if(seleEnv==null && seleBrowser==null){
            seleEnv=getGlobalProperties("app.env");
            seleBrowser=getGlobalProperties("app.browser");
        }

        if(seleHeadless==null){
            seleHeadless=getGlobalProperties("app.headless");
        }

    }

    public static void browser() throws Exception{
        driver=BrowserSetUp.setBrowser(seleBrowser,seleEnv,seleHeadless);
    }

}
