package org.sgjl;

import io.qameta.allure.Attachment;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.ui.WebDriverWait;

import org.testng.annotations.*;
import java.time.Duration;

import static utils.ConfigManager.getProperty;

public class BasePage {

    protected static ThreadLocal<WebDriver> driver=new ThreadLocal<>();
    protected static ThreadLocal<WebDriverWait> wait=new ThreadLocal<>();

    private String PROP_HEADLESS=getProperty("app.headless");
    private static final String PROP_BROWSER = "app.browser";

    @BeforeMethod(alwaysRun = true)
    @Parameters({"seleBrowser"})
    public void setUp(@Optional String seleBrowserParam)  {
        String browserToUse=(seleBrowserParam !=null && !seleBrowserParam.isEmpty())
                ?seleBrowserParam
                :PROP_BROWSER;
        initializeDriver(browserToUse);
        getDriver().manage().window().maximize();
        getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        wait.set(new WebDriverWait(getDriver(), Duration.ofSeconds(30)));
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown(){
        if(getDriver()!=null){
            getDriver().quit();
            driver.remove();
            wait.remove();
        }
    }

    private void initializeDriver(String browser){
        String headless = PROP_HEADLESS;
        switch(browser.toLowerCase()){
            case "chrome":
                ChromeOptions opt = new ChromeOptions();
                opt.setCapability("acceptInsecureCerts", true);
                opt.addArguments("--start-maximized");
                opt.addArguments("--disable-popup-blocking");

                if (headless.equalsIgnoreCase("true")) {
                    opt.addArguments("--headless");
                    opt.addArguments("window-size=1920,1080");
                }

                driver.set(new ChromeDriver(opt));
                break;
            case "firefox":
                FirefoxOptions ffopt = new FirefoxOptions();
                ffopt.setCapability("acceptInsecureCerts", true);
                ffopt.addArguments("--start-maximized");

                if (headless.equalsIgnoreCase("true")) {
                    ffopt.addArguments("--headless");
                    ffopt.addArguments("window-size=1920,1080");
                }

                driver.set(new FirefoxDriver(ffopt));
                break;
            case "edge":
                EdgeOptions edgopt = new EdgeOptions();
                edgopt.setCapability("acceptInsecureCerts", true);
                edgopt.addArguments("--start-maximized");

                if (headless.equalsIgnoreCase("true")) {
                    edgopt.addArguments("--headless");
                    edgopt.addArguments("window-size=1920,1080");
                }

                driver.set(new EdgeDriver(edgopt));
                break;
            default:
                throw new IllegalArgumentException("Browser not supported: " + browser);
        }
    }

    public static WebDriver getDriver() {
        return driver.get();
    }

    public static WebDriverWait getWait() {
        return wait.get();
    }

    protected void navigateToUrl(String url) {
        getDriver().get(url);
    }

    @Attachment(value = "Screenshot", type = "image/png")
    public byte[] takeScreenshot() {
        return ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.BYTES);
    }
}
