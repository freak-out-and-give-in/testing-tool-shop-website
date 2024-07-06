package org.pageobjects.checkoutpage;

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

public class CheckoutPage extends LoadableComponent<CheckoutPage> {

    private WebDriver driver;

    public String url = "https://practicesoftwaretesting.com/checkout";

    @FindBy(css = "button[data-test='proceed-1']")
    private WebElement proceedToCheckoutButton1;

    @FindBy(css = "button[data-test='proceed-2']")
    private WebElement proceedToCheckoutButton2;

    @FindBy(css = "button[data-test='proceed-3']")
    private WebElement proceedToCheckoutButton3;

    @FindBy(css = "button[data-test='finish']")
    private WebElement proceedToCheckoutButton4;

    public CheckoutPage(WebDriver driver) {
        this.driver = driver;

        PageFactory.initElements(driver, this);
    }

    @Override
    protected void load() {
        driver.get("https://practicesoftwaretesting.com/checkout");
    }

    @Override
    protected void isLoaded() throws Error {
        String url = driver.getCurrentUrl();
        assertTrue(url.endsWith("checkout"), "Not on the checkout page: " + url);
    }

    public List<WebElement> getItems() {
        Wait<WebDriver> wait = new WebDriverWait(driver, Duration.ofSeconds(1));
        wait.until(ExpectedConditions.elementToBeClickable(proceedToCheckoutButton1));

        return driver.findElements(By.cssSelector("tbody > tr"));
    }

    public String getItemName(WebElement item) {
        return item.findElement(By.className("product-title")).getText().trim();
    }

    public int getQuantityInputValue(WebElement item) {
        return Integer.parseInt(item.findElement(By.className("quantity")).getAttribute("value"));
    }

    public void removeItem(WebElement item) {
        item.findElement(By.className("fa-remove")).click();

        Wait<WebDriver> wait = new WebDriverWait(driver, Duration.ofSeconds(2));
        wait.until(ExpectedConditions.invisibilityOf(item));
    }

    public void fillOutBillingAddress(String address, String city, String state, String country, String postcode) {
        WebElement addressInput = driver.findElement(By.id("address"));
        WebElement cityInput = driver.findElement(By.id("city"));
        WebElement stateInput = driver.findElement(By.id("state"));
        WebElement countryInput = driver.findElement(By.id("country"));
        WebElement postcodeInput = driver.findElement(By.id("postcode"));

        addressInput.clear();
        addressInput.sendKeys(address);

        cityInput.clear();
        cityInput.sendKeys(city);

        stateInput.clear();
        stateInput.sendKeys(state);

        countryInput.clear();
        countryInput.sendKeys(country);

        postcodeInput.clear();
        postcodeInput.sendKeys(postcode);
    }

    public void fillOutPaymentMethod(PaymentMethod paymentMethod) {
        WebElement selectPayment = driver.findElement(By.id("payment-method"));
        selectPayment.click();

        for(WebElement option : selectPayment.findElements(By.tagName("option"))) {
            if (option.getAttribute("value").equals(paymentMethod.value)) {
                option.click();
                break;
            }
        }
    }

    public enum PaymentMethod {
        BANK_TRANSFER("bank-transfer"),
        CASH_ON_DELIVERY("cash-on-delivery"),
        CREDIT_CARD("credit-card"),
        BUY_NOW_PAY_LATER("buy-now-pay-later"),
        GIFT_CARD("gift-card");

        private String value;

        PaymentMethod(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    public boolean wasPaymentSuccessful() {
        Wait<WebDriver> wait = new WebDriverWait(driver, Duration.ofSeconds(2));
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("help-block")));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public void proceedToCheckout(int stage) {
        WebElement button = getButtonFromStage(stage);

        Wait<WebDriver> wait = new WebDriverWait(driver, Duration.ofSeconds(2));
        wait.until(ExpectedConditions.visibilityOf(button));

        button.click();
    }

    public boolean isProceedToCheckoutButtonVisible(int stage) {
        try {
            Wait<WebDriver> wait = new WebDriverWait(driver, Duration.ofSeconds(2));
            wait.until(ExpectedConditions.visibilityOf(getButtonFromStage(stage)));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private WebElement getButtonFromStage(int stage) {
        WebElement button = null;
        switch (stage) {
            case 1: {
                button = proceedToCheckoutButton1;
                break;
            }

            case 2: {
                button = proceedToCheckoutButton2;
                break;
            }

            case 3: {
                button = proceedToCheckoutButton3;
                break;
            }

            case 4: {
                button = proceedToCheckoutButton4;
                break;
            }
        }

        return button;
    }
}
