
package com.cura.tests;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class bookAppointment {

    WebDriver driver;
    WebDriverWait wait;

    final String BASE_URL =
            "https://katalon-demo-cura.herokuapp.com/";

    final String USERNAME = "John Doe";
    final String PASSWORD = "ThisIsNotAPassword";
    final String VISIT_DATE = "30/10/2026";

    @BeforeMethod
    public void setUp() {

        driver = new ChromeDriver();

        driver.manage().window().maximize();

        driver.manage().timeouts()
                .implicitlyWait(Duration.ofSeconds(0));

        wait = new WebDriverWait(
                driver, Duration.ofSeconds(20));

        driver.get(BASE_URL);

        System.out.println("CURA application opened.");
    }

    // Login with valid credentials
    public void loginWithValidCredentials() {

        wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//*[@id='btn-make-appointment']")))
                .click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//*[@id='txt-username']")))
                .sendKeys(USERNAME);

        driver.findElement(
                By.xpath("//*[@id='txt-password']"))
                .sendKeys(PASSWORD);

        driver.findElement(
                By.xpath("//*[@id='btn-login']"))
                .click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//*[@id='combo_facility']")));

        System.out.println("PASS: Login successful.");
    }

    // Book an appointment
    public void bookAppointment() {

        loginWithValidCredentials();

        // Select facility
        WebElement facilityElement = wait.until(
                ExpectedConditions.elementToBeClickable(
                        By.xpath("//*[@id='combo_facility']")));

        Select facility = new Select(facilityElement);

        facility.selectByVisibleText(
                "Hongkong CURA Healthcare Center");

        // Select hospital readmission
        WebElement readmission = wait.until(
                ExpectedConditions.elementToBeClickable(
                        By.xpath("//*[@id='chk_hospotal_readmission']")));

        if (!readmission.isSelected()) {
            readmission.click();
        }

        // Select Medicaid
        wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//*[@id='radio_program_medicaid']")))
                .click();

        // Enter visit date
        WebElement dateField = wait.until(
                ExpectedConditions.elementToBeClickable(
                        By.xpath("//*[@id='txt_visit_date']")));

        dateField.click();

        dateField.sendKeys(
                Keys.chord(Keys.CONTROL, "a"));

        dateField.sendKeys(Keys.BACK_SPACE);

        dateField.sendKeys(VISIT_DATE);

        dateField.sendKeys(Keys.TAB);

        // Verify entered date
        String enteredDate =
                dateField.getAttribute("value");

        System.out.println(
                "Entered date: " + enteredDate);

        Assert.assertEquals(
                enteredDate,
                VISIT_DATE,
                "Visit date was not entered correctly.");

        // Enter comments
        WebElement commentField = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("//*[@id='txt_comment']")));

        commentField.clear();

        commentField.sendKeys(
                "Appointment booked through Selenium.");

        // Submit appointment
        wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//*[@id='btn-book-appointment']")))
                .click();

        // Wait for confirmation page URL
        wait.until(ExpectedConditions.urlContains(
                "appointment.php"));

        // Verify confirmation heading
        WebElement confirmationHeading = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.xpath(
                                "//h2[contains(normalize-space(.),"
                                + "'Appointment Confirmation')]")));

        Assert.assertTrue(
                confirmationHeading.isDisplayed(),
                "Appointment confirmation heading is missing.");

        System.out.println(
                "PASS: Appointment booked successfully.");
    }

    // TC04: Verify appointment booking and confirmation
    @Test
    public void verifyAppointmentBooking() {

        bookAppointment();

        String pageText = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("//body")))
                .getText();

        Assert.assertTrue(
                pageText.contains("Appointment Confirmation"),
                "Confirmation heading is missing.");

        Assert.assertTrue(
                pageText.contains(
                        "Hongkong CURA Healthcare Center"),
                "Facility is missing.");

        Assert.assertTrue(
                pageText.contains("Medicaid"),
                "Healthcare program is missing.");

        Assert.assertTrue(
                pageText.contains(VISIT_DATE),
                "Visit date is missing.");

        Assert.assertTrue(
                pageText.contains(
                        "Appointment booked through Selenium."),
                "Comments are missing.");

        System.out.println(
                "PASS: Appointment details verified.");
    }

    // TC05: Verify appointment history
    @Test
    public void verifyAppointmentHistory() {

        // Book appointment first
        bookAppointment();

        // Open sidebar menu
        wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//*[@id='menu-toggle']")))
                .click();

        // Locate History link using XPath
        By historyLink = By.xpath(
                "//*[@id='sidebar-wrapper']"
                + "//a[contains(@href,'history.php')]");

        WebElement history = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        historyLink));

        // Scroll History link into view
        ((org.openqa.selenium.JavascriptExecutor) driver)
                .executeScript(
                        "arguments[0].scrollIntoView({block:'center'});",
                        history);

        // Click History
        wait.until(ExpectedConditions.elementToBeClickable(
                historyLink)).click();

        // Wait for History page
        wait.until(ExpectedConditions.urlContains(
                "history.php"));

        Assert.assertTrue(
                driver.getCurrentUrl().contains("history.php"),
                "History page did not open.");

        // Verify page content
        String historyText = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("//body")))
                .getText();

        Assert.assertTrue(
                historyText.contains("History"),
                "History page heading/content is missing.");

        System.out.println(
                "PASS: Appointment history page opened.");
    }

    // Close browser after every test
    @AfterMethod(alwaysRun = true)
    public void tearDown() {

        if (driver != null) {
            driver.quit();
        }
    }
}
