package org.pageobjects.homepage;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import static org.junit.jupiter.api.Assertions.assertEquals;

class HomePageSortTest {

    private WebDriver driver;

    private HomePageSort homePageSort;

    @BeforeEach
    void setUp() {
        driver = new ChromeDriver();

        homePageSort = new HomePageSort(driver);
        homePageSort.load();
        homePageSort.isLoaded();
    }

    @AfterEach
    void tearDown() {
        driver.quit();
    }

    @Test
    void givenHomePage_whenSortingByAlphabetical_thenItemsShouldBeSortedAlphabetically() {
        HomePageItems homePageItems = homePageSort.sortBy(HomePageSort.Sort.Z_TO_A);

        assertEquals("Wood Saw", homePageItems.getFirstItemName(true));
    }
}