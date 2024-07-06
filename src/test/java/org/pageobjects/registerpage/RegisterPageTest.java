package org.pageobjects.registerpage;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import static org.junit.jupiter.api.Assertions.*;

class RegisterPageTest {

    private WebDriver driver;

    private RegisterPage registerPage;

    @BeforeEach
    void setUp() {
        driver = new ChromeDriver();

        registerPage = new RegisterPage(driver);
        registerPage.load();
        registerPage.isLoaded();
    }

    @AfterEach
    void tearDown() {
        driver.quit();
    }

    @Test
    void registerAs() {
        registerPage.registerAs("firsn", "lastn", "14/03/1994", "address", "postc",
                "city", "state", "Andorra", "90123", "email@address", "pa3FTJ**2sd");

        assertTrue(registerPage.wasRegisterSuccessful());
    }
}