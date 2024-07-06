package org.pageobjects.contactpage;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import static org.junit.jupiter.api.Assertions.*;

class ContactPageTest {

    private WebDriver driver;

    private ContactPage contactPage;

    @BeforeEach
    void setUp() {
        this.driver = new ChromeDriver();

        contactPage = new ContactPage(driver);
        contactPage.load();
        contactPage.isLoaded();
    }

    @AfterEach
    void tearDown() {
        driver.quit();
    }

    @Test
    void givenContactPage_whenWritingFirstName_thenInputShouldMatch() {
        String firstName = "bane";

        contactPage.writeFirstNameAs(firstName);

        assertEquals(firstName, contactPage.getFirstName());
    }

    @Test
    void givenContactPage_whenWritingLastName_thenInputShouldMatch() {
        String lastName = "lame";

        contactPage.writeLastNameAs(lastName);

        assertEquals(lastName, contactPage.getLastName());
    }

    @Test
    void givenContactPage_whenWritingEmailAddress_thenInputShouldMatch() {
        String emailAddress = "email@address.com";

        contactPage.writeEmailAddressAs(emailAddress);

        assertEquals(emailAddress, contactPage.getEmailAddress());
    }

    @Test
    void givenContactPage_whenSelectingSubject_thenSelectionShouldWork() {
        ContactPage.SubjectOption subject = ContactPage.SubjectOption.WARRANTY;

        contactPage.selectSubjectAs(subject);

        assertEquals(subject.getValue(), contactPage.getSubjectValue());
    }

    @Test
    void givenContactPage_whenWritingMessage_thenInputShouldMatch() {
        String message = "writing to inform you of lorem";

        contactPage.writeMessageAs(message);

        assertEquals(message, contactPage.getMessage());
    }

    @Nested
    class SendingForm {

        @Test
        void givenContactPage_whenEnteringAllDetails_thenFormShouldSend() {
            contactPage.writeFirstNameAs("firstn");
            contactPage.writeLastNameAs("lastn");
            contactPage.writeEmailAddressAs("emailadd@here");
            contactPage.selectSubjectAs(ContactPage.SubjectOption.RETURN);
            contactPage.writeMessageAs("writing a message herewriting a message herewriting a message herewriting a message here");

            contactPage.sendContactForm();

            assertTrue(contactPage.sendingTheFormWasSuccessful());
        }

        @Test
        void givenContactPage_whenEnteringNoDetails_thenFormShouldNotSend() {
            contactPage.sendContactForm();

            assertFalse(contactPage.sendingTheFormWasSuccessful());
        }
    }
}