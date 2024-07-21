package com.softserve.edu.teachua.tests;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.time.Duration;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public abstract class BaseTest {
    private WebDriver driver;
    private JavascriptExecutor executor;
    private static final Duration IMPLICITLY_WAIT = Duration.ofSeconds(10L);
    protected final Logger logger = LogManager.getLogger(BaseTest.class);
    protected static final String BASE_URL = "http://speak-ukrainian.eastus2.cloudapp.azure.com/dev/";

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
        driver.manage().timeouts().implicitlyWait(IMPLICITLY_WAIT);
        driver.manage().window().setSize(new Dimension(1400, 997));
        executor = (JavascriptExecutor) driver;
    }

    @AfterAll
    public void afterAll() {
        if (driver != null) {
            driver.quit();
            logger.info("Driver is closed.");
        }
    }

    protected void clearStorage() {
        if (executor != null) {
            executor.executeScript("window.localStorage.clear()");
            executor.executeScript("window.sessionStorage.clear();");
            logger.info("Local and session storage cleared.");
        }
    }

    protected void deleteCookies() {
        if (driver != null) {
            driver.manage().deleteAllCookies();
            logger.info("Delete All Cookies.");
        }
    }

    protected WebDriver getDriver() {
        return driver;
    }
}
