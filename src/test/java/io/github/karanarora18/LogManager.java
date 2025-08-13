package io.github.karanarora18;

import org.apache.logging.log4j.Logger;
import org.testng.ITestContext;

public class LogManager {
    private static final ThreadLocal<String> contextInfo = new ThreadLocal<>();
    private static final Logger LOGGER = org.apache.logging.log4j.LogManager.getLogger("BrowserStack");
    public static void info(String msg) {
        LOGGER.info(msg);
    }
    public static void error(String msg, Throwable t){
        LOGGER.error(msg, t);
    }
    public static void setContextInfo(ITestContext context) {
        String browser = (String) context.getCurrentXmlTest().getParameter("browser");
        String device = (String) context.getCurrentXmlTest().getParameter("device");
        String os = (String) context.getCurrentXmlTest().getParameter("os");

        String info = String.format("[Thread-%d] [Browser: %s | Device: %s | OS: %s] ",
                Thread.currentThread().getId(),
                browser != null ? browser : "N/A",
                device != null ? device : "N/A",
                os != null ? os : "N/A");
        contextInfo.set(info);
    }

    public static void log(String message) {
        System.out.println(contextInfo.get() + message);
    }
}
