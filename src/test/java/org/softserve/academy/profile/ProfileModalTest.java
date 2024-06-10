package org.softserve.academy.profile;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.softserve.academy.runner.ProfileBaseTest;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ProfileModalTest extends ProfileBaseTest {
    private static final String[] inputUserXPaths = {"//input[@id='edit_lastName']", "//input[@id='edit_firstName']", "//input[@id='edit_phone']", "//input[@id='edit_email']"};
    private static final List<String> inputPasswordXPaths = Arrays.asList("//input[@id='edit_currentPassword']", "//input[@id='edit_password']", "//input[@id='edit_confirmPassword']");
    private static final List<String> labelUserXPaths = Arrays.asList("//div[@class='user-phone-data']", "//div[@class='user-email-data']", "//div[@class='user-name']");
    private static final String EDIT_INPUTS = "//*[contains(@class, 'ant-input-affix-wrapper') and contains(@class, 'ant-input-password') and contains(@class, 'user-edit-box')]";
    private static final String SUCCESS_MESSAGE = "//span[contains(text(),'Профіль змінено успішно')]";
    private static final String MESSAGE = "//span[contains(text(),'Профіль змінено успішно')]";
    private static final String BOX_INPUT = "//span[contains(@class, 'user-edit-box')]//input";
    private static final String SAVE_CHANGES = "//span[contains(text(),'Зберегти зміни')]";
    private static final String EDIT_BUTTON = "//div[@class='edit-button']";

    @Test
    @Order(1)
    @DisplayName("1. Test check profile header")
    void testProfileHeader() {
        loginUser(EMAIL, PASSWORD);
        openProfile();

        assertTextEquals("Мій профіль", getByXpath("//div[@class='content-title']"), "profile Header");
        isTestSuccessful = true;
    }

    @Order(2)
    @DisplayName("2. Test check profile info")
    @ParameterizedTest(name = "User: {3} {2} login with email: {0}, password: {1}")
    @CsvFileSource(resources = "/sign.csv", numLinesToSkip = 1)
    void testProfileInfo(String email, String password, String lastName, String firstName, String phone) {
        loginUser(email, password);
        openProfile();
        List<String> expectedTexts = Arrays.asList(phone, email, firstName + " " + lastName);

        IntStream.range(0, labelUserXPaths.size()).forEach(i ->
                assertContains(getByXpath(labelUserXPaths.get(i)), labelUserXPaths.get(i), expectedTexts.get(i)));
        isTestSuccessful = true;
    }

    @Test
    @Order(3)
    @DisplayName("3. Test edit profile button")
    void testEditProfileButton() {
        loginUser(EMAIL, PASSWORD);
        openProfile();
        WebElement editButton = getByXpath(EDIT_BUTTON);

        assertEnable(editButton, "Edit user profile");
        assertTextEquals("Редагувати профіль", editButton, "Edit user profile text");
        isTestSuccessful = true;
    }

    @Test
    @Order(4)
    @DisplayName("4. Test edit profile button")
    void testEditProfileClickButtonWithoutSendKeys() {
        loginUser(EMAIL, PASSWORD);
        openProfile();
        openEditProfile();
        clickElementWithJS(getByXpath(SAVE_CHANGES));

        WebElement message = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(SUCCESS_MESSAGE)));
        assertTextEquals("Профіль змінено успішно", message, "Success message");
        isTestSuccessful = true;
    }

    @Test
    @Order(4)
    @DisplayName("4. Test edit profile button")
    void testEditProfileKeyDownWithoutSendKeys() {
        loginUser(EMAIL, PASSWORD);
        openProfile();
        openEditProfile();

        List<WebElement> inputs = driver.findElements(By.xpath(BOX_INPUT));
        assertEquals(2, inputs.size());

        for (WebElement input : inputs) {
            input.sendKeys(Keys.ENTER);
            assertEquals("Профіль змінено успішно", wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(MESSAGE))).getText());
            openEditProfile();
        }
        driver.switchTo().activeElement().sendKeys(Keys.ESCAPE);
        isTestSuccessful = true;
    }

    @Order(4)
    @ParameterizedTest(name = "User: {3} {2} login with email: {0}, password: {1}")
    @CsvFileSource(resources = "/sign.csv", numLinesToSkip = 1)
    @DisplayName("4. Test edit profile modal")
    void testEditProfileModalValue(String email, String password, String lastName, String firstName, String phone) {
        loginUser(email, password);
        openProfile();
        openEditProfile();

        boolean[] shouldBeEnabled = {true, true, true, false};
        String[] expectedValues = {lastName, firstName, phone, email};

        IntStream.range(0, inputUserXPaths.length).forEach(i -> {
            WebElement inputElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(inputUserXPaths[i])));
            assertVisible(inputElement, String.format("The input element for %s should be visible.", inputUserXPaths[i]));
            assertEquals(inputElement.isEnabled(), shouldBeEnabled[i], String.format("The input element for %s should %s", inputUserXPaths[i], shouldBeEnabled[i] ? "be enabled." : "be disabled."));
            assertTrue(inputElement.getAttribute("value").contains(expectedValues[i]), String.format("The value of the input element for %s should contain the expected text.", inputUserXPaths[i]));
        });
        isTestSuccessful = true;
    }

    @Order(5)
    @ParameterizedTest(name = "User: {3} {2} login with email: {0}, password: {1}")
    @CsvFileSource(resources = "/sign.csv", numLinesToSkip = 1)
    @DisplayName("5. Test edit profile modal Password inputs")
    void testEditProfileModalPassword(String email, String password, String lastName, String firstName) {
        loginUser(email, password);
        openProfile();
        openEditProfile();
        clickCheckBox();

        driver.findElements(By.xpath(EDIT_INPUTS)).forEach(element ->
                assertTrue(element.isDisplayed(), "The element is not displayed."));

        inputPasswordXPaths.forEach(inputXPath ->
                assertEnable(wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(inputXPath))), "Element with " + inputXPath));

        clickCheckBox();

        assertTrue(driver.findElements(By.xpath(EDIT_INPUTS)).isEmpty(), "The list of elements is empty.");
        isTestSuccessful = true;
    }

    private static WebElement getByXpath(String xpath) {
        return driver.findElement(By.xpath(xpath));
    }

    private void assertContains(WebElement element, String xpath, String expectedText) {
        assertVisible(element, String.format("Element with XPath %s is not visible.", xpath));
        assertTrue(element.getText().contains(expectedText), "Text does not match for element with XPath " + xpath);
    }
}
