package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.ElementUtils;

import java.time.Duration;

public class ProductDetailsPage {

    private WebDriver driver;
    private ElementUtils elementUtils;

    @FindBy(css = ".product-title, h1, [data-testid='product-title']")
    private WebElement productTitle;

    @FindBy(css = ".product-price, .price, [data-testid='product-price']")
    private WebElement productPrice;

    @FindBy(css = ".quantity-input, input[name='quantity'], [data-testid='quantity']")
    private WebElement quantityInput;

    @FindBy(css = ".quantity-plus, .qty-increase, [data-testid='increase-qty']")
    private WebElement increaseQuantityButton;

    @FindBy(css = ".quantity-minus, .qty-decrease, [data-testid='decrease-qty']")
    private WebElement decreaseQuantityButton;

    @FindBy(css = ".add-to-cart, [data-testid='add-to-cart'], button[type='submit']")
    private WebElement addToCartButton;

    @FindBy(css = ".product-image, .main-image, [data-testid='product-image']")
    private WebElement productImage;

    public ProductDetailsPage(WebDriver driver) {
        this.driver = driver;
        this.elementUtils = new ElementUtils(driver);
        PageFactory.initElements(driver, this);
//        new WebDriverWait(driver, Duration.ofSeconds(10)).until(ExpectedConditions.visibilityOf(quantityInput));
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
                Thread.currentThread().interrupt();
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
