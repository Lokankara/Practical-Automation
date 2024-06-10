package org.softserve.academy.logining;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.EmptySource;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.softserve.academy.provider.InvalidEmailProvider;
import org.softserve.academy.provider.LoginProvider;
import org.softserve.academy.provider.ValidEmailProvider;
import org.softserve.academy.runner.BaseTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DisplayName("Login Modal Tests")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class LoginModalTest extends BaseTest {

    private static final String EMAIL_ICON_XPATH = "//*[contains(@class, 'anticon') and contains(@class, 'anticon-mail')]/following-sibling::*[contains(@class, 'ant-form-item-feedback-icon') and contains(@class, 'ant-form-item-feedback-icon-error')]";
    private static final String NOTICE_ERROR_XPATH = "//*[contains(concat(' ', normalize-space(@class), ' '), ' ant-message-notice ') and contains(concat(' ', normalize-space(@class), ' '), ' ant-message-notice-error ')]";
    private static final String ERROR_ICON_XPATH = "//*[contains(@class, 'ant-form-item-feedback-icon')][contains(@class, 'ant-form-item-feedback-icon-error')]";
    private static final String PROFILE_MENU_XPATH = "//li[contains(@class, 'ant-dropdown-menu-item-only-child') and contains(@data-menu-id, '-profile')]";
    private static final String DROPDOWN_MENU_PROFILE_XPATH = "//*[@class='ant-dropdown-trigger user-profile']";
    private static final String MESSAGE_SUCCESS_XPATH = "//div[contains(@class, 'ant-message-notice-content')]";
    private static final String LOGIN_HEADER_XPATH = "//*[@class='login-header']";
    private static final String expectedColorRed = "#ff4d4f";

    @Test
    @Order(1)
    @DisplayName("1. Test clicking on user icon")
    void testClickUserIcon() {
        WebElement userIcon = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(USER_ICON_XPATH)));
        scrollToElement(userIcon);
        clickElementWithJS(userIcon);
        WebElement dropdownMenu = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(DROPDOWN_MENU_XPATH)));

        assertVisible(dropdownMenu, "Dropdown menu");
        isTestSuccessful = true;
    }

    @Test
    @Order(2)
    @DisplayName("2. Test clicking on 'Login' menu item")
    void testClickLoginMenuItem() {
        String expectedUrl = "http://speak-ukrainian.eastus2.cloudapp.azure.com/dev/";
        openModalWindow();
        wait.until(ExpectedConditions.urlToBe(expectedUrl));

        assertEquals(expectedUrl, driver.getCurrentUrl(), "URL should be the clubs page URL");
        isTestSuccessful = true;
    }

    @Test
    @Order(3)
    @DisplayName("3. Test login modal header text")
    void testLoginModalHeader() {
        openModalWindow();

        WebElement header = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(LOGIN_HEADER_XPATH)));

        assertTextEquals("Вхід", header, "Login header");
        isTestSuccessful = true;
    }

    @Test
    @Order(4)
    @DisplayName("4. Test login form fields presence and placeholders")
    void testLoginFormFieldsPresence() {
        openModalWindow();

        assertEquals("Введіть ваш емейл", driver.findElement(By.xpath(EMAIL_INPUT_XPATH)).getAttribute("placeholder"));
        assertEquals("Введіть ваш пароль", driver.findElement(By.xpath(PASSWORD_INPUT_XPATH)).getAttribute("placeholder"));
        isTestSuccessful = true;
    }

    @Test
    @Order(5)
    @DisplayName("5. Test filling login form fields")
    void testLoginFormFields() {
        openModalWindow();
        WebElement emailInput = fillAndAssertField(EMAIL_INPUT_XPATH, EMAIL);
        WebElement passwordInput = fillAndAssertField(PASSWORD_INPUT_XPATH, PASSWORD);

        assertAttributeEquals(EMAIL, emailInput, "value");
        assertAttributeEquals(PASSWORD, passwordInput, "value");
        isTestSuccessful = true;
    }

    @Test
    @Order(6)
    @DisplayName("6. Test login button activation after filling form")
    void testLoginButton() {
        openModalWindow();

        assertEnable(getLoginButton(), "login Button");
        isTestSuccessful = true;
    }

    @Order(7)
    @DisplayName("7. Test successful login")
    @ParameterizedTest(name = "Test successful login: {3} {2} user with email: {0}, password: {1}")
    @CsvFileSource(resources = "/sign.csv", numLinesToSkip = 1)
    void testSuccessfulLogin(String email, String password, String lastName, String firstName, String phone) {
        openModalWindow();

        fillAndAssertField(EMAIL_INPUT_XPATH, email);
        fillAndAssertField(PASSWORD_INPUT_XPATH, password);

        clickElementWithJS(getLoginButton());

        WebElement successMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(MESSAGE_SUCCESS_XPATH)));
        WebElement dropdownMenu = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(DROPDOWN_MENU_PROFILE_XPATH)));

        assertVisible(successMessage, "Success message");
        assertVisible(dropdownMenu, "After clicking the user icon Dropdown menu");

        clickElementWithJS(dropdownMenu);
        WebElement profile = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(PROFILE_MENU_XPATH)));

        assertVisible(profile, "After clicking the user icon Profile menu");

        profile.click();

        WebElement emailElement = driver.findElement(By.xpath("//*[@class='user-email-data']"));
        WebElement phoneElement = driver.findElement(By.xpath("//*[@class='user-phone-data']"));
        WebElement fullNameElement = driver.findElement(By.xpath("//*[@class='user-name']"));

        assertVisible(phoneElement, "Phone after clicking the user profile");
        assertVisible(fullNameElement, "Fullname after clicking the user profile");
        assertTextEquals(email, emailElement, "After clicking the user profile Email");
        assertTrue(phoneElement.getText().contains(phone), "Phone does not match the " + lastName);
        assertTrue(fullNameElement.getText().contains(lastName), "Fullname does not match the " + lastName);
        assertTrue(fullNameElement.getText().contains(firstName), "Fullname does not match the " + firstName);
        isTestSuccessful = true;
    }

    @Order(7)
    @EmptySource
    @ParameterizedTest
    @DisplayName("7. Test unsuccessful login with Empty email")
    void testUnSuccessfulLogins(String input) {
        openModalWindow();

        fillAndAssertField(EMAIL_INPUT_XPATH, input);
        fillAndAssertField(PASSWORD_INPUT_XPATH, "Password123!");

        clickElementWithJS(getLoginButton());
        WebElement emailIcon = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ERROR_ICON_XPATH)));

        assertErrorIcon(emailIcon);
        isTestSuccessful = true;
    }

    @Order(7)
    @EmptySource
    @ParameterizedTest
    @DisplayName("7. Test unsuccessful login with Empty password")
    void testUnSuccessfulLoginsWithInvalidPassword(String input) {
        openModalWindow();

        fillAndAssertField(EMAIL_INPUT_XPATH, "email@gmail.ua");
        fillAndAssertField(PASSWORD_INPUT_XPATH, input);

        clickElementWithJS(getLoginButton());
        WebElement emailIcon = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ERROR_ICON_XPATH)));

        assertErrorIcon(emailIcon);
        isTestSuccessful = true;
    }

    @Order(7)
    @EmptySource
    @ParameterizedTest
    @DisplayName("7. Test login with empty fields")
    void testLoginWithEmptyFields(String input) {
        final int expected = 2;
        openModalWindow();
        fillAndAssertField(EMAIL_INPUT_XPATH, input);
        fillAndAssertField(PASSWORD_INPUT_XPATH, input);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(LOGIN_HEADER_XPATH)));
        clickElementWithJS(getLoginButton());
        List<WebElement> errorIcons = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath(ERROR_ICON_XPATH)));

        assertEquals(expected, errorIcons.size(), "Error: The size of errorIcons is not equal to " + expected);
        errorIcons.forEach(this::assertErrorIcon);
        assertLoginHeader();
        isTestSuccessful = true;
    }

    @Test
    @Order(7)
    @DisplayName("7. Test login without Filling Field")
    void testLoginWithOutFillingField() {
        final int expected = 2;
        openModalWindow();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(LOGIN_HEADER_XPATH)));
        clickElementWithJS(getLoginButton());
        List<WebElement> errorIcons = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath(ERROR_ICON_XPATH)));

        assertEquals(expected, errorIcons.size(), "Error: The size of errorIcons is not equal to " + expected);
        errorIcons.forEach(this::assertErrorIcon);
        assertLoginHeader();
        isTestSuccessful = true;
    }

    @Order(8)
    @DisplayName("8. Test login with various scenarios with email: {0}, isErrorMessageExpected: {2}, assertionMessage{3}")
    @ParameterizedTest
    @CsvFileSource(resources = "/login-test-data.csv", numLinesToSkip = 1)
    void testLoginScenarios(String email, String password, boolean isErrorMessageExpected, String assertionMessage) {
        openModalWindow();

        fillAndAssertField(EMAIL_INPUT_XPATH, email);
        fillAndAssertField(PASSWORD_INPUT_XPATH, "");

        clickElementWithJS(getLoginButton());
        WebElement emailIcon = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ERROR_ICON_XPATH)));

