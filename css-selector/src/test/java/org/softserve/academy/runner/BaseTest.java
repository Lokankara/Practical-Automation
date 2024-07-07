package org.softserve.academy.runner;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInfo;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public abstract class BaseTest {

    protected static final String BASE_URL = "http://speak-ukrainian.eastus2.cloudapp.azure.com/dev";
    private static final String TIME_TEMPLATE = "yyyy-MM-dd_HH-mm-ss-S";

    protected static WebDriver driver;
    protected static WebDriverWait wait;
    protected static JavascriptExecutor executor;
    protected static boolean isTestSuccessful;

    @BeforeEach
    void setUpEach() {
        isTestSuccessful = false;
        driver.get(BASE_URL);
    }

    @AfterEach
    void tearDownEach(TestInfo testInfo) {
        executor.executeScript("window.localStorage.clear()");
        driver.manage().deleteAllCookies();
        if (!isTestSuccessful) {
            takeShot();
            takePageSource();
            System.err.println(testInfo.getDisplayName());
        }
    }

    @BeforeAll
    static void setUpAll() {
        try {
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
            wait = new WebDriverWait(driver, Duration.ofSeconds(12));
        } catch (Exception e) {
            System.err.println(e.getMessage());
            Assertions.fail("Setup failed: " + e.getMessage());
        }
    }

    @AfterAll
    static void tearDownAll() {
        try {
            if (driver != null) {
                driver.quit();
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    protected void assertEnable(WebElement element, String message) {
        assertVisible(element, message);
        assertTrue(element.isEnabled(), String.format("%s should be enabled", message));
    }

    protected static void assertVisible(WebElement element, String message) {
        assertNotNull(element, String.format("%s should be visible", message));
        assertTrue(element.isDisplayed(), String.format("%s should be displayed", message));
    }

    protected void assertTextPresent(WebElement element, String message) {
        assertVisible(element, message);
        assertNotNull(element.getText(), String.format("%s should be not null", message));
    }

    protected void assertTextEquals(String expected, WebElement element, String message) {
        assertTextPresent(element, message);
        assertEquals(expected, element.getText(), String.format("%s text should match %s", message, expected));
    }

    protected void clickElementWithJS(WebElement element) {
        if (element != null && element.isDisplayed() && element.isEnabled()) {
            executor.executeScript("arguments[0].dispatchEvent(new MouseEvent('click', {bubbles: true, cancelable: true, view: window}));", element);
        } else {
            throw new IllegalArgumentException("Element is not clickable: " + element);
        }
    }

    protected void scrollToElement(WebElement element) {
        assertNotNull(element, "Element should be present");
        executor.executeScript("arguments[0].scrollIntoView(true);", element);
    }

    private void takeShot() {
        String currentTime = new SimpleDateFormat(TIME_TEMPLATE).format(new Date());
        File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        String separator = System.getProperty("os.name").toLowerCase().contains("win") ? "\\" : "/";
        String pathName = String.format(".%sscreenshots%s%s_screenshot.png", separator, separator, currentTime);

        try {
            FileUtils.copyFile(scrFile, new File(pathName));
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    private void takePageSource() {
        String currentTime = new SimpleDateFormat(TIME_TEMPLATE).format(new Date());

        String separator = System.getProperty("os.name").toLowerCase().contains("win") ? "\\" : "/";
        String sourceHtml = String.format(".%sreport%s%s_source.html", separator, separator, currentTime);
        Path path = Paths.get(sourceHtml);

        try {
            byte[] bytes = driver.getPageSource().getBytes();
            Files.write(path, bytes, StandardOpenOption.CREATE);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
}
