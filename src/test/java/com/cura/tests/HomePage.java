package com.cura.tests;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class HomePage {
	
	WebDriver driver;
    WebDriverWait wait;

    final String BASE_URL =
            "https://katalon-demo-cura.herokuapp.com/";

    final String USERNAME = "John Doe";
    final String PASSWORD = "ThisIsNotAPassword";

    @BeforeMethod
    public void setUp() {
        driver = new ChromeDriver();

        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(20));

        driver.get(BASE_URL);
    }

 // TC01: Verify home page
@Test
public void verifyHomePage() {
    Assert.assertTrue(
            wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.id("btn-make-appointment"))).isDisplayed(),
            "Make Appointment button is not displayed.");

    System.out.println("PASS: Home page is displayed.");

}}



