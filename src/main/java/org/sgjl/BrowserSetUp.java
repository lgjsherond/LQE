package org.sgjl;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import java.util.Enumeration;
import java.util.ResourceBundle;

public class BrowserSetUp extends BasePage{

    public static WebDriver driver=null;
    public static String browser=null;
    public static String environment=null;


    // Set up the browser method to initialize the driver
    public static WebDriver setBrowser(String brw,String env,String headless) throws Exception{

        browser=brw;
        environment=env;

        switch(browser){
            case "chrome":
                WebDriverManager.chromedriver().setup();
                ChromeOptions opt=new ChromeOptions();
                opt.setCapability("acceptInsecureCerts", true);
                opt.addArguments("--start-maximized");

                if(headless.equalsIgnoreCase("true")){
                    opt.addArguments("--headless");
                    opt.addArguments("window-size=1920,1080");
                }

                driver=new ChromeDriver(opt);
                break;
            case "firefox":
                WebDriverManager.firefoxdriver().setup();
                FirefoxOptions ffopt=new FirefoxOptions();
                ffopt.setCapability("acceptInsecureCerts", true);
                ffopt.addArguments("--start-maximized");

                if(headless.equalsIgnoreCase("true")){
                    ffopt.addArguments("--headless");
                    ffopt.addArguments("window-size=1920,1080");
                }

                driver=new FirefoxDriver(ffopt);
                break;
            case "edge":
                WebDriverManager.edgedriver().setup();
                EdgeOptions edgopt=new EdgeOptions();
                edgopt.setCapability("acceptInsecureCerts", true);
                edgopt.addArguments("--start-maximized");

                if(headless.equalsIgnoreCase("true")){
                    edgopt.addArguments("--headless");
                    edgopt.addArguments("window-size=1920,1080");
                }

                driver=new EdgeDriver(edgopt);
                break;
            default:
                throw new Exception("Browser not supported: " + browser);
        }

        return driver;
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
