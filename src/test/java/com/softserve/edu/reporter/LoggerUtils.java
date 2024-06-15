package com.softserve.edu.reporter;

import com.softserve.edu.runner.BaseTestSuite;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LoggerUtils {
    private static final String INFO = "💡";
    private static final String PASS = "✅";
    private static final String WARN = "⚠️";
    private static final String ERROR = "❌";
    private static final String FATAL = "❗";
    private static final Logger logger = LogManager.getLogger(BaseTestSuite.class.getSimpleName());

    public static void logPass(String... messages) {
        logger.info("{} {}", PASS, messages);
    }

    public static void logInfo(String... messages) {
        logger.info("{} {}", INFO, messages);
    }

    public static void logError(String... message) {logger.error("{} {}", ERROR, message);}

    public static void logWarning(String... message) {
        logger.warn("{} {}", WARN, message);
    }

    public static void logFatal(String... message) {
        logger.fatal("{} {}", FATAL, message);
    }
}
