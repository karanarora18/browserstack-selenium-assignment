package io.github.karanarora18.pages;

import io.github.karanarora18.frameworkconfig.driver.ElementActions;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ElPaisHomePage extends ElementActions {


    // LOCATORS
    By menu_opinion = By.xpath("//div[@id='hamburger_container']//a[text()='Opinión']");
    By btn_hamburger = By.id("btn_open_hamburger");
    By accept_cookie = By.id("didomi-notice-agree-button");

    public ElPaisHomePage(WebDriver driver){
        super(driver);
    }


    public void selectFromMenuOptions(String option) {
        switch(option.toLowerCase()){
            case "opinion":
                click(btn_hamburger);
                click(menu_opinion);
                break;
            default:
                System.out.println("Invalid option: "+option);
        }
    }

    public void verifyLandingHeaderAndHeader(String pageName) {

        switch(pageName.toLowerCase()){
            case "opinion":
                Assert.assertEquals("Page title is not matching",getPageTitle(),"Opinión en EL PAÍS");
                break;
            default:
                System.out.println("Invalid option: "+pageName);
        }
    }

    public void handleAlerts() {
        handleCookieBanner(accept_cookie);
    }
}