//        WebElement errorMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(@class, 'ant-message-error')]")));
//        assertEquals(isErrorMessageExpected, errorMessage.isDisplayed(), assertionMessage);

        assertLoginHeader();
        checkErrorIconClass(emailIcon);
        isTestSuccessful = true;
    }

    @Order(9)
    @DisplayName("9. Test login with incorrect email")
    @ParameterizedTest(name = "Login with incorrect email: {0}")
    @ArgumentsSource(InvalidEmailProvider.class)
    void testLoginWithIncorrectEmail(String email) {
        openModalWindow();

        fillAndAssertField(EMAIL_INPUT_XPATH, email);
        fillAndAssertField(PASSWORD_INPUT_XPATH, "Password123!");

        WebElement loginButton = getLoginButton();
        clickElementWithJS(loginButton);
        WebElement emailIcon = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(EMAIL_ICON_XPATH)));

        assertLoginHeader();
        checkErrorIconClass(emailIcon);
        isTestSuccessful = true;
    }

    @Order(10)
    @DisplayName("10. Test login with Error unAuthorized user")
    @ParameterizedTest(name = "Test login with unAuthorized user : email {0}")
    @ArgumentsSource(LoginProvider.class)
    void testLoginWithUnAuthorizedUser(String email, String password) {
        openModalWindow();
        fillAndAssertField(EMAIL_INPUT_XPATH, email);
        fillAndAssertField(PASSWORD_INPUT_XPATH, password);
        WebElement loginButton = getLoginButton();
        assertVisible(loginButton, "Login button");
        clickElementWithJS(loginButton);

        assertLoginHeader();
        assertErrorAuthMessage(email);
        isTestSuccessful = true;
    }

    @Order(11)
    @DisplayName("11. Test login Restore with invalid email")
    @ParameterizedTest(name = "Login Restore with invalid email: {0}")
    @ArgumentsSource(InvalidEmailProvider.class)
    void testLoginRestoreWithInvalidEmail(String email) {
        openModalWindow();
        clickRestoreLink();
        fillAndAssertRestoreField(email);

        WebElement emailIcon = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(EMAIL_ICON_XPATH)));

        checkErrorIconClass(emailIcon);
        assertLoginHeader();
        isTestSuccessful = true;
    }

    @Order(12)
    @DisplayName("12. Test login Restore for unAuthorized user")
    @ParameterizedTest(name = "Login Restore for unAuthorized user: {0}")
    @ArgumentsSource(ValidEmailProvider.class)
    void testLoginRestoreWithUnAuthorizedEmail(String email) {
        final String expected = "Користувача з вказаним емейлом не знайдено";
        openModalWindow();
        clickRestoreLink();
        fillAndAssertRestoreField(email);

        WebElement restoreButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[contains(text(),'Відновити')]")));
        assertEnable(restoreButton, "Restore button");
        restoreButton.click();
        WebElement errorMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(@class, 'ant-message-notice') and contains(@class, 'ant-message-notice-error')]")));

        assertTextEquals(expected, errorMessage, String.format("Error message text should be %s %s", expected, email));
        assertLoginHeader();
        isTestSuccessful = true;
    }

    private void clickRestoreLink() {
        WebElement restoreLink = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@class='restore-password-button']")));
        assertVisible(restoreLink, "Restore link");
        assertEnable(restoreLink, "Restore link");
        clickElementWithJS(restoreLink);
    }

    private static void fillAndAssertRestoreField(String email) {
        WebElement restoreInput = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='edit_email']")));
        assertVisible(restoreInput, "Restore Input");
        assertTrue(restoreInput.isEnabled(), "Restore Input is not enabled");
        restoreInput.sendKeys(email);
    }

    private void assertLoginHeader() {
        WebElement header = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(LOGIN_HEADER_XPATH)));
        assertTextEquals("Вхід", header, "Header");
    }

    private void assertErrorIcon(WebElement errorIcon) {
        assertTrue(errorIcon.isDisplayed(), "Error icon is not displayed");
        assertEquals(expectedColorRed, getBorderColor(errorIcon), "Error icon color does not match expected.");
        assertNotNull(errorIcon, "Error icon is not present");
    }

    private void assertErrorAuthMessage(String email) {
        WebElement errorMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(NOTICE_ERROR_XPATH)));
        assertVisible(errorMessage, "error Message");
        assertTextEquals("Введено невірний пароль або email", errorMessage, "UnSuccess message should be displayed with email:" + email);
    }

    private void checkErrorIconClass(WebElement errorIcon) {
        assertVisible(errorIcon, "Error icon");
        assertTrue(errorIcon.getAttribute("class").contains("ant-form-item-feedback-icon-error"), "Email icon should be with class icon-error");
        assertErrorIcon(errorIcon);
        assertEquals("rgba(255, 77, 79, 1)", errorIcon.getCssValue("color"), "Error icon color does not match expected.");
    }

    private String getBorderColor(WebElement element) {
        String rgbColor = (String) executor.executeScript("return getComputedStyle(arguments[0]).borderColor", element);
        String[] rgb = rgbColor.replace("rgb(", "").replace(")", "").split(",");
        return String.format("#%02x%02x%02x", Integer.parseInt(rgb[0].trim()), Integer.parseInt(rgb[1].trim()), Integer.parseInt(rgb[2].trim()));
    }
}
