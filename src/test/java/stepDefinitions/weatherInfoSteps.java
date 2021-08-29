package stepDefinitions;

import io.restassured.RestAssured;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;

import io.cucumber.java.Before;
import io.cucumber.java.en.*;
import io.github.bonigarcia.wdm.WebDriverManager;
import pageObjects.weatherInfoPage;
import static io.restassured.RestAssured.given;
import io.restassured.path.json.JsonPath;



import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.junit.Assert;

public class weatherInfoSteps extends baseClass {

  @Before
  public void setUp() throws IOException {
    //Set up Logger added
    logger = Logger.getLogger("weatherInfo");
    PropertyConfigurator.configure("log4j.properties");

    // Read Properties
    confProp = new Properties();
    FileInputStream connfProfile = new FileInputStream("config.properties");
    confProp.load(connfProfile);

    String browserName = confProp.getProperty("browser");

    if (browserName.equals("chrome")) {
      WebDriverManager.chromedriver().setup();

      ChromeOptions chrOps = new ChromeOptions();
      chrOps.addArguments("--disable-notifications");
//      chrOps.addArguments("--headless","--window-size=1920,1200");

      driver = new ChromeDriver(chrOps);

    } else if (browserName.equals("firefox")) {
      WebDriverManager.firefoxdriver().setup();
      driver = new FirefoxDriver();
    }
    driver.manage().window().maximize();
  }

  double temperatureFromApi;
  double temperatureFromAccuWeatherApp;

  @Given("User is on AccuWeather home page")
  public void user_is_on_accuweather_home_page() {
    weatherInfoPage = new weatherInfoPage(driver);
    logger.info("********** Opening URL *************");
    driver.get(confProp.getProperty("url"));
  }


  @When("User search weather information for {string} city")
  public void user_search_weather_information_for_city(String cityName) throws InterruptedException {
    weatherInfoPage.searchTemperature(cityName);
    String temperature = weatherInfoPage.getTemperature();
    String s = temperature.substring(0, 2);
    temperatureFromAccuWeatherApp = Double.parseDouble(s);
    logger.info("********** User got temperature from Accu Weather App *************" + temperatureFromAccuWeatherApp);
  }

  @When("User quits the browser")
  public void quit_browser() {
    driver.quit();
    logger.info("******** User Quits the browser ***********");
  }

  @When("User fetches information from api")
  public void User_fetches_information_from_api() {

    RestAssured.baseURI = "https://api.openweathermap.org";
    String response = given().log().all().queryParam("q", "Mumbai").queryParam("appid", "7fe67bf08c80ded756e598d6f8fedaea")
            .header("content-Type", "application/json")
            .when().get("/data/2.5/weather")
            .then()
            .assertThat()
            .statusCode(200)
            .extract().response().asString();

    JsonPath js = new JsonPath(response);
    String temp = js.getString("main.temp");
    float tempratureInKelvin = Float.parseFloat(temp);
    float convertToCelcius = (float) (tempratureInKelvin - 273.15);
    temperatureFromApi = (double) convertToCelcius;
    logger.info("*********** temperature from openweather map api *************"+temperatureFromApi);

  }

  @Then("Temperature from both sources should equal")
  public void temperature_from_both_sources_should_equal() {
    Assert.assertTrue(weatherInfoPage.compareTemperature(temperatureFromAccuWeatherApp, temperatureFromApi));
    logger.info("********** temperature from both the apps are same *************");
  }

}
