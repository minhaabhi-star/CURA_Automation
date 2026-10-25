
package com.cura.tests;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class Logout {

    WebDriver driver;
    WebDriverWait wait;

    String baseUrl =
        "https://katalon-demo-cura.herokuapp.com/";

    @BeforeMethod
    public void setUp() {

        driver = new ChromeDriver();

        driver.manage().window().maximize();

        wait = new WebDriverWait(
            driver, Duration.ofSeconds(15));

        driver.get(baseUrl);
    }

    @Test
    public void verifyLogout() {

        // Step 1: Click Make Appointment
        wait.until(ExpectedConditions.elementToBeClickable(
            By.id("btn-make-appointment"))).click();

        // Step 2: Enter valid username
        wait.until(ExpectedConditions.visibilityOfElementLocated(
            By.id("txt-username"))).sendKeys("John Doe");

        // Step 3: Enter valid password
        driver.findElement(By.id("txt-password"))
              .sendKeys("ThisIsNotAPassword");

        // Step 4: Click Login
        driver.findElement(By.id("btn-login")).click();

        // Step 5: Verify successful login
        wait.until(ExpectedConditions.visibilityOfElementLocated(
            By.id("combo_facility")));

        // Step 6: Open the side menu
        WebElement menu = wait.until(
            ExpectedConditions.elementToBeClickable(
                By.id("menu-toggle")));

        menu.click();

        // Step 7: Wait for Logout link to appear
        By logoutLink =
            By.xpath("//a[normalize-space()='Logout']");

        wait.until(
            ExpectedConditions.visibilityOfElementLocated(
                logoutLink));

        // Step 8: Click Logout after the menu animation
        wait.until(
            ExpectedConditions.elementToBeClickable(
                logoutLink)).click();

        // Step 9: Verify logout returns to the home page
        wait.until(
            ExpectedConditions.urlContains("katalon-demo-cura"));

        wait.until(
            ExpectedConditions.elementToBeClickable(
                By.id("btn-make-appointment")));

        Assert.assertTrue(
            driver.findElement(
                By.id("btn-make-appointment")).isDisplayed(),
            "Logout failed: Make Appointment button is not displayed");

        System.out.println("Logout test passed successfully.");
    }

    @AfterMethod
    public void tearDown() {

        if (driver != null) {
            driver.quit();
        }
    }
}