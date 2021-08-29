package stepDefinitions;

import java.util.Properties;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;

import pageObjects.weatherInfoPage;

public class baseClass {
    public WebDriver driver;
    public weatherInfoPage weatherInfoPage;
    public static Logger logger;
    public Properties confProp;
}