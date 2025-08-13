package io.github.karanarora18.steps;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.github.karanarora18.LogManager;
import io.github.karanarora18.frameworkconfig.ConfigManager;
import io.github.karanarora18.frameworkconfig.TestConfig;
import io.github.karanarora18.frameworkconfig.driver.DriverManager;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.time.Duration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
public class Hooks {
    private static final Logger logger = LoggerFactory.getLogger(Hooks.class);

    @Before
    public void setUp(Scenario scenario) {
        String threadName = Thread.currentThread().getName();
        logger.info("Starting scenario: {}", scenario.getName());
        String browser       = TestConfig.get("browser", "");
        String browserVer    = TestConfig.get("browser_version", "");
        String os            = TestConfig.get("os", "");
        String osVersion     = TestConfig.get("os_version", "");
        String device        = TestConfig.get("device", "");
        String realMobile    = TestConfig.get("realMobile", "false");
        String sessionName   = scenario.getName();

        DriverManager.setTestParams(browser, browserVer, os, osVersion, device, realMobile, sessionName);
        LogManager.log("[Thread: " + threadName + "] Scenario Started - " + sessionName);

        if (ConfigManager.getConfigValue("RUN_ENV").equalsIgnoreCase("browserstack")) {
            logCapabilities(browser, browserVer, os, osVersion, device, realMobile);
        }

        try {
            WebDriver driver = DriverManager.getDriver();
            driver.manage().timeouts().implicitlyWait(
                    Duration.ofSeconds(Long.parseLong(ConfigManager.getConfigValue("IMPLICIT_WAIT_TIME")))
            );
            if(realMobile.equalsIgnoreCase("false"))
                driver.manage().window().maximize();
        } catch (Exception e) {
            System.out.println("[Thread: " + threadName + "] Error in initialization: " + e.getMessage());
        }
    }

    @After
    public void tearDown(Scenario scenario) {
        String threadName = Thread.currentThread().getName();
        if (scenario.isFailed()) {
            try {
                byte[] screenshot = ((TakesScreenshot) DriverManager.getDriver()).getScreenshotAs(OutputType.BYTES);
                scenario.attach(screenshot, "image/png", "Failed Step Screenshot");
            } catch (Exception e) {
                System.out.println("[Thread: " + threadName + "] Screenshot capture failed: " + e.getMessage());
            }
        }

        System.out.println("[Thread: " + threadName + "] Scenario status: " + scenario.getStatus());

        if ("chrome".equalsIgnoreCase(ConfigManager.getConfigValue("BROWSER"))) {
            if (DriverManager.getDriver() != null) {
                DriverManager.getDriver().quit();
            }
        }
        TestConfig.clear();
    }

    private void logCapabilities(String browser, String browserVer, String os, String osVer, String device, String realMobile) {
        String name = Thread.currentThread().getName();
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("\n=== [Thread: %s] Test Capabilities ===%n", name));
        sb.append(String.format("%-15s: %s%n", "Browser", browser));
        sb.append(String.format("%-15s: %s%n", "Browser Ver.", browserVer));
        sb.append(String.format("%-15s: %s%n", "OS", os + " " + osVer));
        if (device != null && !device.isEmpty()) {
            sb.append(String.format("%-15s: %s%n", "Device", device));
        }
        sb.append(String.format("%-15s: %s%n", "Real Mobile", realMobile));
        sb.append("====================================\n");

        System.out.print(sb.toString());

    }

}
