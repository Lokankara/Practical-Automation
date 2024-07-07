package com.softserve.edu.teachua.wraps.browser;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.softserve.edu.teachua.tools.ReportUtils;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.softserve.edu.teachua.tools.PropertiesUtils;
import org.apache.commons.io.FileUtils;

public final class DriverUtils {
    private static final String TIME_TEMPLATE = "yyyy-MM-dd_HH-mm-ss-S";
    private static final String LOCALSTORAGE_REMOVE_ITEM = "window.localStorage.removeItem('%s');";
    private static Map<Long, WebDriver> drivers = new HashMap<>();
    private static Browsers defaultBrowser;

    static {
        initDriver();
    }

    private DriverUtils() {
        initDriver();
    }

    private static void initDriver() {
        String browserName = PropertiesUtils.get().readBrowserName();
        if (browserName.equals(PropertiesUtils.ERROR_READ_PROPERTY)) {
            defaultBrowser = Browsers.DEFAULT_TEMPORARY;
            ReportUtils.logAction(Browsers.DEFAULT_TEMPORARY.toString());
        } else {
            defaultBrowser = getBrowserByPartialName(browserName);
            ReportUtils.logAction("Initializing driver with browser: " + defaultBrowser.toString());
        }
    }

    private static Browsers getBrowserByPartialName(String browserName) {
        Browsers browser = Browsers.DEFAULT_TEMPORARY;
        browserName = browserName.toLowerCase()
                .replaceAll("[_-]", " ")
                .replaceAll("[ ]+", " ")
                .trim();

        for (Browsers current : Browsers.values()) {
            String currentName = current.name().toLowerCase().replace("_", " ");
            if (currentName.contains(browserName)) {
                browser = current;
                break;
            }
        }
        return browser;
    }

    public static WebDriver addDriverByPartialName(String browserName) {
        ReportUtils.logInfo("Added driver for browser by partial name: " + browserName);
        return addDriver(getBrowserByPartialName(browserName));
    }

    public static WebDriver addDriver(Browsers browser) {
        drivers.put(Thread.currentThread().getId(), browser.runBrowser());
        ReportUtils.logInfo("Added driver for browser: " + browser);
        return drivers.get(Thread.currentThread().getId());
    }
    
    public static WebDriver getDriver() {
        WebDriver driver = drivers.get(Thread.currentThread().getId());
        if (driver == null) {
            driver = addDriver(defaultBrowser);
            ReportUtils.logInfo("Initialized driver with default browser: " + defaultBrowser.toString());
        }
        ReportUtils.logInfo("Retrieving driver");
        return driver;
    }

    public static void setImplicitWait(long waitSeconds) {
        getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(waitSeconds));
        ReportUtils.logAction("Set implicit wait to " + waitSeconds + " seconds");
    }

    public static void clearImplicitWait() {
        getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(0L));
        ReportUtils.logAction("Cleared implicit wait");
    }

    public static void refreshPage() {
        getDriver().navigate().refresh();
        ReportUtils.logAction("Page refreshed");
    }

    public static void navigateToUrl(String url) {
        getDriver().navigate().to(url);
        ReportUtils.logAction("Navigated to URL: " + url);
    }

    public static void getUrl(String url) {
        getDriver().get(url);
        ReportUtils.logAction("Opened URL: " + url);
    }

    public static void switchToFrame(WebElement webElement) {
        getDriver().switchTo().frame(webElement);
        ReportUtils.logAction("Switched to frame: " + webElement);
    }

    public static void switchToDefaultContent() {
        getDriver().switchTo().defaultContent();
        ReportUtils.logAction("Switched to default content");
    }

    public static void takeScreenShot() {
        LocalDateTime localDate = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(TIME_TEMPLATE);
        String currentTime = localDate.format(formatter);
        File scrFile = ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.FILE);
        try {
            FileUtils.copyFile(scrFile, new File("./target/report" + currentTime + "_screenshot.png"));
            ReportUtils.attachFile("Screenshot", Paths.get("./target/report" + currentTime + "_screenshot.png"));
            ReportUtils.logAction("Screenshot taken at " + currentTime);
        } catch (IOException e) {
            ReportUtils.logError("Failed to take screenshot: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public static void takePageSource() {
        String currentTime = new SimpleDateFormat(TIME_TEMPLATE).format(new Date());
        String pageSource = getDriver().getPageSource();
        byte[] strToBytes = pageSource.getBytes();
        Path path = Paths.get("./target/report" + currentTime + "_" + "_source.html.txt");
        try {
            Files.write(path, strToBytes, StandardOpenOption.CREATE);
            ReportUtils.attachFile("Page Source", path);
            ReportUtils.logAction("Page source saved at " + currentTime);
        } catch (IOException e) {
            ReportUtils.logError("Failed to save page source: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public static void deleteCookies() {
        getDriver().manage().deleteAllCookies();
        ReportUtils.logAction("Deleted all cookies");
    }

    public static void deleteTokens() {
        JavascriptExecutor js = (JavascriptExecutor) getDriver();
        js.executeScript(String.format(LOCALSTORAGE_REMOVE_ITEM, "accessToken"));
        js.executeScript(String.format(LOCALSTORAGE_REMOVE_ITEM, "refreshToken"));
        ReportUtils.logAction("Deleted tokens from local storage");
    }

    public static void scrollToElement(WebElement webElement) {
        JavascriptExecutor js = (JavascriptExecutor) getDriver();
        js.executeScript("arguments[0].scrollIntoView(true);", webElement);
    }

    public static void quit() {
        drivers.entrySet().stream()
                .filter(driverEntry -> driverEntry.getValue() != null)
                .forEach(driverEntry -> {
                    driverEntry.getValue().quit();
                    ReportUtils.logAction("Quit driver for thread " + driverEntry.getKey());
                });
    }
}
