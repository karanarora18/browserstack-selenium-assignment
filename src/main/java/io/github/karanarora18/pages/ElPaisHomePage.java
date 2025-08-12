package io.github.karanarora18.pages;

import io.github.karanarora18.frameworkconfig.driver.ElementActions;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ElPaisHomePage extends ElementActions {


    // LOCATORS
    By menu_opinion = By.xpath("//div[@class='sm _df']/a[text()='Opinión']");
    By accept_cookie = By.id("didomi-notice-agree-button");

    public ElPaisHomePage(WebDriver driver){
        super(driver);
    }


    public void selectFromMenuOptions(String option) {
        switch(option.toLowerCase()){
            case "opinion":
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
        try {
            if (isELementPresent(accept_cookie)) {
                click(accept_cookie);
            }
        }catch(Exception ignored){
        }
    }
}
