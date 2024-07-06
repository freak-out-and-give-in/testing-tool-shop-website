package org.pageobjects.productpage;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.LoadableComponent;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class ProductPage extends LoadableComponent<ProductPage> {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    private WebDriver driver;

    private String productId;

    public final String url;

    @FindBy(id = "btn-add-to-cart")
    private WebElement addToCartButton;

    @FindBy(id = "btn-increase-quantity")
    private WebElement increaseQuantityButton;

    @FindBy(id = "btn-decrease-quantity")
    private WebElement decreaseQuantityButton;

    @FindBy(id = "quantity-input")
    private WebElement quantity;

    @FindBy(id = "lblCartCount")
    private WebElement cartQuantity;

    @FindBy(id = "btn-add-to-favorites")
    private WebElement favoritesButton;

    public ProductPage(WebDriver driver, String productId) {
        this.driver = driver;
        this.productId = productId;
        this.url = "https://practicesoftwaretesting.com/product/" + productId;

        PageFactory.initElements(driver, this);
    }

    @Override
    public void load() {
        driver.get("https://practicesoftwaretesting.com/product/" + productId);
    }

    @Override
    public void isLoaded() throws Error {
        String url = driver.getCurrentUrl();
        assertTrue(url.contains("/product/"), "Not on the product page with the id " + productId);
    }

    public void waitForPageToLoad() {
        Wait<WebDriver> wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOf(quantity));
    }

    public void addToCart(int expectedTotalQuantity) {
        addToCartButton.click();

        // Have to wait a second for the server to give confirmation, otherwise it's not added
        Wait<WebDriver> wait = new WebDriverWait(driver, Duration.ofSeconds(2));
        wait.until(ExpectedConditions.attributeToBe(cartQuantity, "textContent", expectedTotalQuantity + ""));
    }

    public void increaseQuantity() {
        increaseQuantityButton.click();
    }

    public void decreaseQuantity() {
        decreaseQuantityButton.click();
    }

    public int getQuantity() {
        return Integer.parseInt(quantity.getAttribute("value"));
    }

    public void addToFavorites() {
        Wait<WebDriver> wait = new WebDriverWait(driver, Duration.ofSeconds(2));
        wait.until(ExpectedConditions.visibilityOf(favoritesButton));

        favoritesButton.click();
    }
}
