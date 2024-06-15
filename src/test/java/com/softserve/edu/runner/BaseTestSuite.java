package com.softserve.edu.runner;

import com.softserve.edu.manager.Browsers;
import com.softserve.edu.manager.DriverManager;
import com.softserve.edu.reporter.LoggerUtils;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

import static com.softserve.edu.runner.TestData.BASE_URL;
import static com.softserve.edu.runner.TestData.BROWSER;

public abstract class BaseTestSuite extends BaseTest {
    public boolean isSuccess;
    protected WebDriver driver;
    protected WebDriverWait wait;
    private static final Map<String, WebDriver> drivers = new ConcurrentHashMap<>();

    public WebDriver getDriver(Browsers browser) {
        String threadName = String.valueOf(Thread.currentThread().getId());
        LoggerUtils.logInfo("Thread", threadName, browser.name());
        return drivers.computeIfAbsent(threadName, k -> getWebDriver(browser));
    }

    @BeforeAll
    public static void setup() {
        LoggerUtils.logInfo("@BeforeAll executed", "Base URL used", BROWSER.name());
    }

    @BeforeEach
    void setUp() {
        driver = getDriver(BROWSER);
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
        drivers.values().stream().filter(Objects::nonNull).forEach(WebDriver::quit);
        drivers.clear();
        LoggerUtils.logPass("@AfterAll executed",
                String.valueOf(Thread.currentThread().getName()));
    }
}