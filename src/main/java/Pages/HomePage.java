package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class HomePage {

	WebDriver driver;
    WebDriverWait wait;

    By makeAppointmentButton = By.id("btn-make-appointment");

    public HomePage(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
    }

    public void openWebsite() {
        driver.get("https://katalon-demo-cura.herokuapp.com/");
    }

    public void clickMakeAppointment() {
        wait.until(ExpectedConditions.elementToBeClickable(
                makeAppointmentButton)).click();
    }
}

