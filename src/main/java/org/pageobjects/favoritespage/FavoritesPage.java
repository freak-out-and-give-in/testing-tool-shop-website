package org.pageobjects.favoritespage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.LoadableComponent;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class FavoritesPage extends LoadableComponent<FavoritesPage> {

    private WebDriver driver;

    public static final String  url = "https://practicesoftwaretesting.com/account/favorites";

    @FindBy(className = "card")
    private List<WebElement> items;

    public FavoritesPage(WebDriver driver) {
        this.driver = driver;

        PageFactory.initElements(driver, this);
    }

    @Override
    protected void load() {
        driver.get(url);
    }

    @Override
    protected void isLoaded() throws Error {
        String url = driver.getCurrentUrl();
        assertTrue(url.endsWith("favorites"), "Not on the Favorites page: " + url);
    }

    public void waitUntilLoaded_OnlyIfNotEmpty() {
        Wait<WebDriver> wait = new WebDriverWait(driver, Duration.ofSeconds(2));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("card")));
    }

    public List<WebElement> getItems() {
        return items;
    }

    public void removeItem(WebElement item) {
        item.findElement(By.className("fa-remove")).click();

        Wait<WebDriver> wait = new WebDriverWait(driver, Duration.ofSeconds(2));
        wait.until(ExpectedConditions.invisibilityOf(item));
    }

    public boolean isThisItemHere(String name) {
        for (WebElement item : getItems()) {
            String loopName = item.findElement(By.className("card-title")).getText();

            if (name.equals(loopName)) {
                return true;
            }
        }

        return false;
    }
}
