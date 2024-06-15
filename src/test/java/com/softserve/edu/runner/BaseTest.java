package com.softserve.edu.runner;

import com.softserve.edu.manager.Browsers;
import com.softserve.edu.manager.DriverManager;
import com.softserve.edu.reporter.LoggerUtils;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.AfterAll;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.softserve.edu.runner.BaseBrowserTest.TIME_TEMPLATE;

public abstract class BaseTest {

    @AfterAll
    public static void tear() {
        DriverManager.quitAll();
        LoggerUtils.logPass("@AfterAll executed",
                String.valueOf(Thread.currentThread().getName()));
    }

    public WebDriver getWebDriver(Browsers browser) {
        String threadName = Thread.currentThread().getName();
        LoggerUtils.logInfo("Thread", threadName, browser.name());
        return DriverManager.getWebDriver(browser);
    }

    public WebDriver getWebDriver(ChromeOptions options) {
        String threadName = Thread.currentThread().getName();
        LoggerUtils.logInfo("Thread", threadName, options.getBrowserName());
        return DriverManager.getWebDriver(options);
    }

    public WebDriver getWebDriver(EdgeOptions options) {
        String threadName = Thread.currentThread().getName();
        LoggerUtils.logInfo("Thread", threadName, options.getBrowserName());
        return DriverManager.getWebDriver(options);
    }

    public WebDriver getWebDriver(FirefoxOptions options) {
        String threadName = Thread.currentThread().getName();
        LoggerUtils.logInfo("Thread", threadName, options.getBrowserName());
        return DriverManager.getWebDriver(options);
    }

    protected void takeScreenShot(WebDriver driver) {
        String currentTime = new SimpleDateFormat(TIME_TEMPLATE).format(new Date());
        File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        try {
            FileUtils.copyFile(scrFile, new File("./target/reports/" + currentTime + "_screenshot.png"));
        } catch (IOException e) {
            LoggerUtils.logFatal("Can`t take a ScreenShot", e.getMessage());
        }
    }
}
