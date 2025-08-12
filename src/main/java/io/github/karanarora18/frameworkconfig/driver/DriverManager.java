package io.github.karanarora18.frameworkconfig.driver;

import io.github.karanarora18.frameworkconfig.ConfigManager;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class DriverManager {
    private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();
    private static String browser;
    private static String browserVersion;
    private static String os;
    private static String osVersion;
    private static String device;
    private static String realMobile;
    private static String sessionName;

private static ThreadLocal<Map<String, String>> testParams = ThreadLocal.withInitial(HashMap::new);

    public static void setTestParams(String browser, String browserVersion, String os, String osVersion, String device, String realMobile, String sessionName) {
        Map<String, String> params = testParams.get();
        if (browser != null) params.put("browser", browser);
        if (browserVersion != null) params.put("browser_version", browserVersion);
        if (os != null) params.put("os", os);
        if (osVersion != null) params.put("os_version", osVersion);
        if (device != null) params.put("device", device);
        if (realMobile != null) params.put("realMobile", realMobile);
        if (sessionName != null) params.put("sessionName", sessionName);
    }

    public static WebDriver getDriver() {
        if (driver.get() == null) {
            createDriver();
        }
        return driver.get();
    }

    private static void createDriver() {
        String runEnv = System.getProperty("RUN_ENV", ConfigManager.getConfigValue("RUN_ENV"));
        String browser = System.getProperty("BROWSER", ConfigManager.getConfigValue("BROWSER"));


        try {
            if ("local".equalsIgnoreCase(runEnv)) {
                if ("chrome".equalsIgnoreCase(browser)) {
                    System.setProperty("webdriver.chrome.driver", ConfigManager.getConfigValue("CHROME_DRIVER_PATH"));
                    driver.set(new ChromeDriver());
                } else {
                    throw new RuntimeException("Unsupported browser for local: " + browser);
                }
            }
            else if ("browserstack".equalsIgnoreCase(runEnv)) {
                String username = ConfigManager.getConfigValue("BROWSERSTACK_USERNAME");
                String accessKey = ConfigManager.getConfigValue("BROWSERSTACK_ACCESSKEY");
                String url = "https://" + username + ":" + accessKey + "@hub-cloud.browserstack.com/wd/hub";

                Map<String, String> params = testParams.get();
                MutableCapabilities capabilities = new MutableCapabilities();

                // desktop
                if (params.containsKey("browser")) capabilities.setCapability("browserName", params.get("browser"));
                if (params.containsKey("browser_version")) capabilities.setCapability("browserVersion", params.get("browser_version"));

                // mobile
                if (params.containsKey("browserName")) capabilities.setCapability("browserName", params.get("browserName"));

                Map<String, Object> bstackOptions = new HashMap<>();
                if (params.containsKey("os")) bstackOptions.put("os", params.get("os"));
                if (params.containsKey("os_version")) bstackOptions.put("osVersion", params.get("os_version"));
                if (params.containsKey("device")) bstackOptions.put("deviceName", params.get("device"));
                if (params.containsKey("realMobile")) bstackOptions.put("realMobile", params.get("realMobile"));

                bstackOptions.put("buildName", ConfigManager.getConfigValue("BROWSERSTACK_BUILD_NAME"));
                bstackOptions.put("sessionName", params.getOrDefault("sessionName", Thread.currentThread().getName()));
                bstackOptions.put("local", "false");
                bstackOptions.put("seleniumVersion", "4.21.0");
                capabilities.setCapability("bstack:options", bstackOptions);

                driver.set(new RemoteWebDriver(new URL(url), capabilities));
            }
            else {
                throw new RuntimeException("Invalid RUN_ENV: " + runEnv);
            }
        }
        catch (MalformedURLException e) {
            throw new RuntimeException("BrowserStack URL is invalid", e);
        }
        catch (Exception e){
            System.out.println("ERROR in DriverManager: "+e);
        }
    }
}
