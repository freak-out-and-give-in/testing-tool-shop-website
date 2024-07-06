package org.pageobjects;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.pageobjects.signinpage.SignInPage;

import static org.junit.jupiter.api.Assertions.*;

class BasePageTest {

    private WebDriver driver;

    private BasePage basePage;

    @BeforeEach
    void setUp() {
        driver = new ChromeDriver();

        // Signing in so the products are both on the same account
        SignInPage signInPage = new SignInPage(driver);
        signInPage.load();
        signInPage.isLoaded();
        signInPage.loginSuccessfully();
        signInPage.wasTheLoginSuccessful();

        basePage = new BasePage(driver);
        driver.navigate().to(BasePage.url);
        basePage.isLoaded();
    }

    @AfterEach
    void tearDown() {
        driver.quit();
    }

    @Test
    void givenBasePage_whenClickingSignOut_thenSignOut() {
        basePage.signOut();

        assertFalse(basePage.doesTheProfileOptionExist());
    }
}