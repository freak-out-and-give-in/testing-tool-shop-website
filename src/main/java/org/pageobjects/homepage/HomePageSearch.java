package org.pageobjects.homepage;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.LoadableComponent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class HomePageSearch extends LoadableComponent<HomePageSearch> {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    private WebDriver driver;

    @FindBy(id = "search-query")
    private WebElement searchBar;

    @FindBy(css = "button[data-test='search-submit']")
    private WebElement searchButton;

    public HomePageSearch(WebDriver driver) {
        this.driver = driver;

        PageFactory.initElements(driver, this);
    }

    @Override
    protected void load() {
        driver.get("https://practicesoftwaretesting.com/#/");
    }

    @Override
    protected void isLoaded() throws Error {
        String url = driver.getCurrentUrl();
        assertTrue(url.endsWith("#/"), "Not on the home page: " + url);
    }

    public HomePageItems searchFor(String itemNameToSearchFor) {
        log.debug("Searching for {}", itemNameToSearchFor);

        searchBar.clear();
        searchBar.sendKeys(itemNameToSearchFor);
        searchButton.click();

        return new HomePageItems(driver);
    }
}
