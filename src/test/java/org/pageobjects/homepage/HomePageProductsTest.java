package org.pageobjects.homepage;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import static org.junit.jupiter.api.Assertions.*;

class HomePageProductsTest {

    private WebDriver driver;

    private HomePageProducts homePageProducts;

    @BeforeEach
    void setUp() {
        driver = new ChromeDriver();

        homePageProducts = new HomePageProducts(driver);
        homePageProducts.load();
        homePageProducts.isLoaded();
    }

    @AfterEach
    void tearDown() {
        driver.quit();
    }

    @Test
    void givenHomePage_whenCheckingTheFirstItem_thenReturnItsName() {
        assertEquals("Combination Pliers", homePageProducts.getFirstProductName(false));
    }
}