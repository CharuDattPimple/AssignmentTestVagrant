package utilities;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class waitHelper {
	public WebDriver driver;
	
	public waitHelper(WebDriver driver) {
		this.driver=driver;
	}
	
	public void waitForElement(WebElement ele,long timeOutInSeconds) {
		WebDriverWait wait= new WebDriverWait(driver, timeOutInSeconds);
		wait.until(ExpectedConditions.visibilityOf(ele));
	}
	

}
