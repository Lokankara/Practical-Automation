package com.softserve.edu.runner;

import com.softserve.edu.manager.DriverManager;
import com.softserve.edu.reporter.LoggerUtils;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import static com.softserve.edu.runner.TestData.BASE_URL;
import static com.softserve.edu.runner.TestData.BROWSER;

public abstract class BaseTestSuite {

    protected static final WebDriver driver = DriverManager.getWebDriver(BROWSER.name());
    protected static final WebDriverWait wait = DriverManager.getWait(BROWSER.name());

    @BeforeAll
    public static void setup() {
        LoggerUtils.logInfo("@BeforeAll executed into BaseTestSuite into browser ", BROWSER.name());
    }

    @AfterEach
    public void tearDown() {
        LoggerUtils.logInfo("Test finished, page closed");
    }

    @AfterAll
    public static void tear() {
        LoggerUtils.logPass("@AfterAll executed BaseTestSuite");
    }

    @BeforeEach
    public void setUp() {
        LoggerUtils.logInfo("Selected browser", BROWSER.name(), "Base URL used", BASE_URL);
    }
}
