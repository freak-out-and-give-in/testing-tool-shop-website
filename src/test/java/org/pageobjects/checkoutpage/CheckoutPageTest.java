package org.pageobjects.checkoutpage;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.pageobjects.productpage.ProductPage;
import org.pageobjects.signinpage.SignInPage;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CheckoutPageTest {

    private WebDriver driver;

    private CheckoutPage checkoutPage;

    @BeforeEach
    void setUp() {
        driver = new ChromeDriver();

        // Signing in so the products are both on the same account
        SignInPage signInPage = new SignInPage(driver);
        signInPage.load();
        signInPage.isLoaded();
        signInPage.loginSuccessfully();
        signInPage.wasTheLoginSuccessful();

        // Adding a product, quantity: 1
        ProductPage productPage1 = new ProductPage(driver, "01J22WA8ZZ1WQRY0GZ86MP4XJX");
        driver.navigate().to(productPage1.url);
        productPage1.isLoaded();
        productPage1.waitForPageToLoad();
        productPage1.addToCart(1);

        // Adding a product, quantity: 3
        ProductPage productPage2 = new ProductPage(driver, "01J22WA901A0NM3TTZ3BRVSAQE");
        driver.navigate().to(productPage2.url);
        productPage2.isLoaded();
        productPage1.waitForPageToLoad();
        productPage2.increaseQuantity();
        productPage2.increaseQuantity();
        productPage2.increaseQuantity();
        productPage2.addToCart(5);

        checkoutPage = new CheckoutPage(driver);
        driver.navigate().to(checkoutPage.url);
        checkoutPage.isLoaded();
    }

    @AfterEach
    void tearDown() {
        driver.quit();
    }

    @Test
    void given2ItemsAddedToCheckout_whenAtCheckoutPage_thenThereAre2ItemsThere() {
        assertEquals(2, checkoutPage.getItems().size());
    }

    @Test
    void given2ItemsAddedToCheckout_whenAtCheckoutPage_thenTheyHaveRightNames() {
        String[] itemNames = { "Combination Pliers", "Pliers" };

        List<WebElement> items = checkoutPage.getItems();
        for (int i = 0; i < items.size(); i++) {
            assertEquals(checkoutPage.getItemName(items.get(i)), itemNames[i]);
        }
    }

    @Test
    void given2ItemsAddedToCheckout_whenAtCheckoutPage_thenTheQuantityInputsAreCorrect() {
        Integer[] itemNames = { 1, 4 };

        List<WebElement> items = checkoutPage.getItems();
        for (int i = 0; i < items.size(); i++) {
            assertEquals(checkoutPage.getQuantityInputValue(items.get(i)), itemNames[i]);
        }
    }

    @Test
    void given2ItemsAddedToCheckout_whenRemovingFirstItem_thenThereIsOnlyOneItem() {
        WebElement item = checkoutPage.getItems().get(0);

        checkoutPage.removeItem(item);

        assertEquals(1, checkoutPage.getItems().size());
    }

    @Test
    void givenOnCartPage_whenClickingProceedToCheckout_thenGoToCheckoutSignInPage() {
        checkoutPage.proceedToCheckout(1);

        assertFalse(checkoutPage.isProceedToCheckoutButtonVisible(1));
    }

    @Test
    void givenOnCheckoutSignInPage_whenClickingProceedToCheckout_thenGoToBillingAddressPage() {
        checkoutPage.proceedToCheckout(1);
        checkoutPage.proceedToCheckout(2);

        checkoutPage.fillOutBillingAddress("address", "city", "state", "country", "adxc zx1");

        checkoutPage.proceedToCheckout(3);

        assertTrue(checkoutPage.isProceedToCheckoutButtonVisible(4));
    }

    @Test
    void givenOnBillingAddressPage_whenClickingProceedToCheckout_thenPaymentPage() {
        checkoutPage.proceedToCheckout(1);
        checkoutPage.proceedToCheckout(2);

        checkoutPage.fillOutBillingAddress("address", "city", "state", "country", "adxc zx1");
        checkoutPage.proceedToCheckout(3);

        checkoutPage.fillOutPaymentMethod(CheckoutPage.PaymentMethod.CASH_ON_DELIVERY);
        checkoutPage.proceedToCheckout(4);

        assertTrue(checkoutPage.wasPaymentSuccessful());
    }
}