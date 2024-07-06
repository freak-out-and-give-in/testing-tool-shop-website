package org.pageobjects.signinpage;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.LoadableComponent;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class SignInPage extends LoadableComponent<SignInPage> {

    private WebDriver driver;

    @FindBy(id = "email")
    private WebElement emailInput;

    @FindBy(id = "password")
    private WebElement passwordInput;

    @FindBy(css = "input[data-test='login-submit']")
    private WebElement loginButton;

    public SignInPage(WebDriver driver) {
        this.driver = driver;

        PageFactory.initElements(driver, this);
    }

    @Override
    public void load() {
        driver.get("https://practicesoftwaretesting.com/auth/login");
    }

    @Override
    public void isLoaded() throws Error {
        String url = driver.getCurrentUrl();
        assertTrue(url.endsWith("login"), "Not on the login page: " + url);
    }

    public void loginSuccessfully() {
        emailInput.sendKeys("customer@practicesoftwaretesting.com");
        passwordInput.sendKeys("welcome01");

        loginButton.click();
    }

    public boolean wasTheLoginSuccessful() {
        Wait<WebDriver> wait = new WebDriverWait(driver, Duration.ofSeconds(3));
        try {
            wait.until(ExpectedConditions.invisibilityOf(emailInput));
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
