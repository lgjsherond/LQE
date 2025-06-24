package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utils.ElementUtils;
import utils.LoggerUtil;

import java.util.List;

public class SearchResultsPage {

    private WebDriver driver;
    private ElementUtils elementUtils;

    @FindBy(css = "a[area-label=\"Products tab\"]")
    private WebElement searchResultsContainer;

    @FindBy(css = ".product-item, .product-card, [data-testid='product-item']")
    private List<WebElement> productItems;

    @FindBy(css = "div:contains(\"Sort by : Relevance\")")
    private WebElement sortDropdown;

    @FindBy(css = ".klevuImgWrap > a.klevuProductClick")
    private WebElement firstProduct;

    @FindBy(css = ".no-results, .empty-results, [data-testid='no-results']")
    private WebElement noResultsMessage;

    public SearchResultsPage(WebDriver driver) {
        this.driver=driver;
        this.elementUtils=new ElementUtils(driver);
        PageFactory.initElements(driver, this);
    }

    @Step("Verify search results are displayed")
    public boolean areSearchResultsDisplayed() {
        return elementUtils.isElementDisplayed(searchResultsContainer);
    }

    @Step("Sort products by: {sortOption}")
    public void sortBy(String sortOption) {
        elementUtils.selectByVisibleText(sortDropdown, sortOption);

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            LoggerUtil.error("Error while waiting for sort to apply: " + e);
        }
    }

    @Step("Click on first product")
    public ProductDetailsPage clickFirstProduct() {
        elementUtils.waitAndClick(firstProduct);
        return new ProductDetailsPage(driver);
    }

    @Step("Get number of search results")
    public int getProductCount() {
        return productItems.size();
    }

    @Step("Verify sort functionality")
    public boolean isSortDropdownDisplayed() {
        return elementUtils.isElementDisplayed(sortDropdown);
    }
}
