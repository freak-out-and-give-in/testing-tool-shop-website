package org.pageobjects.homepage;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import static org.junit.jupiter.api.Assertions.*;

class HomePageItemsTest {

    private WebDriver driver;

    private HomePageItems homePageItems;

    @BeforeEach
    void setUp() {
        driver = new ChromeDriver();

        homePageItems = new HomePageItems(driver);
        homePageItems.load();
        homePageItems.isLoaded();
    }

    @AfterEach
    void tearDown() {
        driver.quit();
    }

    @Test
    void givenHomePage_whenCheckingTheFirstItem_thenReturnItsName() {
        String expectedFirstItemName = " Combination Pliers ";

        assertEquals(expectedFirstItemName.trim(), homePageItems.getFirstItemName(expectedFirstItemName));
    }
}