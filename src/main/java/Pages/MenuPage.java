package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class MenuPage {
	
	 WebDriver driver;
	    WebDriverWait wait;

	    By menuToggle = By.id("menu-toggle");
	    By historyLink = By.cssSelector("a[href*='history.php']");
	    By profileLink = By.cssSelector("a[href*='profile.php']");
	    By logoutLink = By.cssSelector("a[href*='logout']");

	    public MenuPage(WebDriver driver, WebDriverWait wait) {
	        this.driver = driver;
	        this.wait = wait;
	    }

	    public void openMenu() {
	        wait.until(ExpectedConditions.elementToBeClickable(
	                menuToggle)).click();
	    }

	    public void clickHistory() {
	        wait.until(ExpectedConditions.elementToBeClickable(
	                historyLink)).click();
	    }

	    public void clickProfile() {
	        wait.until(ExpectedConditions.elementToBeClickable(
	                profileLink)).click();
	    }

	    public void clickLogout() {
	        wait.until(ExpectedConditions.elementToBeClickable(
	                logoutLink)).click();
	    }
	}


