package pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;

import utilities.waitHelper;
import utilities.InvalidTemperatureDiffException;

public class weatherInfoPage {
  public WebDriver driver;
  public waitHelper waitHelper;

  public weatherInfoPage(WebDriver driver) {
    this.driver = driver;
    waitHelper = new waitHelper(driver);
  }

  By searchBox = By.xpath("//input[@class='search-input']");
  By temperature = By.xpath("(//div[@class='temp-container'][1]/div)[1]");

  public void searchTemperature(String city) throws InterruptedException {
    waitHelper.waitForElement(driver.findElement(searchBox), 50);
    driver.findElement(searchBox).clear();
    driver.findElement(searchBox).sendKeys(city);
    Thread.sleep(2000);

    driver.findElement(searchBox).sendKeys(Keys.ARROW_DOWN);
    driver.findElement(searchBox).sendKeys(Keys.ENTER);

  }

  public String getTemperature() throws InterruptedException {
    Thread.sleep(2000);
    return driver.findElement(temperature).getText();

  }

  public boolean compareTemperature(double temperatureFromAccuWeatherApp, double TemperatureFromApi) throws InvalidTemperatureDiffException{

    boolean diffvalue = false;

    if (temperatureFromAccuWeatherApp != TemperatureFromApi) {

          double tempDiff = Math.abs(temperatureFromAccuWeatherApp - TemperatureFromApi);

          if (tempDiff <= 1) {
            diffvalue = true;
          }
        else{
          throw new InvalidTemperatureDiffException("Differnce between two temperature sources are mor than expected"+tempDiff);
          }

    }

    return diffvalue;
  }
}

