package com.softserve.edu.runner;

import com.softserve.edu.reporter.LoggerUtils;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.service.DriverService;

import static org.junit.jupiter.api.Assertions.assertTrue;

public abstract class BaseBrowserTest extends BaseTest {

    public static DriverService service;
    public static final String TIME_TEMPLATE = "yyyy-MM-dd_HH-mm-ss-S";

    @BeforeAll
    public static void setupDrivers() {
        LoggerUtils.logInfo("BeforeAll");
    }

    @AfterAll
    public static void StopService() {
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
        LoggerUtils.logInfo("Page title is", driver.getTitle());
    }

    protected void assertContainsUrl(String actualUrl, String expected) {
        assertTrue(actualUrl.contains(expected), String.format("URL should be the : %n%s%n%s%n", expected, actualUrl));
    }

    protected void assertTitle(String actualTitle, String expected) {
        assertTrue(actualTitle.contains(expected), String.format("Title should be contains: %n%s%n%s%n", expected, actualTitle));
    }
}
