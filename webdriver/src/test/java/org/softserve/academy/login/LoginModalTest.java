package org.softserve.academy.login;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DisplayName("Login Modal Tests")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class LoginModalTest {
    private WebDriver driver;
    private WebDriverWait wait;
    private JavascriptExecutor executor;

    private static final String BASE_URL = "http://speak-ukrainian.eastus2.cloudapp.azure.com/dev/";
    private static final String USER_ICON_CSS_SELECTOR = "svg[data-icon='user']";
    private static final String DROPDOWN_MENU_CLASS_NAME = "ant-dropdown-menu";
    private static final String SUCCESS_MESSAGE_CLASS_NAME = ".ant-message-success";
    private static final String LOGIN_MENU_ITEM_XPATH = "//li[@role='menuitem']//div[text()='Увійти']";
    private static final String LOGIN_HEADER_CLASS_NAME = "login-header";
    private static final String EMAIL_INPUT_ID = "basic_email";
    private static final String PASSWORD_INPUT_ID = "basic_password";
    private static final String LOGIN_BUTTON_CSS_SELECTOR = ".login-button";
    private static final String EXPECTED_SUCCESS_MESSAGE = "Ви успішно залогувалися!";

    @BeforeAll
    public void setUpAll() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions chromeOptions = new ChromeOptions();
        if ("true".equals(System.getenv("CI"))) {
            chromeOptions.addArguments("--headless");
            chromeOptions.addArguments("--no-sandbox");
            chromeOptions.addArguments("--disable-dev-shm-usage");
        }
        driver = new ChromeDriver(chromeOptions);
        executor = (JavascriptExecutor) driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @BeforeEach
    public void setUpEach() {
        driver.get(BASE_URL);
    }

    @AfterEach
    public void tearDownEach() {
        executor.executeScript("window.localStorage.clear()");
        driver.manage().deleteAllCookies();
    }

    @Test
    @Order(1)
    @DisplayName("1. Test clicking on user icon")
    void testClickUserIcon() {
        WebElement userIcon = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(USER_ICON_CSS_SELECTOR)));
        scrollToElement(userIcon);
        clickElementWithJS(userIcon);

        WebElement dropdownMenu = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className(DROPDOWN_MENU_CLASS_NAME)));
        assertNotNull(dropdownMenu, "Dropdown menu should be present after clicking the user icon");
    }

    @Test
    @Order(2)
    @DisplayName("2. Test clicking on 'Login' menu item")
    void testClickLoginMenuItem() {
        openModalWindow();
    }

    @Test
    @Order(3)
    @DisplayName("3. Test login modal header text")
    void testLoginModalHeader() {
        openModalWindow();

        WebElement loginHeader = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className(LOGIN_HEADER_CLASS_NAME)));
        assertVisible(loginHeader, "Login modal header");
        assertEquals("Вхід", loginHeader.getText(), "Login modal header text should be 'Вхід'");
    }

    @Test
    @Order(4)
    @DisplayName("4. Test login form fields presence and placeholders")
    void testLoginFormFieldsPresence() {
        openModalWindow();

        WebElement emailInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(EMAIL_INPUT_ID)));
        WebElement passwordInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(PASSWORD_INPUT_ID)));
        assertVisible(emailInput, "Email input field");
        assertVisible(passwordInput, "Password input field should be present");
        assertEquals("Введіть ваш емейл", emailInput.getAttribute("placeholder"), "Email input field placeholder should be 'Введіть ваш емейл'");
        assertEquals("Введіть ваш пароль", passwordInput.getAttribute("placeholder"), "Password input field placeholder should be 'Введіть ваш пароль'");
    }

    @Order(5)
    @ParameterizedTest(name = "5. Test filling login form fields - email: {0}, password: {1}")
    @CsvFileSource(resources = "/sign.csv", numLinesToSkip = 1)
    void testLoginFormFields(String email, String password) {
        openModalWindow();

        WebElement emailInput = fillAndAssertField(EMAIL_INPUT_ID, email);
        WebElement passwordInput = fillAndAssertField(PASSWORD_INPUT_ID, password);
        assertEnable(emailInput, "Email input field");
        assertEnable(passwordInput, "Password input field");
        assertEquals(email, emailInput.getAttribute("value"), String.format("Email input field value should be '%s'", email));
        assertEquals(password, passwordInput.getAttribute("value"), String.format("Password input field value should be '%s'", password));
    }

    @Order(6)
    @ParameterizedTest(name = "6. Test login button activation after filling form - email: {0}, password: {1}")
    @CsvFileSource(resources = "/sign.csv", numLinesToSkip = 1)
    void testLoginButton(String email, String password) {
        openModalWindow();

        fillAndAssertField(EMAIL_INPUT_ID, email);
        fillAndAssertField(PASSWORD_INPUT_ID, password);
        WebElement loginButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(LOGIN_BUTTON_CSS_SELECTOR)));

        assertEnable(loginButton, "Login button");
        assertFalse(loginButton.getAttribute("class").contains("ant-btn-disabled"), "Login button should be enabled after filling all fields");
    }

    @Order(7)
    @ParameterizedTest(name = "7. Test successful login - email: {0}, password: {1}")
    @CsvFileSource(resources = "/sign.csv", numLinesToSkip = 1)
    void testSuccessfulLogin(String email, String password) {
        openModalWindow();

        fillAndAssertField(EMAIL_INPUT_ID, email);
        fillAndAssertField(PASSWORD_INPUT_ID, password);
        WebElement loginButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(LOGIN_BUTTON_CSS_SELECTOR)));
        clickElementWithJS(loginButton);
        WebElement successMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(SUCCESS_MESSAGE_CLASS_NAME)));
        assertVisible(successMessage, "Success message after logging in");
        assertEquals(EXPECTED_SUCCESS_MESSAGE, successMessage.getText(), String.format("Success message text should be '%s'", EXPECTED_SUCCESS_MESSAGE));
    }

    private WebElement fillAndAssertField(String fieldId, String value) {
        WebElement field = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(fieldId)));
        assertNotNull(field, "Field with ID '" + fieldId + "' should be present");
        field.sendKeys(value);
        return field;
    }

    private void openModalWindow() {
        WebElement userIcon = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(USER_ICON_CSS_SELECTOR)));
        scrollToElement(userIcon);
        clickElementWithJS(userIcon);

        WebElement loginMenuItem = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(LOGIN_MENU_ITEM_XPATH)));
        assertNotNull(loginMenuItem, "Login MenuItem should be present");
        assertTrue(loginMenuItem.isDisplayed(), "Login MenuItem should be visible after clicking the 'Login' menu item");
        scrollToElement(loginMenuItem);
        clickElementWithJS(loginMenuItem);

        WebElement loginHeader = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className(LOGIN_HEADER_CLASS_NAME)));
        assertNotNull(loginHeader, "Login modal should be visible after clicking the 'Login' menu item");
    }

    private void assertVisible(WebElement element, String message) {
        assertNotNull(element, String.format("%s should be present", message));
        assertTrue(element.isDisplayed(), String.format("%s should be visible", message));
    }

    private void assertEnable(WebElement element, String message) {
        assertVisible(element, message);
        assertTrue(element.isEnabled(), String.format("%s should be enabled for filling in the form", message));
    }

    private void clickElementWithJS(WebElement element) {
        if (element != null && element.isDisplayed() && element.isEnabled()) {
            executor.executeScript("arguments[0].dispatchEvent(new MouseEvent('click', {bubbles: true, cancelable: true, view: window}));", element);
        } else {
            throw new IllegalArgumentException("Element is not clickable: " + element);
        }
    }

    private void scrollToElement(WebElement element) {
        assertNotNull(element, "Element should be present");
        executor.executeScript("arguments[0].scrollIntoView(true);", element);
    }

    @AfterAll
    public void tearDownAll() {
        if (driver != null) {
            driver.quit();
        }
    }
}
