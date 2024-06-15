package com.softserve.edu.manager;

import com.softserve.edu.driver.AbstractDriverFactory;
import com.softserve.edu.driver.ChromeDriverFactory;
import com.softserve.edu.driver.DriverFactory;
import com.softserve.edu.driver.EdgeDriverFactory;
import com.softserve.edu.driver.FirefoxDriverFactory;
import com.softserve.edu.reporter.LoggerUtils;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;

public enum BrowserFactory {

    CHROME {
        @Override
        public DriverFactory getDriverFactory() { return new ChromeDriverFactory();}
    },
    FIREFOX {
        @Override
        public DriverFactory getDriverFactory() {
            return new FirefoxDriverFactory();
        }
    },
    EDGE {
        @Override
        public DriverFactory getDriverFactory() {
            return new EdgeDriverFactory();
        }
    };

    public static AbstractDriverFactory getFactory(FirefoxOptions options) {
        return new FirefoxDriverFactory(options);
    }

    public static AbstractDriverFactory getFactory(ChromeOptions options) {
        return new ChromeDriverFactory(options);
    }

    public static DriverFactory getFactory(EdgeOptions options) {
        return new EdgeDriverFactory(options);
    }

    public abstract DriverFactory getDriverFactory();

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
