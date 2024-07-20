package com.softserve.edu.teachua.wraps.browser;

import com.softserve.edu.teachua.exception.FileIOException;
import com.softserve.edu.teachua.tools.PropertiesUtils;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

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

public final class DriverUtils {
    private static final String TIME_TEMPLATE = "yyyy-MM-dd_HH-mm-ss-S";
    private static final String LOCALSTORAGE_REMOVE_ITEM = "window.localStorage.removeItem('%s');";
    private static final Map<String, WebDriver> drivers = new HashMap<>();
    private static Browsers defaultBrowser;

    static {
        initDriver();
    }

    private DriverUtils() {
        initDriver();
    }

    private static void initDriver() {
        String browserName = PropertiesUtils.get().readBrowserName();
        defaultBrowser = browserName.equals(PropertiesUtils.ERROR_READ_PROPERTY)
                ? Browsers.DEFAULT_TEMPORARY
                : getBrowserByPartialName(browserName);
    }

    private static Browsers getBrowserByPartialName(String browserName) {
        Browsers browser = Browsers.DEFAULT_TEMPORARY;
        browserName = browserName.toLowerCase()
                .replaceAll("[_-]", " ")
                .replaceAll("[ ]+", " ")
                .trim();
        for (Browsers current : Browsers.values()) {
            if (current.name().toLowerCase().replace("_", " ").contains(browserName)) {
                browser = current;
                break;
            }
        }
        return browser;
    }

    public static WebDriver addDriverByPartialName(String browserName) {
        return addDriver(getBrowserByPartialName(browserName));
    }

    public static WebDriver addDriver(Browsers browser) {
        drivers.put(Thread.currentThread().getName(), browser.runBrowser());
        return drivers.get(Thread.currentThread().getName());
    }

    public static WebDriver getDriver() {
        WebDriver driver = drivers.get(Thread.currentThread().getName());
        if (driver == null) {
            driver = addDriver(defaultBrowser);
        }
        return driver;
    }

    public static void setImplicitWait(long waitSeconds) {
        getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(waitSeconds));
    }

    public static void clearImplicitWait() {
        getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(0L));
    }

    public static void refreshPage() {
        getDriver().navigate().refresh();
    }

    public static void navigateToUrl(String url) {
        getDriver().navigate().to(url);
    }

    public static void getUrl(String url) {
        getDriver().get(url);
    }

    public static void switchToFrame(WebElement webElement) {
        getDriver().switchTo().frame(webElement);
    }

    public static void switchToDefaultContent() {
        getDriver().switchTo().defaultContent();
    }

    public static void takeScreenShot() {
        LocalDateTime localDate = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(TIME_TEMPLATE);
        String currentTime = localDate.format(formatter);
        File scrFile = ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.FILE);
        try {
            FileUtils.copyFile(scrFile, new File("./target/" + currentTime + "_screenshot.png"));
        } catch (IOException e) {
            throw new FileIOException(e.getMessage());
        }
    }

    public static void takePageSource() {
        String currentTime = new SimpleDateFormat(TIME_TEMPLATE).format(new Date());
        String pageSource = getDriver().getPageSource();
        byte[] strToBytes = pageSource.getBytes();
        Path path = Paths.get("./target/" + currentTime + "_" + "_source.html.txt");
        try {
            Files.write(path, strToBytes, StandardOpenOption.CREATE);
        } catch (IOException e) {
            throw new FileIOException(e.getMessage());
        }
    }

    public static void deleteCookies() {
        getDriver().manage().deleteAllCookies();
    }

    public static void deleteTokens() {
        JavascriptExecutor js = (JavascriptExecutor) getDriver();
        js.executeScript(String.format(LOCALSTORAGE_REMOVE_ITEM, "accessToken"));
        js.executeScript(String.format(LOCALSTORAGE_REMOVE_ITEM, "refreshToken"));
    }

    public static void scrollToElement(WebElement webElement) {
        JavascriptExecutor js = (JavascriptExecutor) getDriver();
        js.executeScript("arguments[0].scrollIntoView(true);", webElement);
    }

    public static void clickByJavaScript(WebElement webElement) {
        JavascriptExecutor js = (JavascriptExecutor) getDriver();
        js.executeScript("arguments[0].click();", webElement);
    }

    public static void quit() {
        drivers.entrySet().stream().filter(driverEntry -> driverEntry.getValue() != null).forEach(driverEntry -> driverEntry.getValue().quit());
    }
}
