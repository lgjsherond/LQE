package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utils.ElementUtils;
import utils.LoggerUtil;

public class ProductDetailsPage {

    private WebDriver driver;
    private ElementUtils elementUtils;

    @FindBy(css = "")
    private WebElement productTitle;

    @FindBy(css = "")
    private WebElement productPrice;

    @FindBy(css = "")
    private WebElement quantityInput;

    @FindBy(css = "")
    private WebElement increaseQuantityButton;

    @FindBy(css = "")
    private WebElement decreaseQuantityButton;

    @FindBy(css = "")
    private WebElement addToCartButton;

    @FindBy(css = "")
    private WebElement productImage;

    public ProductDetailsPage(WebDriver driver) {
        this.driver = driver;
        this.elementUtils = new ElementUtils(driver);
        PageFactory.initElements(driver, this);
  }

    @Step("Verify product details page is loaded")
    public boolean isProductDetailsPageLoaded() {
        return elementUtils.isElementDisplayed(productTitle) &&
                elementUtils.isElementDisplayed(addToCartButton);
    }

    @Step("Get product title")
    public String getProductTitle() {
        return elementUtils.waitAndGetText(productTitle);
    }

    @Step("Get product price")
    public String getProductPrice() {
        return elementUtils.waitAndGetText(productPrice);
    }

    @Step("Increase quantity to: {targetQuantity}")
    public void setQuantity(int targetQuantity) {
        elementUtils.waitAndSendKeys(quantityInput, String.valueOf(targetQuantity));
    }

    @Step("Increase quantity by clicking plus button")
    public void increaseQuantity(int times) {
        for (int i = 0; i < times; i++) {
            elementUtils.waitAndClick(increaseQuantityButton);
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                LoggerUtil.error("Interrupted while increasing quantity: " + e);
            }
        }
    }

    @Step("Get current quantity value")
    public int getCurrentQuantity() {
        String quantityValue = quantityInput.getAttribute("value");
        return Integer.parseInt(quantityValue);
    }

    @Step("Click add to cart button")
    public CartPage clickAddToCart(){
        elementUtils.waitAndClick(addToCartButton);
        return  new CartPage(driver);
    }

    public boolean isAddToCartButtonEnabled() {
        return addToCartButton.isEnabled();
    }

}
