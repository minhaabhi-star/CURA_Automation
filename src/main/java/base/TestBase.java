package base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

public class TestBase {
	
	 protected WebDriver driver;

	    @BeforeMethod
	    public void setUp() {
	        driver = new ChromeDriver();
	        driver.manage().window().maximize();
	        
	        // Navigates directly to your target login page
	        driver.get("https://katalon-demo-cura.herokuapp.com/");
	    }

	    @AfterMethod
	    public void tearDown() {
	        if (driver != null) {
	            driver.quit();
	        }
	    }
	}


