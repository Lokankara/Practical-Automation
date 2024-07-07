package org.softserve.academy.logining;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.EmptySource;
import org.junit.jupiter.params.provider.MethodSource;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.softserve.academy.provider.InvalidEmailProvider;
import org.softserve.academy.provider.ValidEmailProvider;
import org.softserve.academy.runner.BaseTest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DisplayName("Login Modal Tests")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class LoginModalTest extends BaseTest {
    private static final String USER_ICON_CSS_SELECTOR = "svg[data-icon='user']";
    private static final String DROPDOWN_MENU_CSS_SELECTOR = ".ant-dropdown-menu";
    private static final String LOGIN_HEADER_CSS_SELECTOR = ".login-header";
    private static final String EMAIL_INPUT_CSS_SELECTOR = "#basic_email";
    private static final String PASSWORD_INPUT_CSS_SELECTOR = "#basic_password";
    private static final String LOGIN_BUTTON_CSS_SELECTOR = ".login-button";
    private static final String MESSAGE_SUCCESS_SELECTOR = ".ant-message-success";
    private static final String ERROR_ICON_SELECTOR = ".ant-form-item-feedback-icon.ant-form-item-feedback-icon-error";
    private static final String DROPDOWN_MENU_SELECTOR = ".ant-dropdown-trigger.user-profile";
    private static final String PROFILE_MENU_SELECTOR = "li.ant-dropdown-menu-item-only-child[data-menu-id*='-profile']";
    private static final String NOTICE_ERROR_SELECTOR = ".ant-message-notice.ant-message-notice-error";
    private static final String expectedColorRed = "#ff4d4f";

    private Stream<Arguments> provideLoginArguments() {
        List<String> emails = readCsvFile("/emails.csv");
        List<String> passwords = readCsvFile("/valid_passwords.csv");
        return IntStream
                .range(0, Math.min(passwords.size(), emails.size()))
                .mapToObj(i -> Arguments.of(emails.get(i), passwords.get(i)))
                .toList().stream();
    }

    @Test
    @Order(1)
    @DisplayName("1. Test clicking on user icon")
    void testClickUserIcon() {
        WebElement userIcon = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(USER_ICON_CSS_SELECTOR)));
        scrollToElement(userIcon);
        clickElementWithJS(userIcon);

        WebElement dropdownMenu = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(DROPDOWN_MENU_CSS_SELECTOR)));
        assertVisible(dropdownMenu, "Dropdown menu");
        isTestSuccessful = true;
    }

    @Test
    @Order(2)
    @DisplayName("2. Test clicking on 'Login' menu item")
    void testClickLoginMenuItem() {
        openModalWindow();
        isTestSuccessful = true;
    }

    @Test
    @Order(3)
    @DisplayName("3. Test login modal header text")
    void testLoginModalHeader() {
        openModalWindow();

        assertLoginHeader();
        isTestSuccessful = true;
    }

    @Test
    @Order(4)
    @DisplayName("4. Test login form fields presence and placeholders")
    void testLoginFormFieldsPresence() {
        openModalWindow();

        assertEquals("Введіть ваш емейл", driver.findElement(By.cssSelector(EMAIL_INPUT_CSS_SELECTOR)).getAttribute("placeholder"));
        assertEquals("Введіть ваш пароль", driver.findElement(By.cssSelector(PASSWORD_INPUT_CSS_SELECTOR)).getAttribute("placeholder"));
        isTestSuccessful = true;
    }

    @Order(5)
    @ParameterizedTest(name = "Login with email: {0}, password: {1}")
    @CsvFileSource(resources = "/sign.csv", numLinesToSkip = 1)
    @DisplayName("5. Test filling login form fields")
    void testLoginFormFields(String email, String password) {
        openModalWindow();

        WebElement emailInput = fillAndAssertField(EMAIL_INPUT_CSS_SELECTOR, email);
        WebElement passwordInput = fillAndAssertField(PASSWORD_INPUT_CSS_SELECTOR, password);

        assertAttributeEquals(email, emailInput, "value");
        assertAttributeEquals(password, passwordInput, "value");
        isTestSuccessful = true;
    }

    @Test
    @Order(6)
    @DisplayName("6. Test login button activation after filling form")
    void testLoginButton() {
        openModalWindow();

        WebElement loginButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(LOGIN_BUTTON_CSS_SELECTOR)));

        assertVisible(loginButton, "Login button");
        assertTrue(loginButton.isEnabled(), "Login button should be enabled after filling all fields");
        assertFalse(loginButton.getAttribute("class").contains("ant-btn-disabled"), "Login button should be enabled after filling all fields");
        isTestSuccessful = true;
    }

    @Order(7)
    @DisplayName("7. Test successful login")
    @ParameterizedTest(name = "User: {3} {2} login with email: {0}, password: {1}")
    @CsvFileSource(resources = "/sign.csv", numLinesToSkip = 1)
    void testSuccessfulLogin(String email, String password, String lastName, String firstName, String phone) {
        openModalWindow();

        fillAndAssertField(EMAIL_INPUT_CSS_SELECTOR, email);
        fillAndAssertField(PASSWORD_INPUT_CSS_SELECTOR, password);

        WebElement loginButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(LOGIN_BUTTON_CSS_SELECTOR)));

        assertTrue(loginButton.isEnabled(), "Login button should be enabled after filling all fields");
        assertFalse(loginButton.getAttribute("class").contains("ant-btn-disabled"), "Login button should be enabled after filling all fields");

        clickElementWithJS(loginButton);

        WebElement successMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(MESSAGE_SUCCESS_SELECTOR)));
        WebElement dropdownMenu = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(DROPDOWN_MENU_SELECTOR)));

        assertVisible(successMessage, "Success message");
        assertVisible(dropdownMenu, "After clicking the user icon Dropdown menu");

        clickElementWithJS(dropdownMenu);

        WebElement profile = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(PROFILE_MENU_SELECTOR)));

        assertVisible(profile, "After clicking the user icon Profile menu");

        profile.click();

        WebElement emailElement = driver.findElement(By.cssSelector(".user-email-data"));
        WebElement phoneElement = driver.findElement(By.cssSelector(".user-phone-data"));
        WebElement fullNameElement = driver.findElement(By.cssSelector(".user-name"));

        assertTextEquals(email, emailElement, "After clicking the user profile Email");
        assertVisible(phoneElement, "Phone after clicking the user profile");
        assertTrue(phoneElement.getText().contains(phone), "Phone does not match the " + lastName);
        assertVisible(fullNameElement, "Fullname after clicking the user profile");
        assertTrue(fullNameElement.getText().contains(lastName), "Fullname does not match the " + lastName);
        assertTrue(fullNameElement.getText().contains(firstName), "Fullname does not match the " + firstName);
        isTestSuccessful = true;
    }

    @Order(7)
    @EmptySource
    @ParameterizedTest
    @DisplayName("7. Test unsuccessful login with invalid email")
    void testUnSuccessfulLogins(String input) {
        openModalWindow();

        fillAndAssertField(EMAIL_INPUT_CSS_SELECTOR, input);
        fillAndAssertField(PASSWORD_INPUT_CSS_SELECTOR, "Password123!");

        WebElement loginButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(LOGIN_BUTTON_CSS_SELECTOR)));
        clickElementWithJS(loginButton);

        assertErrorIcon();
        isTestSuccessful = true;
    }

    @Order(8)
    @EmptySource
    @ParameterizedTest
    @DisplayName("8. Test login with empty fields")
    void testLoginWithEmptyFields(String input) {
        openModalWindow();
        fillAndAssertField(EMAIL_INPUT_CSS_SELECTOR, input);
        fillAndAssertField(PASSWORD_INPUT_CSS_SELECTOR, input);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(LOGIN_HEADER_CSS_SELECTOR)));
        WebElement loginButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(LOGIN_BUTTON_CSS_SELECTOR)));
        clickElementWithJS(loginButton);
        List<WebElement> errorIcons = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector(ERROR_ICON_SELECTOR)));

        assertEquals(2, errorIcons.size());
        errorIcons.forEach(this::assertErrorIcon);
        assertLoginHeader();
        isTestSuccessful = true;
    }

    @Order(9)
    @DisplayName("9. Test login with incorrect email format")
    @ParameterizedTest(name = "login with email: {0}")
    @CsvFileSource(resources = "/invalid_emails.csv")
    void testLoginWithIncorrectEmailFormat(String email) {
        openModalWindow();

        fillAndAssertField(EMAIL_INPUT_CSS_SELECTOR, email);
        fillAndAssertField(PASSWORD_INPUT_CSS_SELECTOR, "Password123!");

        WebElement loginButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(LOGIN_BUTTON_CSS_SELECTOR)));
        assertVisible(loginButton, "Registration button");

        clickElementWithJS(loginButton);
        WebElement emailIcon = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".ant-form-item-feedback-icon")));

        assertLoginHeader();
        assertVisible(emailIcon, "Error icon");
        assertTrue(emailIcon.getAttribute("class").contains("ant-form-item-feedback-icon-error"), "Email icon should be with class icon-error " + email);
        assertErrorIcon(emailIcon);
        assertEquals("rgba(255, 77, 79, 1)", emailIcon.getCssValue("color"), "Error icon color does not match expected.");
        isTestSuccessful = true;
    }

    @Order(10)
    @DisplayName("10. Test login with incorrect password")
    @ParameterizedTest(name = "Login with incorrect password: {0}")
    @CsvFileSource(resources = "/invalid_passwords.csv")
    void testLoginWithIncorrectPassword(String password) {
        openModalWindow();
        fillAndAssertField(EMAIL_INPUT_CSS_SELECTOR, password);
        fillAndAssertField(PASSWORD_INPUT_CSS_SELECTOR, "1");

        WebElement loginButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(LOGIN_BUTTON_CSS_SELECTOR)));
        clickElementWithJS(loginButton);

        assertLoginHeader();
        assertErrorIcon();
        isTestSuccessful = true;
    }

    @Order(11)
    @ParameterizedTest
    @ArgumentsSource(ValidEmailProvider.class)
    @DisplayName("11. Test login with short password")
    void testLoginWithShortPassword(String email) {
        openModalWindow();
        fillAndAssertField(EMAIL_INPUT_CSS_SELECTOR, email);
        fillAndAssertField(PASSWORD_INPUT_CSS_SELECTOR, "");
        WebElement button = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(LOGIN_BUTTON_CSS_SELECTOR)));
        clickElementWithJS(button);

        assertLoginHeader();
        WebElement passwordInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".ant-input-affix-wrapper.ant-input-password")));
        WebElement errorIcon = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".ant-form-item-feedback-icon-error")));

        assertVisible(passwordInput, "Password Input");
        assertTrue(passwordInput.getAttribute("class").contains("ant-input-affix-wrapper-status-error"), "Login button should be with class icon-error");
        assertVisible(errorIcon, "Error icon");
        assertErrorIcon(errorIcon);
        assertEquals("rgba(255, 77, 79, 1)", errorIcon.getCssValue("color"), "Error icon color does not match expected.");
        assertTrue(passwordInput.isDisplayed(), "Error icon is not displayed as expected");
        isTestSuccessful = true;
    }

    @Order(12)
    @DisplayName("12. Test login with Error unAuthorized user")
    @ParameterizedTest(name = "login with unAuthorized user : email {0}")
    @MethodSource("provideLoginArguments")
    void testLoginWithUnAuthorizedUser(String email, String password) {
        openModalWindow();
        fillAndAssertField(EMAIL_INPUT_CSS_SELECTOR, email);
        fillAndAssertField(PASSWORD_INPUT_CSS_SELECTOR, password);
        WebElement loginButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(LOGIN_BUTTON_CSS_SELECTOR)));
        assertVisible(loginButton, "Login button");
        clickElementWithJS(loginButton);

        assertLoginHeader();
        assertErrorAuthMessage(email);
        isTestSuccessful = true;
    }

    @Order(13)
    @DisplayName("13. Test login with incorrect email")
    @ParameterizedTest(name = "Login with incorrect email: {0}")
    @ArgumentsSource(InvalidEmailProvider.class)
    void testLoginWithIncorrectEmail(String email) {
        openModalWindow();

        fillAndAssertField(EMAIL_INPUT_CSS_SELECTOR, email);
        fillAndAssertField(PASSWORD_INPUT_CSS_SELECTOR, "Password123!");

        WebElement loginButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(LOGIN_BUTTON_CSS_SELECTOR)));
        clickElementWithJS(loginButton);
        WebElement emailIcon = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".ant-form-item-feedback-icon")));

        assertLoginHeader();
        assertVisible(emailIcon, "Error icon");
        assertTrue(emailIcon.getAttribute("class").contains("ant-form-item-feedback-icon-error"), "Login button should be with class icon-error " + email);
        assertErrorIcon(emailIcon);
        assertEquals("rgba(255, 77, 79, 1)", emailIcon.getCssValue("color"), "Error icon color does not match expected with email: " + email);
        isTestSuccessful = true;
    }

    private void assertAttributeEquals(String expected, WebElement element, String attribute) {
        assertVisible(element, "Element");
        assertEquals(expected, element.getAttribute(attribute), "Attribute should be with value " + expected);
    }

    private void assertErrorIcon() {
        assertErrorIcon(wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(ERROR_ICON_SELECTOR))));
    }

    private void assertErrorIcon(WebElement errorIcon) {
        assertTrue(errorIcon.isDisplayed(), "Error icon is not displayed");
        assertEquals(expectedColorRed, getBorderColor(errorIcon), "Error icon color does not match expected.");
        assertNotNull(errorIcon, "Error icon is not present");
    }

    private void assertErrorAuthMessage(String email) {
        WebElement errorMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(NOTICE_ERROR_SELECTOR)));
        assertVisible(errorMessage, "error Message");
        assertTextEquals("Введено невірний пароль або email", errorMessage, "UnSuccess message should be displayed with email:" + email);
    }

    private void assertLoginHeader() {
        WebElement header = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(LOGIN_HEADER_CSS_SELECTOR)));
        assertTextEquals("Вхід", header, "Header");
    }

    private void openModalWindow() {
        WebElement userIcon = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(USER_ICON_CSS_SELECTOR)));
        scrollToElement(userIcon);
        clickElementWithJS(userIcon);
        WebElement dropdownMenu = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(DROPDOWN_MENU_CSS_SELECTOR)));
        assertNotNull(dropdownMenu);

        WebElement menuItem = findElementContainingText();
        scrollToElement(menuItem);
        clickElementWithJS(menuItem);
    }

    private WebElement findElementContainingText() {
        String script = "return Array.from(document.querySelectorAll(\"" + "li[role='menuitem'] div" + "\")).find(element => element.textContent.includes(\"" + "Увійти" + "\"));";
        return (WebElement) executor.executeScript(script);
    }

    private WebElement fillAndAssertField(String fieldCssSelector, String value) {
        WebElement field = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(fieldCssSelector)));
        assertNotNull(field, String.format("Field with CSS Selector '%s' should be present", fieldCssSelector));
        field.sendKeys(value);
        return field;
    }

    private String getBorderColor(WebElement element) {
        String rgbColor = (String) executor.executeScript("return getComputedStyle(arguments[0]).borderColor", element);
        String[] rgb = rgbColor.replace("rgb(", "").replace(")", "").split(",");
        return String.format("#%02x%02x%02x", Integer.parseInt(rgb[0].trim()), Integer.parseInt(rgb[1].trim()), Integer.parseInt(rgb[2].trim()));
    }

    private List<String> readCsvFile(String resourcePath) {
        InputStream inputStream = getClass().getResourceAsStream(resourcePath);
        if (inputStream == null) {
            throw new RuntimeException("Resource not found: " + resourcePath);
        }
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            return reader.lines().toList();
        } catch (IOException e) {
            throw new RuntimeException("Error reading CSV file", e);
        }
    }
}
