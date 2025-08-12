package io.github.karanarora18.frameworkconfig.driver;

import io.github.karanarora18.frameworkconfig.ConfigManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class ElementActions {
    private WebDriver driver;
    private WebDriverWait wait;
    private static final Logger logger =  LogManager.getLogger(ElementActions.class);

    public ElementActions(WebDriver driver) {
        this.driver=driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(Long.parseLong(ConfigManager.getConfigValue("EXPLICIT_WAIT_TIME"))));

    }
    public void click(By locator) {
        wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
        logger.info("Clicked Element: " + locator.toString());
    }
    public boolean isELementPresent(By locator) {
        logger.info("Is Element Present?: " + locator.toString());
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator)).isDisplayed();
    }
    public String getPageTitle()
    {
        logger.info("Event: Get Page Title");
        return driver.getTitle();
    }
    public String getText(By locator) {
        logger.info("Get Text for Element: " + locator.toString());
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator)).getText();
    }

}
