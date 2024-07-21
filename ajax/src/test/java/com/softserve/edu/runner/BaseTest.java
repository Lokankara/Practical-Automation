package com.softserve.edu.runner;

import io.github.bonigarcia.wdm.WebDriverManager;
import lombok.Getter;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.TestInstance;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public abstract class BaseTest {

    @Getter
    private WebDriver driver;
    protected JavascriptExecutor executor;
    public static final Long IMPLICITLY_WAIT_SECONDS = 5L;
    protected static final String TIME_TEMPLATE = "yyyy-MM-dd_HH-mm-ss-S";
    protected final Logger logger = LogManager.getLogger(BaseTest.class);

    @AfterEach
    public void afterEach(final TestInfo testInfo) {
        deleteCookie("dx-cookie-policy");
        takeShot(testInfo);
        clearStorage();
    }

    @BeforeAll
    public void beforeAll() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions chromeOptions = new ChromeOptions();
        if ("true".equals(System.getenv("CI"))) {
            chromeOptions.addArguments("--headless");
            chromeOptions.addArguments("--incognito");
            chromeOptions.addArguments("--no-sandbox");
            chromeOptions.addArguments("--disable-gpu");
            chromeOptions.addArguments("--disable-web-security");
            chromeOptions.addArguments("--disable-dev-shm-usage");
        }
        driver = new ChromeDriver(chromeOptions);
        executor = (JavascriptExecutor) driver;
        driver.manage().timeouts().implicitlyWait(Duration.ZERO);
        driver.manage().window().setSize(new Dimension(1400, 997));
    }

    @AfterAll
    public void afterAll() {
        if (driver != null) {
            deleteCookies();
            closeDriver();
        } else {
            logger.warn("Driver closed");
        }
    }

    protected void clearStorage() {
        if (executor != null) {
            executor.executeScript("window.localStorage.clear()");
            executor.executeScript("window.sessionStorage.clear();");
            logger.info("Local and session storage cleared.");
        }
    }

    protected void deleteCookie(final String name) {
        Cookie cookie = driver.manage().getCookieNamed(name);
        if (cookie != null) {
            driver.manage().deleteCookie(cookie);
            logger.info("Cookie {} deleted.", name);
        }
    }

    protected void deleteCookies() {
        if (driver != null) {
            driver.manage().deleteAllCookies();
            logger.info("All cookies deleted.");
        }
    }

    protected void closeDriver() {
        if (driver != null) {
            driver.quit();
            driver = null;
            logger.info("Driver closed.");
        }
    }

    protected void takeShot(final TestInfo testInfo) {
        String currentTime = new SimpleDateFormat(TIME_TEMPLATE).format(new Date());
        File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        String separator = System.getProperty("os.name").toLowerCase().contains("win") ? "\\" : "/";
        String pathName = String.format(".%starget%sreports%s%s_%s_screenshot.png",
                separator, separator, separator, currentTime, testInfo.getDisplayName());
        try {
            FileUtils.copyFile(scrFile, new File(pathName));
            logger.info("Take shot: {}",  testInfo.getDisplayName());
        } catch (IOException e) {
            logger.error(e.getMessage());
            Assertions.fail("Setup failed: " + e.getMessage());
        }
    }
}
