package io.github.karanarora18.frameworkconfig;

import java.io.FileInputStream;
import java.util.Properties;

public class ConfigManager {

    private static final Properties prop = new Properties();

    static {
        try{
            FileInputStream fin = new FileInputStream("src/main/resources/config.properties");
            prop.load(fin);
        }
        catch(Exception e){
            throw new RuntimeException("Failed to load config.properties.\n"+e);
        }
    }
    public static String getConfigValue(String key) {
        String value = System.getProperty(key);
        if (value != null && !value.isEmpty()) {
            return value;
        }
        return prop.getProperty(key);
    }


}
