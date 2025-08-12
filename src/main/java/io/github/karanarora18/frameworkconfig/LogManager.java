package io.github.karanarora18.frameworkconfig;

import org.apache.logging.log4j.Logger;

public class LogManager {

    private static final Logger LOGGER = org.apache.logging.log4j.LogManager.getLogger("BrowserStack");
    public static void info(String msg) {
        LOGGER.info(msg);
    }
    public static void error(String msg, Throwable t){
        LOGGER.error(msg, t);
    }

}
