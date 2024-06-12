package com.softserve.edu.manger;

import com.softserve.edu.driver.ChromeDriverFactory;
import com.softserve.edu.driver.DriverFactory;
import com.softserve.edu.driver.EdgeDriverFactory;
import com.softserve.edu.driver.FirefoxDriverFactory;

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

    public static BrowserFactory getDefault() {
        return CHROME;
    }

    public static BrowserFactory fromString(String browser) {
        if (browser == null || browser.isBlank()) {
            return getDefault();
        }
        try {
            return BrowserFactory.valueOf(browser.toUpperCase());
        } catch (IllegalArgumentException e) {
            return getDefault();
        }
    }
}
