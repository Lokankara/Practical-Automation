package com.softserve.edu.manager;

import com.softserve.edu.reporter.LoggerUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class DriverManager {

    private static final ThreadLocal<WebDriver> threadLocalDriver = new ThreadLocal<>();
    private static final ThreadLocal<WebDriverWait> threadLocalWait = new ThreadLocal<>();
    private static final ConcurrentMap<String, WebDriver> drivers = new ConcurrentHashMap<>();

    private DriverManager() {
    }

    public static WebDriver getWebDriver(String browser) {
        if (threadLocalDriver.get() == null) {
            WebDriver driver = DriverFactoryBuilder.getFactory(browser).getDriver();
            threadLocalDriver.set(driver);
            threadLocalWait.set(new WebDriverWait(driver, Configuration.getInstance().getDuration()));
            drivers.put(Thread.currentThread().getName(), driver);
        }
        return threadLocalDriver.get();
    }

    public static WebDriverWait getWait(String browser) {
        WebDriverWait wait = threadLocalWait.get();
        if (wait == null) {
            WebDriver driver = threadLocalDriver.get();
            if (driver == null) {
                driver = DriverFactoryBuilder.getFactory(browser).getDriver();
                threadLocalDriver.set(driver);
            }
            wait = new WebDriverWait(driver, Configuration.getInstance().getDuration());
            threadLocalWait.set(wait);
        }
        return wait;
    }

    public static void quit() {
        WebDriver driver = threadLocalDriver.get();
        if (driver != null) {
            driver.quit();
            threadLocalDriver.remove();
            threadLocalWait.remove();
        } else {
            LoggerUtils.logFatal("WebDriver was not initialized properly.");
        }
    }

    public static void quitAll() {
        drivers.values().stream().filter(Objects::nonNull).forEach(WebDriver::quit);
        drivers.clear();
    }
}
