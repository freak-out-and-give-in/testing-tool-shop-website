package org.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.LoadableComponent;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class BasePage extends LoadableComponent<BasePage> {

    private WebDriver driver;

    static final String url = "https://practicesoftwaretesting.com/";

    public BasePage(WebDriver driver) {
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
        assertTrue(url.startsWith("https://practicesoftwaretesting.com/"), "Not on the starting page: " + url);
    }

    public void signOut() {
        Wait<WebDriver> wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("menu")));

        WebElement profileName = driver.findElement(By.id("menu"));
        profileName.click();

        WebElement signOut = driver.findElement(By.cssSelector("a[data-test='nav-sign-out']"));
        signOut.click();
    }

    public boolean doesTheProfileOptionExist() {
        try {
            driver.findElement(By.id("menu"));
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
