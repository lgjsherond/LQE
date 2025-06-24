package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utils.ElementUtils;

public class CartPage {

    private WebDriver driver;
    private ElementUtils elementUtils;

    @FindBy(css = ".cart-popup, .cart-slider, .mini-cart, [data-testid='cart-popup']")
    private WebElement cartPopup;

    @FindBy(css = ".cart-item, .line-item, [data-testid='cart-item']")
    private WebElement cartItem;

    @FindBy(css = ".item-quantity, .quantity-display, [data-testid='cart-quantity']")
    private WebElement itemQuantity;

    @FindBy(css = ".item-price, .line-item-price, [data-testid='item-price']")
    private WebElement itemPrice;

    @FindBy(css = ".subtotal, .cart-subtotal, [data-testid='cart-subtotal']")
    private WebElement subtotal;

    @FindBy(css = ".product-name, .item-title, [data-testid='cart-product-name']")
    private WebElement productName;

    @FindBy(css = ".chat-icon, .live-chat, [data-testid='chat-icon']")
    private WebElement chatIcon;

    @FindBy(css = ".cart-close, .close-cart, [data-testid='close-cart']")
    private WebElement closeCartButton;

    public CartPage(WebDriver driver){
        this.driver=driver;
        this.elementUtils = new ElementUtils(driver);
        PageFactory.initElements(driver, this);
//        new WebDriverWait(driver, Duration.ofSeconds(10)).until(ExpectedConditions.visibilityOf(subtotal));
    }

    @Step("Verify if the cart popup is displayed")
    public boolean isCartPopupDisplayed() {
        return elementUtils.isElementDisplayed(cartPopup);
    }

    @Step("Get cart item quantity")
    public int getCartItemQuantity() {
        String quantityText = elementUtils.waitAndGetText(itemQuantity);
        return Integer.parseInt(quantityText.replaceAll("\\D+", ""));
    }

    @Step("Get cart item price")
    public String getCartItemPrice() {
        return elementUtils.waitAndGetText(itemPrice);
    }

    @Step("Get cart subtotal")
    public String getCartSubtotal() {
        return elementUtils.waitAndGetText(subtotal);
    }

    @Step("Get product name in cart")
    public String getProductNameInCart() {
        return elementUtils.waitAndGetText(productName);
    }

    @Step("Verify chat icon is displayed")
    public boolean isChatIconDisplayed() {
        return elementUtils.isElementDisplayed(chatIcon);
    }

    @Step("Verify cart contains correct product information")
    public boolean validateCartInformation(String expectedProductName, int expectedQuantity) {
        boolean isProductNameCorrect = getProductNameInCart().contains(expectedProductName);
        boolean isQuantityCorrect = getCartItemQuantity() == expectedQuantity;
        boolean isSubtotalDisplayed = elementUtils.isElementDisplayed(subtotal);
        boolean isChatIconVisible = isChatIconDisplayed();

        return isProductNameCorrect && isQuantityCorrect && isSubtotalDisplayed && isChatIconVisible;
    }

    @Step("Close cart popup")
    public void closeCart() {
        if (elementUtils.isElementDisplayed(closeCartButton)) {
            elementUtils.waitAndClick(closeCartButton);
        }
    }
}
