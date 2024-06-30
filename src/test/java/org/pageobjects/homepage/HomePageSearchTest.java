package org.pageobjects.homepage;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import static org.junit.jupiter.api.Assertions.*;

class HomePageSearchTest {

    private WebDriver driver;

    private HomePageSearch homePageSearch;

    @BeforeEach
    void setUp() {
        driver = new ChromeDriver();

        homePageSearch = new HomePageSearch(driver);
        homePageSearch.load();
        homePageSearch.isLoaded();
    }

    @AfterEach
    void tearDown() {
        driver.quit();
    }

    @Test
    void givenHomePage_whenSearching_thenSearchShouldBeSuccessful() {
        String itemNameToSearchFor = "Thor Hammer";

        HomePageItems homePageItems = homePageSearch.searchFor(itemNameToSearchFor);

        assertEquals(itemNameToSearchFor, homePageItems.getFirstItemName(true));
    }
}