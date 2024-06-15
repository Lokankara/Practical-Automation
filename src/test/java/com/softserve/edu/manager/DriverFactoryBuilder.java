package com.softserve.edu.manager;

import com.softserve.edu.driver.AbstractDriverFactory;
import com.softserve.edu.driver.DriverFactory;
import com.softserve.edu.driver.RemoteDriverFactory;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;

public class DriverFactoryBuilder {

    public static DriverFactory getFactory(String browser, String gridUrl) {
        return gridUrl != null && !gridUrl.isBlank()
                ? new RemoteDriverFactory(browser, gridUrl)
                : getFactory(browser);
    }

    public static DriverFactory getFactory(String browser) {
        return BrowserFactory.fromString(browser).getDriverFactory();
    }

    public static AbstractDriverFactory getFactory(FirefoxOptions options) {
        return BrowserFactory.getFactory(options);
    }

    public static AbstractDriverFactory getFactory(ChromeOptions options) {
        return BrowserFactory.getFactory(options);
    }

    public static DriverFactory getFactory(EdgeOptions options) {
        return BrowserFactory.getFactory(options);
    }
}
