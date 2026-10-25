package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ConfirmationPage {
	
	 WebDriver driver;
	    WebDriverWait wait;

	    By confirmationHeading =
	            By.xpath("//h2[contains(normalize-space(),'Appointment Confirmation')]");

	    public ConfirmationPage(WebDriver driver, WebDriverWait wait) {
	        this.driver = driver;
	        this.wait = wait;
	    }

	    public boolean isConfirmationDisplayed() {
	        return wait.until(ExpectedConditions.visibilityOfElementLocated(
	                confirmationHeading)).isDisplayed();
	    }

	    public String getConfirmationText() {
	        return driver.findElement(By.tagName("body")).getText();
	    }
	}


