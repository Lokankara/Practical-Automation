package com.softserve.edu.teachua.tests;

import com.softserve.edu.factory.BrowserFactory;
import com.softserve.edu.factory.Browsers;
import com.softserve.edu.util.Configuration;
import com.softserve.edu.util.DriverWrapper;
import com.softserve.edu.util.LoggerUtils;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.extension.ExtendWith;
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
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@ExtendWith(RunnerExtension.class)
public abstract class TestRunner {
    private static final String TIME_TEMPLATE = "yyyy-MM-dd_HH-mm-ss-S";
    protected static boolean isTestSuccessful = false;
    private static BrowserFactory browserFactory;
    protected DriverWrapper driverWrapper;
    protected static Integer waitSeconds;

    @BeforeAll
    public static void setupClass() {
        Browsers browser = Browsers.fromString(Configuration.getInstance().getBrowser());
        browserFactory = BrowserFactory.valueOf(browser.name());
        waitSeconds = Configuration.getInstance().getImplicitWait();
    }

    private void takeScreenShot() {
        LocalDateTime localDate = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(TIME_TEMPLATE);
        String currentTime = localDate.format(formatter);
        File scrFile = ((TakesScreenshot) driverWrapper.getDriver()).getScreenshotAs(OutputType.FILE);
        try {
            FileUtils.copyFile(scrFile, new File("./target/report/" + currentTime + "_screenshot.png"));
        } catch (IOException e) {
            LoggerUtils.logError(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    private void takePageSource() {
        String currentTime = new SimpleDateFormat(TIME_TEMPLATE).format(new Date());
        String pageSource = driverWrapper.getDriver().getPageSource();
        byte[] strToBytes = pageSource.getBytes();
        Path path = Paths.get("./target/report/" + currentTime + "_" + "_source.html.txt");
        try {
            Files.write(path, strToBytes, StandardOpenOption.CREATE);
        } catch (IOException e) {
            LoggerUtils.logError(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @BeforeEach
    public void beforeEach() {
        WebDriver driver = browserFactory.createDriver();
        driverWrapper = new DriverWrapper(driver, waitSeconds);
    }

    @AfterEach
    public void afterEach(TestInfo testInfo) {
        if (!isTestSuccessful) {
            LoggerUtils.logWarn("\t\t\tTest_Name = ", testInfo.getDisplayName(), " fail");
            LoggerUtils.logWarn("\t\t\tTest_Method = ", String.valueOf(testInfo.getTestMethod()), " fail");
            takeScreenShot();
            takePageSource();
        }
        driverWrapper.getDriver().manage().deleteAllCookies();
        driverWrapper.getDriver().quit();
    }
}
