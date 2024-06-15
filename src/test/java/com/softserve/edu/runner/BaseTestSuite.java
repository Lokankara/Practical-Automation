package com.softserve.edu.runner;

import com.softserve.edu.manager.DriverManager;
import com.softserve.edu.reporter.LoggerUtils;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static com.softserve.edu.runner.TestData.BASE_URL;
import static com.softserve.edu.runner.TestData.BROWSER;

public abstract class BaseTestSuite extends BaseTest {

    public boolean isSuccess;

    protected WebDriver driver;
    protected WebDriverWait wait;

    @BeforeAll
    public static void setup() {
        LoggerUtils.logInfo("@BeforeAll executed", "Base URL used", BROWSER.name());
    }

    @BeforeEach
    void setUp() {
        driver = getWebDriver(BROWSER);
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        LoggerUtils.logInfo("Selected browser", BROWSER.name(), "Base URL used", BASE_URL);
    }

    @AfterEach
    void tearDown() {
        String threadName = Thread.currentThread().getName();
        LoggerUtils.logPass("Closed", threadName);
    }

    @AfterAll
    public static void tear() {
        DriverManager.quitAll();
        LoggerUtils.logPass("@AfterAll executed",
                String.valueOf(Thread.currentThread().getName()));
    }
}