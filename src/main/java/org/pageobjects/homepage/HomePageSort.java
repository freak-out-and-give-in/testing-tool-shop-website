package org.pageobjects.homepage;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.LoadableComponent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class HomePageSort extends LoadableComponent<HomePageSort> {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    private WebDriver driver;

    @FindBy(className = "form-select")
    private WebElement sortSelect;

    @FindBy(css = ".form-select > option")
    private List<WebElement> sortOptions;

    public HomePageSort(WebDriver driver) {
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

    public HomePageItems sortBy(Sort sort) {
        log.debug("Sort by {}", sort);

        sortSelect.click();

        WebElement option = getSortOptionByValue(sort.getValue());
        option.click();

        return new HomePageItems(driver);
    }

    private WebElement getSortOptionByValue(String value) {
        log.debug("Getting the sort option by the value {}", value);

        for (WebElement option : sortOptions) {
            if (option.getAttribute("value").equals(value)) {
                return option;
            }
        }

        throw new NoSuchElementException("The option with the value: " + value + ", does not exist");
    }

    public enum Sort {
        A_TO_Z("name,asc"),
        Z_TO_A("name,desc"),
        PRICE_HIGH_TO_LOW("price,desc"),
        PRICE_LOW_TO_HIGH("price,asc");

        private final String value;

        Sort(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }
}
