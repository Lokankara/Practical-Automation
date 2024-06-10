package org.softserve.academy.runner;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInfo;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public abstract class BaseTest {

    protected static final String BASE_URL = "http://speak-ukrainian.eastus2.cloudapp.azure.com/dev";
    protected static final String SUCCESS_LOGIN_MESSAGE = "//div[contains(@class, 'ant-message-success')]";
    protected static final String DROPDOWN_MENU_XPATH = "//ul[contains(@class, 'ant-dropdown-menu')]";
    private static final String LOGIN_MENU_ITEM_XPATH = "//li[@role='menuitem']//div[text()='Увійти']";
    private static final String LOGIN_BUTTON_XPATH = "//button[contains(@class, 'login-button')]";
    protected static final String USER_ICON_XPATH = "//*[name()='svg'][@data-icon='user']";
    protected static final String PASSWORD_INPUT_XPATH = "//*[@id='basic_password']";
    protected static final String EMAIL_INPUT_XPATH = "//*[@id='basic_email']";
    private static final String TIME_TEMPLATE = "yyyy-MM-dd_HH-mm-ss-S";
    protected static final String EMAIL = "lijoyiv225@hutov.com";
    protected static final String PASSWORD = "Gard3ner#";
    protected static JavascriptExecutor executor;
    protected static boolean isTestSuccessful;
    protected static WebDriverWait wait;
    protected static WebDriver driver;

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
            wait = new WebDriverWait(driver, Duration.ofSeconds(5));
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
            Assertions.fail("Setup failed: " + e.getMessage());
        }
    }

    protected void loginUser(String email, String password) {
        openModalWindow();
        fillAndAssertField(EMAIL_INPUT_XPATH, email);
        fillAndAssertField(PASSWORD_INPUT_XPATH, password);

        clickElementWithJS(getLoginButton());

        WebElement successMessage = getVisibleElement(By.xpath(SUCCESS_LOGIN_MESSAGE));
        assertVisible(successMessage, "Success message");
    }

    protected void openModalWindow() {
        WebElement userIcon = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(USER_ICON_XPATH)));
        assertEnable(userIcon, "After filling all fields login button");
        scrollToElement(userIcon);
        clickElementWithJS(userIcon);
        WebElement dropdownMenu = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(DROPDOWN_MENU_XPATH)));
        assertNotNull(dropdownMenu);
        WebElement menuItem = findElementContainingText();
        scrollToElement(menuItem);
        clickElementWithJS(menuItem);
    }

    protected WebElement fillAndAssertField(String fieldxpath, String value) {
        WebElement field = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(fieldxpath)));
        assertNotNull(field, "Field with XPath '" + fieldxpath + "' should be present");
        field.clear();
        field.sendKeys(value);
        return field;
    }

    private WebElement findElementContainingText() {
        String script = "return Array.from(document.querySelectorAll(\"li[role='menuitem'] div\")).find(element => element.textContent.includes(\"Увійти\"));";
        return (WebElement) executor.executeScript(script);
    }

    protected WebElement getElementByXpath(String xpath) {
        return driver.findElement(By.xpath(xpath));
    }

    protected WebElement getVisibleElement(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }
    protected WebElement getClickableElement(By locator) {
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    protected WebElement getLoginButton() {
        WebElement loginButton = getVisibleElement(By.xpath(LOGIN_BUTTON_XPATH));
        assertVisible(loginButton, "Login button");
        assertTrue(loginButton.isEnabled(), "Login button should be enabled after filling all fields");
        assertFalse(loginButton.getAttribute("class").contains("ant-btn-disabled"), "Login button should be enabled after filling all fields");
        return loginButton;
    }

    protected void assertAttributeEquals(String expected, WebElement element, String attribute) {
        assertVisible(element, "Element with attribute " + attribute);
        assertEquals(expected, element.getAttribute(attribute), "Attribute should be with value " + expected);
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

    protected void assertContains(WebElement element, String xpath, String expectedText) {
        assertVisible(element, String.format("Element with XPath %s is not visible.", xpath));
        assertTrue(element.getText().contains(expectedText),  String.format("Text does not match for element with %s and %s ", expectedText, element.getText()));
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
            Assertions.fail("Setup failed: " + e.getMessage());
        }
    }
}
