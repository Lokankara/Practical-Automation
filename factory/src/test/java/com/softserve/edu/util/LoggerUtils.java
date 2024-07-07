package com.softserve.edu.util;

import com.softserve.edu.teachua.tests.TestRunner;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class LoggerUtils {
    private static final Logger logger = LogManager.getLogger(TestRunner.class.getSimpleName());

    public static void logInfo(String... messages) {
        logger.info(String.join(" ", messages));
    }

    public static void logError(String... messages) {
        logger.error(String.join(" ", messages));
    }

    public static void logWarn(String... messages) {
        logger.warn(String.join(" ", messages));
    }

    public static void logFatal(String... messages) {
        logger.fatal(String.join(" ", messages));
    }
}
