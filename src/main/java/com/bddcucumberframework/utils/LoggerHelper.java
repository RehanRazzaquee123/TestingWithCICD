package com.bddcucumberframework.utils;

import org.apache.logging.log4j.Logger;

public class LoggerHelper {

    public static Logger getLogger() {
        return org.apache.logging.log4j.LogManager.getLogger();
    }
}
