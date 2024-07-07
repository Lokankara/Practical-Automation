package com.softserve.edu.runner;

import com.softserve.edu.manager.Browsers;
import com.softserve.edu.reporter.LoggerUtils;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.service.DriverService;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

import static org.junit.jupiter.api.Assertions.assertTrue;

public abstract class BaseBrowserTest extends BaseTest {

    public static DriverService service;
    public static final String TIME_TEMPLATE = "yyyy-MM-dd_HH-mm-ss-S";
    private static final Map<String, WebDriver> drivers = new ConcurrentHashMap<>();

    public WebDriver getDriver(Browsers browser) {
        String threadName = String.valueOf(Thread.currentThread().getName());
        LoggerUtils.logInfo("Thread", threadName, browser.name());
        return drivers.computeIfAbsent(threadName, k -> getWebDriver(browser));
    }

    public WebDriver getDriver(ChromeOptions options) {
        String threadName = String.valueOf(Thread.currentThread().getName());
        LoggerUtils.logInfo("Thread", threadName, options.getBrowserName());
        return drivers.computeIfAbsent(threadName, k -> getWebDriver(options));
    }

    public WebDriver getDriver(EdgeOptions options) {
        String threadName = String.valueOf(Thread.currentThread().getName());
        LoggerUtils.logInfo("Thread", threadName, options.getBrowserName());
        return drivers.computeIfAbsent(threadName, k -> getWebDriver(options));
    }

    public WebDriver getDriver(FirefoxOptions options) {
        String threadName = String.valueOf(Thread.currentThread().getName());
        LoggerUtils.logInfo("Thread", threadName, options.getBrowserName());
        return drivers.computeIfAbsent(threadName, k -> getWebDriver(options));
    }

    @BeforeAll
    public static void setupDrivers() {
        LoggerUtils.logInfo("BeforeAll");
    }

    @AfterEach
    void tearDown() {
        String threadName = Thread.currentThread().getName();
        LoggerUtils.logPass("Finished", threadName);
    }

    @AfterAll
    public static void StopService() {
        drivers.values().stream().filter(Objects::nonNull).forEach(WebDriver::quit);
        drivers.clear();
        if (service != null) {
            service.stop();
            LoggerUtils.logInfo("+++RemoteWebDriver Stop");
        }
    }

    @BeforeEach
    void setUpWebDriver() {
        String threadName = Thread.currentThread().getName();
        LoggerUtils.logInfo("BeforeEach", threadName);
    }

    protected void fillSearchSubmit(WebDriver driver, String value) {
        WebElement element = driver.findElement(By.name("q"));
        element.sendKeys(value);
        element.submit();
    }

    protected void assertContains(String actual, String expected) {
        assertTrue(actual.contains(expected),
                String.format("URL should be the : %n%s%n%s%n", expected, actual));
    }
}
