package io.github.karanarora18.pages;

import io.github.karanarora18.data.DataManager;
import io.github.karanarora18.frameworkconfig.ConfigManager;
import io.github.karanarora18.frameworkconfig.driver.DriverManager;
import io.github.karanarora18.frameworkconfig.driver.ElementActions;
import io.github.karanarora18.frameworkconfig.util.RapidAPITranslator;
import io.github.karanarora18.frameworkconfig.util.CommonUtilities;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class ElPaisOpinionPage extends ElementActions {
    WebDriver driver  = DriverManager.getDriver();

    // LOCATORS
    By all_articles = By.tagName("article");
    By headline_link = By.xpath(".//h2[contains(@class,'c_t')]/a");
    By article_content = By.xpath(".//p");
    By article_image = By.xpath(".//img");

    public ElPaisOpinionPage(WebDriver driver) {
        super(driver);
    }

    public void fetchArticles(int no_of_articles, DataManager dataManager) {
        for(WebElement elm :  driver.findElements(all_articles)){
            dataManager.articleList.add(elm);
            if(no_of_articles-- <= 1)
                break;
        }
        }
    public String getHeadline(WebElement article) {
        String headline="";
        try {
            WebElement headlineElm = article.findElement(headline_link);
            headline = headlineElm.getText();
        }catch (Exception e){
            System.out.println("headline not found");
        }
        return headline;
    }
        public String getContent(WebElement article) {
        String content="";
        try{
            WebElement contentElm = article.findElement(article_content);
            content=contentElm.getText();
        }catch(Exception e){
            return "Content is not present for this article";
        }
        return content;
    }
    public String getArticleImage(WebElement article){
        try{
            WebElement imageElm = article.findElement(article_image);
            String imgUrl= imageElm.getAttribute("src");
            if (imgUrl != null && !imgUrl.isEmpty()) {
               return imgUrl;
            }
        }catch(Exception e){
        }
        return null;
    }

    public void getRepeatedWords(List<String> translatedEnglishArticles, int repeated) {
        String combinedArticles = "";
        for(String s : translatedEnglishArticles)
            combinedArticles+=s+" ";
        System.out.println("Counting repeated words for all the headlines: ");
        System.out.println(combinedArticles);
        System.out.println("Repeated words:");
        CommonUtilities.countRepeatedWords(combinedArticles.replaceAll("[^a-zA-Z0-9\\s]", "").toLowerCase(),repeated);
    }

    public void printTitleContent(DataManager dataManager) {
        int i=1;
        System.out.println();
        for(WebElement article : dataManager.articleList) {
            String header_title = getHeadline(article);
            System.out.println("Headline "+(i++)+": " + header_title);
            dataManager.articleHeadersInSpanish.add(header_title);
            System.out.println("Content: "+getContent(article));
                String img_url = getArticleImage(article);
                if (img_url != null) {
                    if(ConfigManager.getConfigValue("RUN_ENV").equalsIgnoreCase("local")) {
                        System.out.println("Image present. Downloading Image...");
                        String filename = "article_" + i + ".jpg";
                        CommonUtilities.downloadImage(img_url, filename);
                    }else {
                        System.out.println("Image present. Not downloading image as RUN_ENV is not local");
                    }
                } else {
                    System.out.println("Image is not present for this article");
            }
            System.out.println("\n");
        }
    }

    public void printHeaders(String lang, DataManager dataManager) {
        switch(lang.toLowerCase()){
            case "english":
            case "en":
                System.out.println("Translation in English:");
                int i=1;
                for(String header_title : dataManager.articleHeadersInSpanish) {
                    System.out.println("Headline "+i++);
                    System.out.println("Original: " + header_title);
                    String english_translated_title = RapidAPITranslator.translate("es", "en", header_title);
                    System.out.println("English: " + english_translated_title );
                    dataManager.translatedEnglishArticles.add(english_translated_title);
                }
                break;
            default:
                System.out.println("Invalid language: "+lang);
        }
    }
}
