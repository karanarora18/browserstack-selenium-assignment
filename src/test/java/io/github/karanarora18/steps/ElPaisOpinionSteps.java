package io.github.karanarora18.steps;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.github.karanarora18.data.DataManager;
import io.github.karanarora18.frameworkconfig.driver.DriverManager;
import io.github.karanarora18.pages.ElPaisOpinionPage;
import org.openqa.selenium.WebDriver;

public class ElPaisOpinionSteps {

    WebDriver driver= DriverManager.getDriver();
    ElPaisOpinionPage elPaisOpinionPage;
    private DataManager dataManager;

    public ElPaisOpinionSteps(){
        elPaisOpinionPage = new ElPaisOpinionPage(driver);
        this.dataManager = new DataManager();

    }
    @Then("user fetches {int} article links")
    public void userFetchesArticleLinks(int no) {
        elPaisOpinionPage.fetchArticles(no, dataManager);
    }

    @And("user prints title content in Spanish and downloads article image if available")
    public void userPrintsTitleContentInSpanishAndDownloadsArticleImageIfAvailable() {
        elPaisOpinionPage.printTitleContent(dataManager);

    }

    @And("user translates and prints each headers in {string} language")
    public void userTranslatesAndPrintsEachHeadersInLanguage(String lang) {
            elPaisOpinionPage.printHeaders(lang, dataManager);
    }

    @And("user prints repeated words \\(>{int} occurrences) in all headers combined")
    public void userPrintsRepeatedWordsOccurrencesInAllHeadersCombined(int repeated) {
        elPaisOpinionPage.getRepeatedWords(dataManager.translatedEnglishArticles,repeated);
    }
}
