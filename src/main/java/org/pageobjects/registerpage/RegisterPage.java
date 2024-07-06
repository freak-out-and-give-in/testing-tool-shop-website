package org.pageobjects.registerpage;

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

import static org.junit.jupiter.api.Assertions.assertTrue;

public class RegisterPage extends LoadableComponent<RegisterPage> {

    private WebDriver driver;

    @FindBy(id = "first_name")
    private WebElement firstNameInput;

    @FindBy(id = "last_name")
    private WebElement lastNameInput;

    @FindBy(id = "dob")
    private WebElement dateOfBirthInput;

    @FindBy(id = "address")
    private WebElement addressInput;

    @FindBy(id = "postcode")
    private WebElement postcodeInput;

    @FindBy(id = "city")
    private WebElement cityInput;

    @FindBy(id = "state")
    private WebElement stateInput;

    @FindBy(css = "select[data-test='country']")
    private WebElement countrySelectInput;

    @FindBy(id = "phone")
    private WebElement phoneInput;

    @FindBy(id = "email")
    private WebElement emailInput;

    @FindBy(id = "password")
    private WebElement passwordInput;

    @FindBy(css = "button[data-test='register-submit']")
    private WebElement registerButton;

    public RegisterPage(WebDriver driver) {
        this.driver = driver;

        PageFactory.initElements(driver, this);
    }

    @Override
    protected void load() {
        driver.get("https://practicesoftwaretesting.com/auth/register");
    }

    @Override
    protected void isLoaded() throws Error {
        String url = driver.getCurrentUrl();
        assertTrue(url.endsWith("register"), "Not on the Register page: " + url);
    }

    public void registerAs(String firstName, String lastName, String dateOfBirth, String address, String postcode, String city,
                           String state, String country, String phone, String email, String password) {
        firstNameInput.sendKeys(firstName);
        lastNameInput.sendKeys(lastName);
        dateOfBirthInput.sendKeys(dateOfBirth);
        addressInput.sendKeys(address);
        postcodeInput.sendKeys(postcode);
        cityInput.sendKeys(city);
        stateInput.sendKeys(state);

        countrySelectInput.click();
        getCountryOption(country).click();

        phoneInput.sendKeys(phone);
        emailInput.sendKeys(email);
        passwordInput.sendKeys(password);

        registerButton.click();
    }

    public WebElement getCountryOption(String country) {
        for (WebElement option : countrySelectInput.findElements(By.tagName("option"))) {
            if (option.getAttribute("textContent").equals(country)) {
                return option;
            }
        }

        throw new IllegalArgumentException("No such country exists");
    }

    public boolean wasRegisterSuccessful() {
        Wait<WebDriver> wait = new WebDriverWait(driver, Duration.ofSeconds(2));
        try {
            wait.until(ExpectedConditions.invisibilityOf(firstNameInput));
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
