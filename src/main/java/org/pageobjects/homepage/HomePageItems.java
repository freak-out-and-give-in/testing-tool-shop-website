package org.pageobjects.homepage;

import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.LoadableComponent;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class HomePageItems extends LoadableComponent<HomePageItems> {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    private WebDriver driver;

    @FindBy(css = ".card")
    private List<WebElement> items;

    public HomePageItems(WebDriver driver) {
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

    private void waitForCardsToLoad() {
        log.debug("Waiting for cards to load");

        Wait<WebDriver> wait = new WebDriverWait(driver, Duration.ofSeconds(2));

        // Waits until the cards have loaded
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("card")));
    }

    private void waitForCardsToLoadForSort() {
        log.debug("Waiting for cards to load for sorting");

        Wait<WebDriver> wait = new WebDriverWait(driver, Duration.ofSeconds(2));
        String originalItemName = items.get(0).findElement(By.className("card-title")).getText();

        // This is for sorting, as once you click a sorting option it takes a bit to load,
        // and during that loading the cards are removed temporarily, so when it tries to find the first card it
        // returns a stale element reference exception.
        // To fix this we just catch the exception and look again, with 2 attempts.
        for (int attempts = 0; attempts < 2; attempts++) {
            try {
                // Waits until the expected first card has the given name
                wait.until(ExpectedConditions.not(ExpectedConditions.textToBePresentInElement(
                        items.get(0).findElement(By.className("card-title")), originalItemName)));
                break;
            } catch(StaleElementReferenceException ignored) {}
        }
    }

    public double getFirstItemCost() {
        log.debug("Getting the first item's cost");

        waitForCardsToLoad();
        WebElement firstItem = items.get(0).findElement(By.className("card-footer"));

        return Double.parseDouble(firstItem.getText().replace("$", ""));
    }

    // Flag is for whether we need to wait for new items to load, active currently for sorting and searching.
    public String getFirstItemName(boolean loadNewItemsFlag) {
        log.debug("Getting the first item's name");

        waitForCardsToLoad();
        if (loadNewItemsFlag) {
            waitForCardsToLoadForSort();
        }

        return items.get(0).findElement(By.className("card-title")).getText();
    }
}
