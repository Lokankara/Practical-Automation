package com.softserve.edu.manager;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class DriverManager {

    private static final ThreadLocal<WebDriver> threadLocalDriver = new ThreadLocal<>();
    private static final ThreadLocal<WebDriverWait> threadLocalWait = new ThreadLocal<>();

    private DriverManager() {
    }

    public static WebDriver getWebDriver(Browsers browser) {
        if (threadLocalDriver.get() == null) {
            putToMap(DriverFactoryBuilder.getFactory(browser.name()).getDriver());
        }
        return threadLocalDriver.get();
    }

    public static WebDriver getWebDriver(ChromeOptions options) {
        if (threadLocalDriver.get() == null) {
            putToMap(DriverFactoryBuilder.getFactory(options).getDriver());
        }
        return threadLocalDriver.get();
    }

    public static WebDriver getWebDriver(FirefoxOptions options) {
        if (threadLocalDriver.get() == null) {
            putToMap(DriverFactoryBuilder.getFactory(options).getDriver());
        }
        return threadLocalDriver.get();
    }

    public static WebDriver getWebDriver(EdgeOptions options) {
        if (threadLocalDriver.get() == null) {
            putToMap(DriverFactoryBuilder.getFactory(options).getDriver());
        }
        return threadLocalDriver.get();
    }

    private static void putToMap(WebDriver driver) {
        driver.manage().timeouts().implicitlyWait(Configuration.getInstance().getDuration());
        threadLocalDriver.set(driver);
        threadLocalWait.set(new WebDriverWait(driver, Configuration.getInstance().getDuration()));
    }

    public static void quitAll() {
        threadLocalDriver.remove();
        threadLocalWait.remove();
    }
}