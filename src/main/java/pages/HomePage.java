package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import utils.ElementUtils;

public class HomePage {

    private WebDriver driver;
    private ElementUtils elementUtils;

    public HomePage(WebDriver driver){
        this.driver=driver;
        this.elementUtils=new ElementUtils(driver);
        PageFactory.initElements(driver,this);
    }
}
