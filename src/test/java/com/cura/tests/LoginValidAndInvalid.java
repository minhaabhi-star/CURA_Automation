package com.cura.tests;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class LoginValidAndInvalid {
	
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

	    // Reusable login method
	    public void loginWithValidCredentials() {
	        wait.until(ExpectedConditions.elementToBeClickable(
	                By.id("btn-make-appointment"))).click();

	        wait.until(ExpectedConditions.visibilityOfElementLocated(
	                By.id("txt-username"))).sendKeys(USERNAME);

	        driver.findElement(By.id("txt-password")).sendKeys(PASSWORD);

	        driver.findElement(By.id("btn-login")).click();

	        wait.until(ExpectedConditions.visibilityOfElementLocated(
	                By.id("combo_facility")));
	    }

	    
	    // TC02: Verify valid login
	    @Test
	    public void verifyValidLogin() {
	        loginWithValidCredentials();

	        Assert.assertTrue(
	                driver.findElement(By.id("combo_facility")).isDisplayed(),
	                "Appointment form was not displayed.");

	        System.out.println("PASS: Valid login successful.");
	    }

	    // TC03: Verify invalid login
	    @Test
	    public void verifyInvalidLogin() {
	        driver.findElement(By.id("btn-make-appointment")).click();

	        wait.until(ExpectedConditions.visibilityOfElementLocated(
	                By.id("txt-username"))).sendKeys("WrongUser");

	        driver.findElement(By.id("txt-password"))
	                .sendKeys("WrongPassword");

	        driver.findElement(By.id("btn-login")).click();

	        WebElement error = wait.until(
	                ExpectedConditions.visibilityOfElementLocated(
	                        By.cssSelector(".text-danger")));

	        Assert.assertTrue(error.getText().contains("Login failed"),
	                "Expected login failure message was not displayed.");

	        System.out.println("PASS: Invalid login rejected.");
	    }

	
	

}

	
	


