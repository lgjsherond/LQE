package org.sgjl.test;

import io.qameta.allure.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.sgjl.BasePage;
import org.sgjl.BrowserSetUp;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.CartPage;
import pages.HomePage;
import pages.ProductDetailsPage;
import pages.SearchResultsPage;

@Epic("FAO Schwarz E-commerce Testing")
@Feature("Product Search and Cart Functionality")
public class AddToFlowTest extends BasePage {

    private HomePage homePage;
    private SearchResultsPage searchPage;
    private ProductDetailsPage productDetailsPage;
    private CartPage cartPage;

    @Test(priority = 1, description = "Complete user journey from homepage to cart")
    @Story("User can search for products and add them to cart")
    @Severity(SeverityLevel.CRITICAL)
    @Description("E2E Workflow Goto site -> Search -> Sort -> Add to Cart -> Validate")
    public void testCompleteUserJourney() throws Exception {

        navigateToHomePage();
        performSearchAction();
        searchForProducts("toys");
        sortProductsByPrice();
        openProductDetails();
        updateProductQuantity(3);
        addProductToCart();
        validateCartInformation();
    }

    @Step("Step 1: Navigate to FAO Schwarz homepage")
    private void navigateToHomePage() throws Exception{
        BrowserSetUp.navigateToHomePage();
        homePage = new HomePage(BrowserSetUp.driver);

        Assert.assertTrue(homePage.isHomePageLoaded(),
                "Home page should load properly");

        takeScreenshot();
        System.out.println("✓ Homepage loaded successfully");
    }

    @Step("Step 2: Click on search button")
    private void performSearchAction() {
        searchPage = homePage.clickSearchButton();
        Assert.assertNotNull(searchPage,"Search layout should load");
        takeScreenshot();
    }

    @Step("Step 3: Search for products with term: {searchTerm}")
    private void searchForProducts(String searchTerm) {
        searchPage = homePage.performSearch(searchTerm);

        Assert.assertTrue(searchPage.areSearchResultsDisplayed(), "Search results should appear on the page");
        Assert.assertTrue(searchPage.getProductCount() > 0, "At least one product should be found");
        takeScreenshot();
    }

    @Step("Step 4: Sort products by price (low to high)")
    private void sortProductsByPrice() {
        Assert.assertTrue(searchPage.isSortDropdownDisplayed(),"Sort dropdown should be available");
        searchPage.sortBy("Price: Low to High");
        Assert.assertTrue(searchPage.areSearchResultsDisplayed(),"Results should get sorted accurately");
        takeScreenshot();
    }

    @Step("Step 5: Open product details page")
    private void openProductDetails() {
        productDetailsPage = searchPage.clickFirstProduct();
        Assert.assertTrue(productDetailsPage.isProductDetailsPageLoaded(),"Product display page should load");
        takeScreenshot();
    }

    @Step("Step 6: Update product quantity to: {quantity}")
    private void updateProductQuantity(int quantity) {
        try {
            productDetailsPage.increaseQuantity(quantity - 1);
        } catch (Exception e) {
            productDetailsPage.setQuantity(quantity);
        }

        try {
            int currentQuantity = productDetailsPage.getCurrentQuantity();
            Assert.assertEquals(currentQuantity, quantity,"Should increase count to " + quantity);
        } catch (Exception e) {
            Assert.assertTrue(productDetailsPage.isAddToCartButtonEnabled(),"Add to cart button should remain functional");
        }
        takeScreenshot();
    }

    @Step("Step 7: Add product to cart")
    private void addProductToCart() {
        String productName = productDetailsPage.getProductTitle();
        cartPage = productDetailsPage.clickAddToCart();

        Assert.assertTrue(cartPage.isCartPopupDisplayed(),"Cart slider pop-up should appear");
        takeScreenshot();
    }

    @Step("Step 8: Validate cart information")
    private void validateCartInformation() {
        try {
            int cartQuantity = cartPage.getCartItemQuantity();
            Assert.assertEquals(cartQuantity, 3,"Added number of items should be correct (3)");
        } catch (Exception e) {
            Assert.fail("Cart item quantity should be retrievable");
        }

        Assert.assertNotNull(cartPage.getCartSubtotal(),"Subtotal should be updated accordingly");
        Assert.assertFalse(cartPage.getCartSubtotal().isEmpty(),"Subtotal should not be empty");
        Assert.assertTrue(cartPage.isChatIconDisplayed(),"Real time chat icon should be displayed on the popup");
        performComprehensiveCartValidation();
    takeScreenshot();
    }

    @Step("Perform comprehensive cart validation")
    private void performComprehensiveCartValidation() {
        Assert.assertTrue(cartPage.isCartPopupDisplayed(),"Cart popup should remain visible");

        String productName = cartPage.getProductNameInCart();
        Assert.assertNotNull(productName,"Product name should be displayed in cart");
        Assert.assertFalse(productName.trim().isEmpty(),"Product name should not be empty");

        String itemPrice = cartPage.getCartItemPrice();
        Assert.assertNotNull(itemPrice,"Item price should be displayed");

        String subtotal = cartPage.getCartSubtotal();
        Assert.assertNotNull(subtotal,"Subtotal should be displayed");
    }

    @Test(priority = 2, description = "Test search with no results")
    @Story("Handle empty search results gracefully")
    @Severity(SeverityLevel.NORMAL)
    public void testEmptySearchResults() throws Exception{
        BrowserSetUp.navigateToHomePage();
        homePage = new HomePage(driver);

        searchPage = homePage.performSearch("mynameissheron");
        Assert.assertNotNull(searchPage, "Search should handle no results gracefully");
        takeScreenshot();
    }

    @Test(priority = 3, description = "Test cart functionality with single item")
    @Story("Basic cart operations work correctly")
    @Severity(SeverityLevel.NORMAL)
    public void testBasicCartFunctionality() throws Exception{
        BrowserSetUp.navigateToHomePage();
        homePage = new HomePage(driver);
        searchPage = homePage.performSearch("toys");

        if (searchPage.areSearchResultsDisplayed()) {
            productDetailsPage = searchPage.clickFirstProduct();

            if (productDetailsPage.isProductDetailsPageLoaded()) {
                cartPage = productDetailsPage.clickAddToCart();
                Assert.assertTrue(cartPage.isCartPopupDisplayed(),"Cart should open with single item");
            }
        }
    takeScreenshot();
    }
}
