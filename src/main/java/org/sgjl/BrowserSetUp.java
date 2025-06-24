package org.sgjl;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import java.util.Enumeration;
import java.util.ResourceBundle;

import static common.Constants.*;

public class BrowserSetUp extends BasePage {

    public static WebDriver driver = null;
    public static String browser = null;
    public static String environment = null;


    // Set up the browser method to initialize the driver
    public static WebDriver setBrowser() throws Exception {

        // Fetch browser
        String brw = System.getProperty("brw");
        if (brw == null) {
            brw = getGlobalProperties("app.browser");
        }

        // Fetch environment
        String env = System.getProperty("env");
        if (env == null) {
            env = getGlobalProperties("app.env");
        }

        // Fetch headless
        String headless = System.getProperty("headless");
        if (headless == null) {
            headless = getGlobalProperties("app.headless");
        }

        browser = brw;
        environment = env;

        switch (browser) {
            case "chrome":
                ChromeOptions opt = new ChromeOptions();
                opt.setCapability("acceptInsecureCerts", true);
                opt.addArguments("--start-maximized");

                if (headless.equalsIgnoreCase("true")) {
                    opt.addArguments("--headless");
                    opt.addArguments("window-size=1920,1080");
                }

                driver = new ChromeDriver(opt);
                break;
            case "firefox":
                FirefoxOptions ffopt = new FirefoxOptions();
                ffopt.setCapability("acceptInsecureCerts", true);
                ffopt.addArguments("--start-maximized");

                if (headless.equalsIgnoreCase("true")) {
                    ffopt.addArguments("--headless");
                    ffopt.addArguments("window-size=1920,1080");
                }

                driver = new FirefoxDriver(ffopt);
                break;
            case "edge":
                EdgeOptions edgopt = new EdgeOptions();
                edgopt.setCapability("acceptInsecureCerts", true);
                edgopt.addArguments("--start-maximized");

                if (headless.equalsIgnoreCase("true")) {
                    edgopt.addArguments("--headless");
                    edgopt.addArguments("window-size=1920,1080");
                }

                driver = new EdgeDriver(edgopt);
                break;
            default:
                throw new Exception("Browser not supported: " + browser);
        }

        return driver;
    }

    public static String getBaseURL() {
        String env=environment;

        if(env==null){
            env=getGlobalProperties("app.env");
        }
        environment=env;

        switch(env.toUpperCase()) {

            case "PROD":
                return BASE_URL;
            case "QA":
                return QA_BASE_URL;
            case "PRE_PROD":
                return PPROD_BASE_URL;
            default:
                return QA_BASE_URL;
        }
}

    public static void navigateToHomePage() throws Exception {
        setBrowser();
        String BaseURL = getBaseURL();
        if (BaseURL == null || BaseURL.isEmpty()) {
            throw new IllegalArgumentException("Base URL is not defined in global properties.");
        }
        driver.get(BaseURL);
    }

    //Access Global properties
    public static String getGlobalProperties(String key){

        String value="";
        ResourceBundle globalresourceBundle=ResourceBundle.getBundle("global");
        Enumeration<String> keys=globalresourceBundle.getKeys();

        while(keys.hasMoreElements()){
            if(key.equalsIgnoreCase(keys.nextElement())){
                value=globalresourceBundle.getString(key);
                break;
            }
        }
        return value;
    }

    // Tear down method to quit the driver
    public static void tearDown(){
        if(!(driver.toString().contains("null"))){
            driver.quit();
        }
    }
}
