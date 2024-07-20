package com.softserve.edu.teachua.wraps.browser;

import com.softserve.edu.teachua.tools.PropertiesUtils;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;

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

public class DriverUtilsSingleton {
    private static volatile DriverUtilsSingleton instance = null;
    private final String TIME_TEMPLATE = "yyyy-MM-dd_HH-mm-ss-S";
    private static final String LOCALSTORAGE_REMOVE_ITEM = "window.localStorage.removeItem('%s');";
    private static final ThreadLocal<WebDriver> drivers = new ThreadLocal<>();
    private Browsers defaultBrowser;

    private DriverUtilsSingleton() {
        initDriver();
    }

    public static DriverUtilsSingleton get() {
        if (instance == null) {
            synchronized (DriverUtilsSingleton.class) {
                if (instance == null) {
                    instance = new DriverUtilsSingleton();
                }
            }
        }
        return instance;
    }

    private void initDriver() {
        String browserName = PropertiesUtils.get().readBrowserName();
        if (browserName.equals(PropertiesUtils.ERROR_READ_PROPERTY)) {
            defaultBrowser = Browsers.DEFAULT_TEMPORARY;
        } else {
            defaultBrowser = getBrowserByPartialName(browserName);
        }
    }

    private Browsers getBrowserByPartialName(String browserName) {
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

    public WebDriver addDriverByPartialName(String browserName) {
        return addDriver(getBrowserByPartialName(browserName));
    }

    public WebDriver addDriver(Browsers browser) {
        drivers.set(browser.runBrowser());
        return drivers.get();
    }

    public WebDriver getDriver() {
        WebDriver driver = drivers.get();
        if (driver == null) {
            driver = addDriver(defaultBrowser);
        }
        return driver;
    }

    public void setImplicitWait(long waitSeconds) {
        getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(waitSeconds));
    }

    public void clearImplicitWait() {
        getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(0L));
    }

    public void refreshPage() {
        getDriver().navigate().refresh();
    }

    public void navigateToUrl(String url) {
        getDriver().navigate().to(url);
    }

    public void getUrl(String url) {
        getDriver().get(url);
    }

    public void switchToFrame(WebElement webElement) {
        getDriver().switchTo().frame(webElement);
    }

    public void switchToDefaultContent() {
        getDriver().switchTo().defaultContent();
    }

    public void takeScreenShot() {
        LocalDateTime localDate = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(TIME_TEMPLATE);
        String currentTime = localDate.format(formatter);
        //
        File scrFile = ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.FILE);
        try {
            FileUtils.copyFile(scrFile, new File("./" + currentTime + "_screenshot.png"));
        } catch (IOException e) {
            // Log.error
            throw new RuntimeException(e);
        }
    }

    public void takePageSource() {
        String currentTime = new SimpleDateFormat(TIME_TEMPLATE).format(new Date());
        String pageSource = getDriver().getPageSource();
        byte[] strToBytes = pageSource.getBytes();
        Path path = Paths.get("./" + currentTime + "_" + "_source.html.txt");
        try {
            Files.write(path, strToBytes, StandardOpenOption.CREATE);
        } catch (IOException e) {
            // Log.error
            throw new RuntimeException(e);
        }
    }

    public void deleteCookies() {
        getDriver().manage().deleteAllCookies();
    }

    public void deleteTokens() {
        JavascriptExecutor js = (JavascriptExecutor) getDriver();
        js.executeScript(String.format(LOCALSTORAGE_REMOVE_ITEM, "accessToken"));
        js.executeScript(String.format(LOCALSTORAGE_REMOVE_ITEM, "refreshToken"));
    }

    public void scrollToElement(WebElement webElement) {
        JavascriptExecutor js = (JavascriptExecutor) getDriver();
        js.executeScript("arguments[0].scrollIntoView(true);", webElement);
    }

    public void quit() {
        if (getDriver() != null) {
            getDriver().quit();
        }
    }

}
