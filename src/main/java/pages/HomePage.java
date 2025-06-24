package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.sgjl.BrowserSetUp;
import utils.ElementUtils;

import java.time.Duration;

public class HomePage {

    private WebDriver driver;
    private ElementUtils elementUtils;

    @FindBy(css = ".js-search-header > .icon")
    private WebElement searchButton;

    @FindBy(name = "q")
    private WebElement searchInput;

    @FindBy(css = ".logo--has-inverted > .small--hide")
    private WebElement logo;

    @FindBy(css = "a[href='/']")
    private WebElement mainNavigation;

    @FindBy(css = "a.site-nav__link.js-drawer-open-cart")
    private WebElement cartIcon;

    @FindBy(id="onetrust-accept-btn-handler")
    private WebElement acceptCookiesButton;

    @FindBy(css = "svg[title='Close dialog']")
    private WebElement closePopupButton;

    public HomePage(WebDriver driver){

        if(driver==null){
            throw new IllegalArgumentException("WebDriver cannot be null");
        }
        this.driver=driver;
        this.elementUtils=new ElementUtils(driver);

        System.out.println("Driver initialized: " + driver.getClass().getSimpleName());
        System.out.println("Current URL: " + driver.getCurrentUrl());

        PageFactory.initElements(driver,this);
//        new WebDriverWait(driver, Duration.ofSeconds(10)).until(ExpectedConditions.visibilityOf(searchInput));
    }

    @Step("Verify the home page is loaded")
    public boolean isHomePageLoaded(){
        if(elementUtils.isElementDisplayed(acceptCookiesButton)) {
            elementUtils.waitAndClick(acceptCookiesButton);
        }
        closePopupIfPresent();

        return  elementUtils.isElementDisplayed(searchButton) &&
                elementUtils.isElementDisplayed(logo);
    }

    @Step("Click on Search Button")
    public SearchResultsPage clickSearchButton(){
        elementUtils.waitUntilClickable(searchButton);
        try{
            elementUtils.waitAndClick(searchButton);
        } catch (Exception ignored) {
        }
        return new SearchResultsPage(driver);
    }

    @Step("Enter search text \"{searchText}\"")
    public void enterSearchText(String searchText){
        elementUtils.waitAndSendKeys(searchInput, searchText);
    }

    @Step("Perform search with text \"{searchText}\"")
    public SearchResultsPage performSearch(String searchText) {
        clickSearchButton();
        enterSearchText(searchText);
        searchInput.sendKeys(Keys.ENTER);
        return new SearchResultsPage(driver);
    }

    @Step("Get the page title")
    public String getPageTitle(){
        return driver.getTitle();
    }

    public void closePopupIfPresent() {
        try {
            if (elementUtils.isElementDisplayed(closePopupButton)) {
                elementUtils.waitAndClick(closePopupButton);
            }
        } catch (Exception ignored) {
        }
    }
}
