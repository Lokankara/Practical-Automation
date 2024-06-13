package com.softserve.edu.manager;

import com.softserve.edu.driver.ChromeDriverFactory;
import com.softserve.edu.driver.DriverFactory;
import com.softserve.edu.driver.EdgeDriverFactory;
import com.softserve.edu.driver.FirefoxDriverFactory;
import com.softserve.edu.reporter.LoggerUtils;

public enum BrowserFactory {

    CHROME {
        @Override
        public DriverFactory getDriverManager() {
            return new ChromeDriverFactory();
        }
    },
    FIREFOX {
        @Override
        public DriverFactory getDriverManager() {
            return new FirefoxDriverFactory();
        }
    },
    EDGE {
        @Override
        public DriverFactory getDriverManager() {
            return new EdgeDriverFactory();
        }
    };

    public abstract DriverFactory getDriverManager();

    public static BrowserFactory fromString(String browser) {
        if (browser == null || browser.isBlank()) {
            LoggerUtils.logWarning("Browser string is null or blank");
            return getDefault();
        }
        try {
            return BrowserFactory.valueOf(browser.toUpperCase());
        } catch (IllegalArgumentException e) {
            LoggerUtils.logWarning("No matching Browser for: ", browser, e.getMessage());
            return getDefault();
        }
    }

    private static BrowserFactory getDefault() {
        return BrowserFactory.fromString(Configuration.getInstance().getBrowser().toUpperCase());
    }
}
