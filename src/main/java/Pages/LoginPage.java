package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginPage {
	
	WebDriver driver;
    WebDriverWait wait;

    By usernameField = By.id("txt-username");
    By passwordField = By.id("txt-password");
    By loginButton = By.id("btn-login");
    By errorMessage = By.cssSelector(".text-danger");
    By facilityDropdown = By.id("combo_facility");

    public LoginPage(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
    }

    public void enterUsername(String username) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(
                usernameField)).sendKeys(username);
    }

    public void enterPassword(String password) {
        driver.findElement(passwordField).sendKeys(password);
    }

    public void clickLogin() {
        driver.findElement(loginButton).click();
    }

    public void login(String username, String password) {
        enterUsername(username);
        enterPassword(password);
        clickLogin();
    }

    public boolean isAppointmentFormDisplayed() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(
                facilityDropdown)).isDisplayed();
    }

    public String getErrorMessage() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(
                errorMessage)).getText();
    }
}


