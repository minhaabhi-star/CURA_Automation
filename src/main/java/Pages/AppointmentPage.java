package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class AppointmentPage {
	
	 WebDriver driver;
	    WebDriverWait wait;

	    By facility = By.id("combo_facility");
	    By readmission = By.id("chk_hospotal_readmission");
	    By medicaid = By.id("radio_program_medicaid");
	    By visitDate = By.id("txt_visit_date");
	    By comment = By.id("txt_comment");
	    By bookButton = By.id("btn-book-appointment");

	    public AppointmentPage(WebDriver driver, WebDriverWait wait) {
	        this.driver = driver;
	        this.wait = wait;
	    }

	    public void selectFacility(String facilityName) {
	        WebElement dropdown = wait.until(
	                ExpectedConditions.visibilityOfElementLocated(facility));

	        new Select(dropdown).selectByVisibleText(facilityName);
	    }

	    public void selectReadmission() {
	        WebElement checkbox = driver.findElement(readmission);

	        if (!checkbox.isSelected()) {
	            checkbox.click();
	        }
	    }

	    public void selectMedicaid() {
	        driver.findElement(medicaid).click();
	    }

	    public void enterVisitDate(String date) {
	        WebElement dateField = wait.until(
	                ExpectedConditions.visibilityOfElementLocated(visitDate));

	        dateField.clear();
	        dateField.sendKeys(date);
	    }

	    public void enterComments(String comments) {
	        driver.findElement(comment).clear();
	        driver.findElement(comment).sendKeys(comments);
	    }

	    public void clickBookAppointment() {
	        wait.until(ExpectedConditions.elementToBeClickable(
	                bookButton)).click();
	    }

	    public void completeAppointment(String facilityName,
	                                    String date,
	                                    String comments) {
	        selectFacility(facilityName);
	        selectReadmission();
	        selectMedicaid();
	        enterVisitDate(date);
	        enterComments(comments);
	        clickBookAppointment();
	    }
	}


