package io.github.karanarora18.steps;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.github.karanarora18.frameworkconfig.ConfigManager;
import io.github.karanarora18.frameworkconfig.driver.DriverManager;
import io.github.karanarora18.pages.ElPaisHomePage;
import org.openqa.selenium.WebDriver;

public class ElPaisHomeSteps {
    WebDriver driver = DriverManager.getDriver();
    ElPaisHomePage elPaisPage;

    public ElPaisHomeSteps() {
        elPaisPage= new ElPaisHomePage(driver);
    }

    @Given("user opens ELPais website")
    public void userOpensELPaisWebsite() {
        driver.get(ConfigManager.getConfigValue("TARGET_URL"));
        elPaisPage.handleAlerts();
    }

    @When("user selects {string} option menu from homepage")
    public void userSelectsOptionMenuFromMenuOption(String option) {
        elPaisPage.selectFromMenuOptions(option);
    }


    @Then("user verifies title for {string} page")
    public void userVerifiesPageTitle(String pageName) {
        elPaisPage.verifyLandingHeaderAndHeader(pageName);
    }
}
