package com.softserve.edu.teachua.wraps.browser;

import com.softserve.edu.teachua.exceptions.IOCustomException;
import com.softserve.edu.teachua.tools.PropertiesUtils;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

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

public final class DriverUtils {
    private static final Logger logger = Logger.getLogger(DriverUtils.class.getName());
    private static final String TIME_TEMPLATE = "yyyy-MM-dd_HH-mm-ss-S";
    private static final String LOCALSTORAGE_REMOVE_ITEM = "window.localStorage.removeItem('%s');";
    private static final ThreadLocal<WebDriver> drivers = new ThreadLocal<>();
    private static Browsers defaultBrowser;

    private static class SingletonHelper {
        private static final DriverUtils INSTANCE = new DriverUtils();
    }

    public static DriverUtils getInstance() {
        return SingletonHelper.INSTANCE;
    }

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

    public WebDriver addDriverByPartialName(String browserName) {
        return addDriver(getBrowserByPartialName(browserName));
    }

    public WebDriver addDriver(Browsers browser) {
        drivers.set(browser.runBrowser());
        return drivers.get();
    }

    public WebDriver getDriver() {
        WebDriver driver = drivers.get();
        return driver == null ? addDriver(defaultBrowser) : driver;
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

    public void deleteCookies() {
        getDriver().manage().deleteAllCookies();
    }

    public void deleteTokens() {
        JavascriptExecutor js = (JavascriptExecutor) getDriver();
        js.executeScript(String.format(LOCALSTORAGE_REMOVE_ITEM, "accessToken"));
        js.executeScript(String.format(LOCALSTORAGE_REMOVE_ITEM, "refreshToken"));
    }

    public void quit() {
        if (drivers.get() != null) {
            drivers.get().quit();
        }
    }

    public void quitAll() {
        quit();
        drivers.remove();
    }

    public void takeScreenShot() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(TIME_TEMPLATE);
        String currentTime = LocalDateTime.now().format(formatter);
        File scrFile = ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.FILE);
        try {
            FileUtils.copyFile(scrFile, new File("./target/report/" + currentTime + "_screenshot.png"));
        } catch (IOException e) {
            logger.error("Error occurred during IO operation", e);
            throw new IOCustomException(e.getMessage());
        }
    }

    public void takePageSource() {
        String currentTime = new SimpleDateFormat(TIME_TEMPLATE).format(new Date());
        byte[] strToBytes = getDriver().getPageSource().getBytes();
        Path path = Paths.get("./target/report/" + currentTime + "_" + "_source.html.txt");
        try {
            Files.write(path, strToBytes, StandardOpenOption.CREATE);
        } catch (IOException e) {
            logger.error("Error occurred during IO operation", e);
            throw new IOCustomException(e.getMessage());
        }
    }
}
