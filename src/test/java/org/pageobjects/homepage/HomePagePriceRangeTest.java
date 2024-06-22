package org.pageobjects.homepage;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import static org.junit.jupiter.api.Assertions.*;

class HomePage_PriceRangeTest {

    private WebDriver driver;

    private HomePage_PriceRange homePagePriceRange;

    @BeforeEach
    void setUp() {
        driver = new ChromeDriver();

        homePagePriceRange = new HomePage_PriceRange(driver);
        homePagePriceRange.load();
        homePagePriceRange.isLoaded();
    }

    @AfterEach
    void tearDown() {
        driver.quit();
    }

    @Test
    void givenHomePage_whenChangingLowerAndUpperPriceRange_thenPriceRangeShouldChangeAccordingly() {
        int lowerPriceRange = 12;
        int higherPriceRange = 85;

        homePagePriceRange.changePriceRange(lowerPriceRange, higherPriceRange);

        assertEquals(homePagePriceRange.getLowerPriceRange(), lowerPriceRange);
        assertEquals(homePagePriceRange.getUpperPriceRange(), higherPriceRange);
    }

}