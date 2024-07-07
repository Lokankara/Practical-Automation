package org.softserve.academy.registration;

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
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.Duration;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DisplayName("Registration Modal Tests")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class RegistrationModalTest {
    private WebDriver driver;
    private WebDriverWait wait;
    private JavascriptExecutor executor;

    private static final String BASE_URL = "http://speak-ukrainian.eastus2.cloudapp.azure.com/dev/";
    private static final String USER_ICON_CSS_SELECTOR = "svg[data-icon='user']";
    private static final String DROPDOWN_MENU_CLASS_NAME = "ant-dropdown-menu";
    private static final String REGISTER_MENU_ITEM_XPATH = "//li[@role='menuitem']//div[text()='Зареєструватися']";
    private static final String MODAL_CONTENT_CLASS_NAME = "ant-modal-content";
    private static final String CLOSE_BUTTON_CLASS_NAME = "ant-modal-close";
    private static final String MODAL_HEADER_CLASS_NAME = "registration-header";
    private static final String LAST_NAME_INPUT_ID = "lastName";
    private static final String FIRST_NAME_INPUT_ID = "firstName";
    private static final String PHONE_INPUT_ID = "phone";
    private static final String EMAIL_INPUT_ID = "email";
    private static final String PASSWORD_INPUT_ID = "password";
    private static final String CONFIRM_INPUT_ID = "confirm";
    private static final String REGISTER_BUTTON_CSS_SELECTOR = ".registration-button";
    private static final String SUCCESS_MESSAGE = "Ви успішно зареєструвалися! Вам на пошту відправлено лист з лінком для підтвердження реєстрації";

    @BeforeAll
    void setUpAll() {
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
    void setUpEach() {
        driver.get(BASE_URL);
    }

    @AfterEach
    void tearDownEach() {
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
        assertNotNull(dropdownMenu);
    }

    @Test
    @Order(2)
    @DisplayName("2. Test clicking on 'Register' menu item")
    void testClickRegisterMenuItem() {
        openModalWindow();
    }

    @Test
    @Order(3)
    @DisplayName("3. Test modal content visibility")
    void testModalContentVisibility() {
        openModalWindow();

        WebElement modalContent = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className(MODAL_CONTENT_CLASS_NAME)));
        assertTrue(modalContent.isDisplayed(), "Modal content should be visible");
    }

    @Test
    @Order(4)
    @DisplayName("4. Test modal close button presence and functionality")
    void testCloseButton() {
        openModalWindow();

        WebElement closeButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className(CLOSE_BUTTON_CLASS_NAME)));
        assertNotNull(closeButton);
        clickElementWithJS(closeButton);

        assertTrue(wait.until(ExpectedConditions.invisibilityOfElementLocated(By.className(MODAL_CONTENT_CLASS_NAME))), "Modal should be closed");
    }

    @Test
    @Order(5)
    @DisplayName("5. Test modal header text")
    void testModalHeader() {
        openModalWindow();

        WebElement header = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className(MODAL_HEADER_CLASS_NAME)));
        assertNotNull(header);
        assertEquals("Реєстрація", header.getText());
    }

    @Test
    @Order(6)
    @DisplayName("6. Test form fields presence and placeholders")
    void testFormFieldsPresence() {
        openModalWindow();

        WebElement lastNameInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(LAST_NAME_INPUT_ID)));
        WebElement firstNameInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(FIRST_NAME_INPUT_ID)));
        WebElement phoneInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(PHONE_INPUT_ID)));
        WebElement emailInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(EMAIL_INPUT_ID)));
        WebElement passwordInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(PASSWORD_INPUT_ID)));
        WebElement confirmInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(CONFIRM_INPUT_ID)));

        assertVisible(lastNameInput, "Last name input field");
        assertVisible(firstNameInput, "First name input field");
        assertVisible(phoneInput, "Phone input field");
        assertVisible(emailInput, "Email input field");
        assertVisible(passwordInput, "Password input field");
        assertVisible(confirmInput, "Confirm password input field");

        assertEquals("Введіть ваше прізвище", lastNameInput.getAttribute("placeholder"), String.format("Placeholder for last name input field should be '%s'", "Введіть ваше прізвище"));
        assertEquals("Введіть ваше ім`я", firstNameInput.getAttribute("placeholder"), String.format("Placeholder for first name input field should be '%s'", "Введіть ваше ім`я"));
        assertEquals("__________", phoneInput.getAttribute("placeholder"), String.format("Placeholder for phone input field should be '%s'", "__________"));
        assertEquals("Введіть ваш емейл", emailInput.getAttribute("placeholder"), String.format("Placeholder for email input field should be '%s'", "Введіть ваш емейл"));
        assertEquals("Введіть ваш пароль", passwordInput.getAttribute("placeholder"), String.format("Placeholder for password input field should be '%s'", "Введіть ваш пароль"));
        assertEquals("Підтвердіть ваш пароль", confirmInput.getAttribute("placeholder"), String.format("Placeholder for confirm password input field should be '%s'", "Підтвердіть ваш пароль"));
    }

    @Order(7)
    @ParameterizedTest(name = "7. Test filling registration form fields - email: {0}, password: {1}, fullName: {2} {3}, phone: {4}")
    @CsvFileSource(resources = "/sign.csv", numLinesToSkip = 1)
    void testFormFieldsParams(String email, String password, String lastName, String firstName, String phone) {
        openModalWindow();

        WebElement lastNameInput = fillAndAssertField(LAST_NAME_INPUT_ID, lastName);
        WebElement firstNameInput = fillAndAssertField(FIRST_NAME_INPUT_ID, firstName);
        WebElement phoneInput = fillAndAssertField(PHONE_INPUT_ID, phone);
        WebElement emailInput = fillAndAssertField(EMAIL_INPUT_ID, email);
        WebElement passwordInput = fillAndAssertField(PASSWORD_INPUT_ID, password);
        WebElement confirmInput = fillAndAssertField(CONFIRM_INPUT_ID, password);

        assertEquals(lastName, lastNameInput.getAttribute("value"), String.format("Last name input field value should be '%s'", lastName));
        assertEquals(firstName, firstNameInput.getAttribute("value"), String.format("First name input field value should be '%s'", firstName));
        assertEquals(phone, phoneInput.getAttribute("value"), String.format("Phone input field value should be '%s'", phone));
        assertEquals(email, emailInput.getAttribute("value"), String.format("Email input field value should be '%s'", email));
        assertEquals(password, passwordInput.getAttribute("value"), String.format("Password input field value should be '%s'", password));
        assertEquals(password, confirmInput.getAttribute("value"), String.format("Confirm password input field value should be '%s'", password));
    }

    @Order(8)
    @ParameterizedTest(name = "8. Test register button activation after filling form - email: {0}, password: {1}, fullName: {2} {3} phone: {4}")
    @CsvFileSource(resources = "/sign.csv", numLinesToSkip = 1)
    void testRegisterButton(String email, String password, String lastName, String firstName, String phone) {
        openModalWindow();

        WebElement registerButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(REGISTER_BUTTON_CSS_SELECTOR)));
        assertFalse(registerButton.getAttribute("class").contains("ant-btn-disabled"), "Register button should be disabled initially");

        fillAndAssertField(LAST_NAME_INPUT_ID, lastName);
        fillAndAssertField(FIRST_NAME_INPUT_ID, firstName);
        fillAndAssertField(PHONE_INPUT_ID, phone);
        fillAndAssertField(EMAIL_INPUT_ID, email);
        fillAndAssertField(PASSWORD_INPUT_ID, password);
        fillAndAssertField(CONFIRM_INPUT_ID, password);

        registerButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(REGISTER_BUTTON_CSS_SELECTOR)));
        assertFalse(registerButton.getAttribute("class").contains("ant-btn-disabled"), "Register button should be enabled after filling all fields");
    }

    @Order(9)
    @ParameterizedTest(name = "9. Test successful registration - email: {0}, password: {1}, fullName: {2} {3}, phone: {4}")
    @CsvFileSource(resources = "/sign.csv", numLinesToSkip = 1)
    void testSuccessfulRegistration(String email, String password, String lastName, String firstName, String phone) {
        openModalWindow();

        fillAndAssertField(LAST_NAME_INPUT_ID, lastName);
        fillAndAssertField(FIRST_NAME_INPUT_ID, firstName);
        fillAndAssertField(PHONE_INPUT_ID, phone);
        fillAndAssertField(EMAIL_INPUT_ID, System.currentTimeMillis() + email);
        fillAndAssertField(PASSWORD_INPUT_ID, password);
        fillAndAssertField(CONFIRM_INPUT_ID, password);

        WebElement registerButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(REGISTER_BUTTON_CSS_SELECTOR)));
        clickElementWithJS(registerButton);

        WebElement successMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("ant-message-success")));
        assertNotNull(successMessage, "Success message should be present");
        assertEquals(SUCCESS_MESSAGE, successMessage.getText(), String.format("Success message should be '%s'", SUCCESS_MESSAGE));
    }

    @Order(10)
    @ParameterizedTest(name = "10. Test Unsuccessful Registration after filling form with error: {5}")
    @CsvFileSource(resources = "/invalid-sign.csv", numLinesToSkip = 1)
    void testUnSuccessfulRegistration(String email, String password, String lastName, String firstName, String phone, String message) {
        openModalWindow();

        fillAndAssertField(LAST_NAME_INPUT_ID, lastName);
        fillAndAssertField(FIRST_NAME_INPUT_ID, firstName);
        fillAndAssertField(PHONE_INPUT_ID, phone);
        fillAndAssertField(EMAIL_INPUT_ID, email);
        fillAndAssertField(PASSWORD_INPUT_ID, password);
        fillAndAssertField(CONFIRM_INPUT_ID, password);

        WebElement errorMessage = driver.findElement(By.cssSelector(".ant-form-item-explain-error"));
        WebElement errorIcon = driver.findElement(By.cssSelector(".ant-form-item-feedback-icon.ant-form-item-feedback-icon-error"));
        WebElement button = driver.findElement(By.cssSelector(REGISTER_BUTTON_CSS_SELECTOR));

        assertVisible(errorMessage, "Error message");
        assertVisible(errorIcon, "Error icon");
        assertVisible(button, "Registration button");
        assertEquals("rgba(255, 77, 79, 1)", errorIcon.getCssValue("color"), "Error icon color does not match expected.");
        assertTrue(errorMessage.getText().contains(message), "Error message text does not match expected.");
        assertTrue(errorIcon.isDisplayed(), "Error icon is not displayed as expected");
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

        WebElement dropdownMenu = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className(DROPDOWN_MENU_CLASS_NAME)));
        assertNotNull(dropdownMenu);

        WebElement registerMenuItem = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(REGISTER_MENU_ITEM_XPATH)));
        scrollToElement(registerMenuItem);
        clickElementWithJS(registerMenuItem);
    }

    private static void assertVisible(WebElement element, String message) {
        assertNotNull(element, String.format("%s should be visible", message));
        assertTrue(element.isDisplayed(), String.format("%s should be displayed", message));
    }

    private void clickElementWithJS(WebElement element) {
        if (element != null && element.isDisplayed() && element.isEnabled()) {
            executor.executeScript("arguments[0].dispatchEvent(new MouseEvent('click', {bubbles: true, cancelable: true, view: window}));", element);
        } else {
            throw new IllegalArgumentException("Element is not clickable: " + element);
        }
    }

    private void scrollToElement(WebElement element) {
        executor.executeScript("arguments[0].scrollIntoView(true);", element);
    }

    @AfterAll
    void tearDownAll() {
        if (driver != null) {
            driver.quit();
        }
    }
}
