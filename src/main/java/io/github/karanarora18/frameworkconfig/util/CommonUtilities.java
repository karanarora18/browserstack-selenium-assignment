package io.github.karanarora18.frameworkconfig.util;

import io.github.karanarora18.frameworkconfig.ConfigManager;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.*;

public class CommonUtilities {

    //method to download image
    public static void downloadImage(String url, String file){
        String imageDir = ConfigManager.getConfigValue("IMAGE_LOCATION");
        File dir = new File(imageDir);
        if (!dir.exists()) {
            dir.mkdirs(); // create the images directory if not exists
        }
        String dateStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());

        file = imageDir+"/"+dateStamp+"_"+file;

        try (BufferedInputStream in = new BufferedInputStream(new URL(url).openStream());
             FileOutputStream fileOutputStream = new FileOutputStream(file)) {
            byte dataBuffer[] = new byte[1024];
            int bytesRead;
            while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
                fileOutputStream.write(dataBuffer, 0, bytesRead);
            }
            System.out.println("Image Downloaded - "+file);
        } catch (IOException e) {
            System.out.println("Failed to download image: " + e.getMessage());
        }

        }

    //method to count repeated words 'n' no of times
    public static void countRepeatedWords(String combinedArticles, int repeated) {
        String words_arr[] = combinedArticles.split("\\s+");
        boolean flag = false;
        Map<String, Integer> counter = new HashMap<>();

        for(String word : words_arr){
            if(counter.containsKey(word)){
                int temp = counter.get(word);
                counter.put(word,temp+1);
            }else{
                counter.put(word,1);
            }
        }
        for (String word : counter.keySet()){
            int temp = counter.get(word);
            if(temp>repeated){
                System.out.println(word + " - " + temp + " times");
                flag = true;
            }
        }
        if(!flag){
            System.out.println("No Repeated Words Found!");
        }
    }
}
