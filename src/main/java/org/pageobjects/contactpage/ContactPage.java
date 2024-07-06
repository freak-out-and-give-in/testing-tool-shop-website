package org.pageobjects.contactpage;

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

public class ContactPage extends LoadableComponent<ContactPage> {

    private WebDriver driver;

    @FindBy(id = "first_name")
    private WebElement firstNameInput;

    @FindBy(id = "last_name")
    private WebElement lastNameInput;

    @FindBy(id = "email")
    private WebElement emailAddressInput;

    @FindBy(id = "subject")
    private WebElement subjectSelectInput;

    @FindBy(id = "message")
    private WebElement messageInput;

    @FindBy(id = "attachment")
    private WebElement attachFileInput;

    @FindBy(className = "btnSubmit")
    private WebElement sendButton;

    public ContactPage(WebDriver driver) {
        this.driver = driver;

        PageFactory.initElements(driver, this);
    }

    @Override
    protected void load() {
        driver.get("https://practicesoftwaretesting.com/contact");
    }

    @Override
    protected void isLoaded() throws Error {
        String url = driver.getCurrentUrl();
        assertTrue(url.endsWith("contact"), "Not on the Contact page: " + url);
    }

    public void writeFirstNameAs(String firstName) {
        firstNameInput.sendKeys(firstName);
    }

    public String getFirstName() {
        return firstNameInput.getAttribute("value");
    }

    public void writeLastNameAs(String lastName) {
        lastNameInput.sendKeys(lastName);
    }

    public String getLastName() {
        return lastNameInput.getAttribute("value");
    }

    public void writeEmailAddressAs(String emailAddress) {
        emailAddressInput.sendKeys(emailAddress);
    }

    public String getEmailAddress() {
        return emailAddressInput.getAttribute("value");
    }

    public void selectSubjectAs(SubjectOption subject) {
        subjectSelectInput.click();

        WebElement subjectOption = driver.findElement(By.cssSelector("option[value='" + subject.value + "']"));
        subjectOption.click();
    }

    public String getSubjectValue() {
        return subjectSelectInput.getAttribute("value");
    }

    public void writeMessageAs(String message) {
        messageInput.sendKeys(message);
    }

    public String getMessage() {
        return messageInput.getAttribute("value");
    }

    public void sendContactForm() {
        sendButton.click();
    }

    public boolean sendingTheFormWasSuccessful() {
        Wait<WebDriver> wait = new WebDriverWait(driver, Duration.ofSeconds(2));

        try {
            wait.until(ExpectedConditions.invisibilityOf(firstNameInput));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public enum SubjectOption {
        CUSTOMER_SERVICE("customer-service"),
        WEBMASTER("webmaster"),
        RETURN("return"),
        PAYMENTS("payments"),
        WARRANTY("warranty"),
        STATUS_OF_MY_ORDER("status-of-order");

        private final String value;

        SubjectOption(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }
}
