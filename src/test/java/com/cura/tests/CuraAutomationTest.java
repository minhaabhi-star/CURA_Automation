
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

public class CuraAutomationTest {

    WebDriver driver;
    WebDriverWait wait;

    String baseUrl = "https://katalon-demo-cura.herokuapp.com/";
    String username = "John Doe";
    String password = "ThisIsNotAPassword";
    String visitDate = "30/10/2026";

    @BeforeMethod
    public void setUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();

        driver.manage().timeouts()
              .implicitlyWait(Duration.ofSeconds(0));

        wait = new WebDriverWait(driver, Duration.ofSeconds(15));

        driver.get(baseUrl);
    }

    // Login using valid credentials
    public void loginWithValidCredentials() {
        wait.until(ExpectedConditions.elementToBeClickable(
                By.id("btn-make-appointment"))).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.id("txt-username"))).sendKeys(username);

        driver.findElement(By.id("txt-password")).sendKeys(password);

        wait.until(ExpectedConditions.elementToBeClickable(
                By.id("btn-login"))).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.id("combo_facility")));
    }

    // Reusable appointment booking method
    public void bookAppointment() {

        loginWithValidCredentials();

        // Select facility
        Select facility = new Select(
                wait.until(ExpectedConditions.elementToBeClickable(
                        By.id("combo_facility"))));

        facility.selectByVisibleText("Hongkong CURA Healthcare Center");

        // Hospital readmission checkbox
        WebElement readmission =
                driver.findElement(By.id("chk_hospotal_readmission"));

        if (!readmission.isSelected()) {
            readmission.click();
        }

        // Select Medicaid
        driver.findElement(By.id("radio_program_medicaid")).click();

        // Enter visit date
        WebElement dateField = wait.until(
                ExpectedConditions.elementToBeClickable(
                        By.id("txt_visit_date")));

        dateField.click();
        dateField.sendKeys(Keys.chord(Keys.CONTROL, "a"));
        dateField.sendKeys(visitDate);
        dateField.sendKeys(Keys.TAB);

        // Confirm date was entered
        String enteredDate = dateField.getAttribute("value");

        Assert.assertEquals(
                enteredDate,
                visitDate,
                "Visit date was not entered correctly.");

        // Enter comments
        driver.findElement(By.id("txt_comment"))
              .sendKeys("Appointment booked through Selenium.");

        // Submit appointment
        wait.until(ExpectedConditions.elementToBeClickable(
                By.id("btn-book-appointment"))).click();

        // Wait for confirmation page
        wait.until(ExpectedConditions.or(
                ExpectedConditions.urlContains("appointment.php"),
                ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("//h2[contains(normalize-space(.),"
                                + "'Appointment Confirmation')]"))
        ));

        // Verify confirmation
        Assert.assertTrue(
                driver.getPageSource().contains("Appointment Confirmation")
                || driver.getCurrentUrl().contains("appointment.php"),
                "Appointment confirmation page did not open.");
    }

    @Test
    public void verifyHomePage() {
        Assert.assertTrue(
                wait.until(ExpectedConditions.visibilityOfElementLocated(
                        By.id("btn-make-appointment"))).isDisplayed(),
                "Make Appointment button is not displayed.");
    }

    @Test
    public void verifyValidLogin() {
        loginWithValidCredentials();

        Assert.assertTrue(
                driver.findElement(By.id("combo_facility")).isDisplayed(),
                "Login failed with valid credentials.");
    }

    @Test
    public void verifyInvalidLogin() {
        driver.findElement(By.id("btn-make-appointment")).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.id("txt-username"))).sendKeys("WrongUser");

        driver.findElement(By.id("txt-password"))
              .sendKeys("WrongPassword");

        driver.findElement(By.id("btn-login")).click();

        WebElement errorMessage = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.cssSelector(".text-danger")));

        Assert.assertTrue(
                errorMessage.getText().contains("Login failed"),
                "Invalid login error message was not displayed.");
    }

    @Test
    public void verifyAppointmentBooking() {
        bookAppointment();

        Assert.assertTrue(
                driver.getPageSource().contains("Appointment Confirmation"),
                "Appointment confirmation was not displayed.");
    }

    @Test
    public void verifyAppointmentHistory() {
        // Book an appointment first
        bookAppointment();

        // Open side menu
        wait.until(ExpectedConditions.elementToBeClickable(
                By.id("menu-toggle"))).click();

        // Click History from the sidebar
        wait.until(ExpectedConditions.elementToBeClickable(
                By.cssSelector("#sidebar-wrapper a[href*='history.php']")))
                .click();

        wait.until(ExpectedConditions.urlContains("history.php"));

        Assert.assertTrue(
                driver.getCurrentUrl().contains("history.php"),
                "Appointment History page did not open.");
    }

    @Test
    public void verifyProfilePage() {
        loginWithValidCredentials();

        wait.until(ExpectedConditions.elementToBeClickable(
                By.id("menu-toggle"))).click();

        wait.until(ExpectedConditions.elementToBeClickable(
                By.cssSelector("#sidebar-wrapper a[href*='profile.php']")))
                .click();

        wait.until(ExpectedConditions.urlContains("profile.php"));

        Assert.assertTrue(
                driver.getCurrentUrl().contains("profile.php"),
                "Profile page did not open.");
    }

    @Test
    public void verifyLogout() {
        loginWithValidCredentials();

        // Open side menu
        wait.until(ExpectedConditions.elementToBeClickable(
                By.id("menu-toggle"))).click();

        // Wait for sidebar Logout link and click
        WebElement logoutLink = wait.until(
                ExpectedConditions.elementToBeClickable(
                        By.cssSelector("#sidebar-wrapper a[href*='logout']")));

        logoutLink.click();

        // Verify return to home page
        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.id("btn-make-appointment")));

        Assert.assertTrue(
                driver.findElement(By.id("btn-make-appointment")).isDisplayed(),
                "Logout did not return to the home page.");
    }

    @Test
    public void verifyAppointmentFormFields() {
        loginWithValidCredentials();

        Assert.assertTrue(
                driver.findElement(By.id("combo_facility")).isDisplayed(),
                "Facility dropdown is not displayed.");

        Assert.assertTrue(
                driver.findElement(By.id("chk_hospotal_readmission")).isDisplayed(),
                "Hospital readmission checkbox is not displayed.");

        Assert.assertTrue(
                driver.findElement(By.id("radio_program_medicaid")).isDisplayed(),
                "Medicaid option is not displayed.");

        Assert.assertTrue(
                driver.findElement(By.id("txt_visit_date")).isDisplayed(),
                "Visit date field is not displayed.");

        Assert.assertTrue(
                driver.findElement(By.id("txt_comment")).isDisplayed(),
                "Comment field is not displayed.");
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
