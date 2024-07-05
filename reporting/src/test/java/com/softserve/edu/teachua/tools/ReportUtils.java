package com.softserve.edu.teachua.tools;

import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public final class ReportUtils {
    private static final Logger logger = LoggerFactory.getLogger(ReportUtils.class);

    private ReportUtils() {
    }

    @Step("Log test start: {testName}")
    public static void logTestStart(String testName) {
        String message = "Starting test: " + testName + " at " + java.time.LocalDateTime.now();
        logger.info(message);
        Allure.addAttachment("Test Start", "text/plain", message);
    }

    @Step("Log test end: {testName}")
    public static void logTestEnd(String testName) {
        String message = "Ending test: " + testName + " at " + java.time.LocalDateTime.now();
        logger.info(message);
        Allure.addAttachment("Test End", "text/plain", message);
    }

    @Step("Log action: {action}")
    public static void logAction(String action) {
        logger.info(action);
        Allure.addAttachment("Action", "text/plain", action);
    }

    @Step("Log error: {error}")
    public static void logError(String error) {
        logger.error(error);
        Allure.addAttachment("Error", "text/plain", error);
    }

    public static void attachScreenshot(byte[] screenshotBytes) {
        Allure.addAttachment("Screenshot", new ByteArrayInputStream(screenshotBytes));
    }

    public static void attachPageSource(String pageSource) {
        Allure.addAttachment("Page Source", "text/plain", pageSource);
    }

    public static void attachFile(String name, Path path) {
        try (InputStream is = Files.newInputStream(path)) {
            byte[] content = is.readAllBytes();
            Allure.addAttachment(name, new ByteArrayInputStream(content));
        } catch (IOException e) {
            logger.error("Failed to attach file: {}", path, e);
        }
    }

    @Step("Info: {message}")
    public static void logInfo(String message) {
        logger.info(message);
        Allure.addAttachment("Message", "text/plain", message);
    }

    public static void logBrowserDetails(WebDriver driver) {
        Capabilities capabilities = ((RemoteWebDriver) driver).getCapabilities();
        String browserName = capabilities.getBrowserName();
        String browserVersion = capabilities.getCapability("browserVersion").toString();
        String platformName = capabilities.getCapability("platformName").toString();
        ReportUtils.logBrowserDetails(browserName, browserVersion, platformName);

    }
    @Step("Log browser details: Name: {browserName}, Version: {browserVersion}, Platform: {platformName}")
    public static void logBrowserDetails(String browserName, String browserVersion, String platformName) {
        logger.info("Browser Name: {}, Version: {}, Platform: {}", browserName, browserVersion, platformName);
        Allure.addAttachment("Browser Details", "text/plain", "Name: " + browserName + "\nVersion: " + browserVersion + "\nPlatform: " + platformName);
    }
}
