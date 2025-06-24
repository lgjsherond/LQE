package utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class ElementUtils {

    private WebDriver driver;
    private WebDriverWait wait;

    public ElementUtils(WebDriver driver){
        this.driver=driver;
        this.wait=new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    public void waitAndClick(WebElement element){
        wait.until(ExpectedConditions.elementToBeClickable(element)).click();
    }

    public void waitAndSendKeys(WebElement element,String text){
        wait.until(ExpectedConditions.visibilityOf(element));
        element.clear();
        element.sendKeys(text);
    }

    public String waitAndGetText(WebElement element){
        return wait.until(ExpectedConditions.visibilityOf(element)).getText();
    }

    public boolean isElementDisplayed(WebElement element){
        try {
            return wait.until(ExpectedConditions.visibilityOf(element)).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public void selectByVisibleText(WebElement dropdown, String text) {
        wait.until(ExpectedConditions.elementToBeClickable(dropdown));
        Select select = new Select(dropdown);
        select.selectByVisibleText(text);
    }

    public void waitUntilClickable(WebElement element){
        wait.until(ExpectedConditions.elementToBeClickable(element));
    }

}
