package com.softserve.edu.manager;

import com.softserve.edu.driver.DriverFactory;
import com.softserve.edu.driver.RemoteDriverFactory;

public class DriverFactoryBuilder {

    public static DriverFactory getFactory(String browser, String gridUrl) {
        return gridUrl != null && !gridUrl.isBlank()
                ? new RemoteDriverFactory(browser, gridUrl)
                : getFactory(browser);
    }

    public static DriverFactory getFactory(String browser) {
        return BrowserFactory.fromString(browser).getDriverManager();
    }
}
