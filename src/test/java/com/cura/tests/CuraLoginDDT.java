package com.cura.tests;

import java.io.IOException;
import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import Utlities.ExcelReader;

public class CuraLoginDDT {
	 WebDriver driver;
	    WebDriverWait wait;

	    String baseUrl =
	        "https://katalon-demo-cura.herokuapp.com/";

	    @BeforeMethod
	    public void setUp() {

	        driver = new ChromeDriver();
	        driver.manage().window().maximize();

	        wait = new WebDriverWait(
	            driver, Duration.ofSeconds(10));

	        driver.get(baseUrl);
	    }

	    @DataProvider(name = "loginData")
	    public Object[][] getLoginData() throws IOException {

	        return ExcelReader.readExcel(
	            "TestData.xlsx", "LoginData");
	    }

	    @Test(dataProvider = "loginData")
	    public void verifyLogin(String username,
	                            String password,
	                            String expected) {

	        // Open login page
	        driver.findElement(
	            By.id("btn-make-appointment")).click();

	        // Enter username and password
	        wait.until(ExpectedConditions.visibilityOfElementLocated(
	            By.id("txt-username"))).sendKeys(username);

	        driver.findElement(
	            By.id("txt-password")).sendKeys(password);

	        // Click Login
	        driver.findElement(By.id("btn-login")).click();

	        if (expected.equalsIgnoreCase("SUCCESS")) {

	            wait.until(ExpectedConditions.visibilityOfElementLocated(
	                By.id("combo_facility")));

	            Assert.assertTrue(
	                driver.findElement(
	                    By.id("combo_facility")).isDisplayed(),
	                "Valid login should open the appointment form");

	        } else if (expected.equalsIgnoreCase("FAILURE")) {

	            wait.until(ExpectedConditions.visibilityOfElementLocated(
	                By.className("text-danger")));

	            String errorMessage = driver.findElement(
	                By.className("text-danger")).getText();

	            Assert.assertTrue(
	                errorMessage.contains("Login failed"),
	                "Invalid login should display an error message");

	        } else {
	            Assert.fail("Unknown Expected value in Excel: " + expected);
	        }
	    }

	    @AfterMethod
	    public void tearDown() {

	        if (driver != null) {
	            driver.quit();
	        }
	    }
	}


