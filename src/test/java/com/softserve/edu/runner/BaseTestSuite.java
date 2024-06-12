package com.softserve.edu.runner;

import com.softserve.edu.manger.Configuration;
import com.softserve.edu.driver.DriverFactory;
import com.softserve.edu.manger.DriverFactoryBuilder;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;

public abstract class BaseTestSuite {

    protected WebDriver driver;

    @BeforeEach
    public void setUp() {
        var gridUrl = Configuration.getInstance().getGridURL();
        var browser = Configuration.getInstance().getBrowser();
        DriverFactory driverManager = (gridUrl != null && !gridUrl.isBlank())
                ? DriverFactoryBuilder.getFactory(browser, gridUrl)
                : DriverFactoryBuilder.getFactory(browser);
        driver = driverManager.getDriver();
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        } else {
            System.err.println("WebDriver was not initialized properly.");
        }
    }
}
