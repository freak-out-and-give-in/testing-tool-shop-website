package org.pageobjects.favoritespage;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.pageobjects.productpage.ProductPage;
import org.pageobjects.signinpage.SignInPage;

import static org.junit.jupiter.api.Assertions.*;

class FavoritesPageTest {

    private WebDriver driver;

    private FavoritesPage favoritesPage;

    @BeforeEach
    void setUp() {
        driver = new ChromeDriver();

        // Signing in so the products are both on the same account
        SignInPage signInPage = new SignInPage(driver);
        signInPage.load();
        signInPage.isLoaded();
        signInPage.loginSuccessfully();
        signInPage.wasTheLoginSuccessful();

        favoritesPage = new FavoritesPage(driver);
        driver.navigate().to(FavoritesPage.url);
        favoritesPage.isLoaded();
    }

    @AfterEach
    void tearDown() {
        driver.quit();
    }

    @Test
    void givenFavoritesPageWith3Products_whenCountingAmount_thenThereAre3() {
        assertEquals(3, favoritesPage.getItems().size());
    }

    @Test
    void givenFavoritesPageWith3Products_whenRemovingAnItem_thenThereAre2() {
        favoritesPage.removeItem(favoritesPage.getItems().get(0));

        assertEquals(2, favoritesPage.getItems().size());
    }

    @Test
    void givenFavoritesPageWith3Products_whenAddedProductToFavorite_thenThisItemIsHere() {
        String productId = "01J22WA904JG040HBP052Y9QWH";
        String productName = "Long Nose Pliers";

        ProductPage productPage = new ProductPage(driver, productId);
        driver.navigate().to(productPage.url);
        productPage.isLoaded();

        productPage.addToFavorites();

        favoritesPage = new FavoritesPage(driver);
        driver.navigate().to(FavoritesPage.url);
        favoritesPage.isLoaded();
        favoritesPage.waitUntilLoaded_OnlyIfNotEmpty();

        assertTrue(favoritesPage.isThisItemHere(productName));
    }
}