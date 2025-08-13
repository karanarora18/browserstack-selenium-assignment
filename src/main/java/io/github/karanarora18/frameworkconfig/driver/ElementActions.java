package io.github.karanarora18.frameworkconfig.driver;

import io.github.karanarora18.frameworkconfig.ConfigManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.*;
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
        if(isElementPresent(locator)){
           jsClick(driver.findElement(locator));
            driver.findElement(locator);
            logger.info("Clicked Element: " + locator.toString());
        }else{
            logger.info("Unable to Click: " + locator.toString());
        }
    }
    public boolean isElementPresent(By locator) {
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

    public void handleCookieBanner(By locator) {
        try {
            WebDriverWait shortWait = new WebDriverWait(driver, Duration.ofSeconds(5));
            WebElement cookieBtn = shortWait.until(ExpectedConditions.presenceOfElementLocated(locator));
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", cookieBtn);
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", cookieBtn);
            System.out.println("Cookie banner accepted.");
        } catch (TimeoutException e) {
            System.out.println("Cookie banner not found, skipping...");
        }
    }
    public void jsClick(WebElement element) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
    }
}
