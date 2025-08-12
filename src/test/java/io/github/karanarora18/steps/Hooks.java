package io.github.karanarora18.steps;

import io.cucumber.java.After;

import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.github.karanarora18.frameworkconfig.ConfigManager;
import io.github.karanarora18.frameworkconfig.driver.DriverManager;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import java.time.Duration;


public class Hooks {

    @Before
    public void setUp(Scenario scenario) {
        System.out.println("Starting scenario: " + scenario.getName());

        // read from system properties (passed via Maven command line)
        String browser = System.getProperty("browser");
        String browserVersion = System.getProperty("browserVersion");
        String os = System.getProperty("os");
        String osVersion = System.getProperty("osVersion");
        String device = System.getProperty("device");
        String realMobile = System.getProperty("realMobile");
        String sessionName = System.getProperty("sessionName");

        DriverManager.setTestParams(browser, browserVersion, os, osVersion, device, realMobile, sessionName);

        try{
            WebDriver driver = DriverManager.getDriver();
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(Long.parseLong(ConfigManager.getConfigValue("IMPLICIT_WAIT_TIME"))));
            driver.manage().window().maximize();
        }catch (Exception e){
            System.out.println("Error in initialization: "+e);
        }



    }

    @After
    public void tearDown(Scenario scenario) {
        if (scenario.isFailed()) {
            byte[] screenshot = ((TakesScreenshot) DriverManager.getDriver()).getScreenshotAs(OutputType.BYTES);
            scenario.attach(screenshot, "image/png", "Failed Step Screenshot");
        }
        System.out.println("Scenario status: " + scenario.getStatus());
        if(ConfigManager.getConfigValue("BROWSER").equalsIgnoreCase("chrome")) {
            if (DriverManager.getDriver() != null) {
                DriverManager.getDriver().quit();
            }
        }
    }

}
