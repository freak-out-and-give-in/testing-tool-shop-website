package org.pageobjects.productpage;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.pageobjects.checkoutpage.CheckoutPage;

import static org.junit.jupiter.api.Assertions.*;

class ProductPageTest {

    private WebDriver driver;

    private ProductPage productPage;

    @BeforeEach
    void setUp() {
        driver = new ChromeDriver();

        String productId = "01J22RWDABRTJFBCYTY31YVQMA";
        productPage = new ProductPage(driver, productId);
        productPage.load();
        productPage.isLoaded();
        productPage.waitForPageToLoad();
    }

    @AfterEach
    void tearDown() {
        driver.quit();
    }

    @Test
    void givenProductPage_whenClickingAddQuantity_thenTheQuantityIncreases() {
        productPage.increaseQuantity();

        assertEquals(productPage.getQuantity(), 2);
    }

    @Test
    void givenProductPage_whenClickingAddQuantityThreeTimesAndDecreaseQuality_thenTheQuantityDecreases() {
        productPage.increaseQuantity();
        productPage.increaseQuantity();
        productPage.increaseQuantity();

        productPage.decreaseQuantity();

        assertEquals(productPage.getQuantity(), 3);
    }

    @Test
    void givenProductPage_whenClickingAddToCart_thenAddToCart() {
        productPage.addToCart(1);

        CheckoutPage checkoutPage = new CheckoutPage(driver);
        driver.navigate().to(checkoutPage.url);
        assertEquals(1, checkoutPage.getItems().size());
    }

    @Test
    void givenProductPage_whenDoingNothing_thenTheQuantityShouldBeOne() {
        assertEquals(productPage.getQuantity(), 1);
    }
}