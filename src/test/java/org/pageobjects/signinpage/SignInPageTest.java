package org.pageobjects.signinpage;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import static org.junit.jupiter.api.Assertions.*;
class SignInPageTest {

    private WebDriver driver;

    private SignInPage signInPage;

    @BeforeEach
    void setUp() {
        driver = new ChromeDriver();

        signInPage = new SignInPage(driver);
        signInPage.load();
        signInPage.isLoaded();
    }

    @AfterEach
    void tearDown() {
        driver.quit();
    }

    @Test
    void loginAs() {
        signInPage.loginSuccessfully();

        assertTrue(signInPage.wasTheLoginSuccessful());
    }
}