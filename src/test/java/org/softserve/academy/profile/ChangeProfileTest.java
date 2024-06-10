package org.softserve.academy.profile;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.softserve.academy.provider.InfoUserProvider;
import org.softserve.academy.provider.PasswordProvider;
import org.softserve.academy.runner.ProfileBaseTest;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ChangeProfileTest extends ProfileBaseTest {
    private static final List<String> inputXPaths = Arrays.asList("//input[@id='edit_currentPassword']", "//input[@id='edit_password']", "//input[@id='edit_confirmPassword']");
    private static final String SUCCESS_MESSAGE = "//span[contains(text(),'Профіль змінено успішно')]";
    private static final String ERROR_MESSAGE = "//span[contains(text(),'Введено невірний пароль')]";
    private static final String SAVE_CHANGE = "//span[contains(text(),'Зберегти зміни')]";

    @ParameterizedTest
    @CsvFileSource(resources = "/passwords.csv", numLinesToSkip = 1)
    void testChangePasswordSuccess(String current, String newPass, String confirm) {
        loginUser(EMAIL, current);
        openProfile();
        openEditProfile();
        clickCheckBox();

        fillPasswordInputs(current, newPass, confirm);
        saveChanges();

        WebElement successMessage = getWebElement(By.xpath(SUCCESS_MESSAGE));
        assertEquals("Профіль змінено успішно", successMessage.getText());
        isTestSuccessful = true;
    }

    @Test
    void testChangeWrongCurrentPassword() {
        loginUser(EMAIL, PASSWORD);
        openProfile();
        openEditProfile();
        clickCheckBox();
        fillPasswordInputs("TestPassword123!", "NewPassword123!", "NewPassword123!");
        saveChanges();

        assertEquals("Введено невірний пароль", getWebElement(By.xpath(ERROR_MESSAGE)).getText());
        isTestSuccessful = true;
    }

    @ParameterizedTest(name = "Test change Current Password: {0} newPassword: {1}, confirmPassword: {2}")
    @ArgumentsSource(InfoUserProvider.class)
    void testUserInfoChange(String lastName, String firstName, String phone, List<String> xPaths, List<String> expectedMessages) {
        openChangePassword();
        //TODO fillUserInputs(lastName, firstName, phone);
        saveChanges();
//        IntStream.range(0, xPaths.size()).forEach(i -> assertEquals(expectedMessages.get(i), getWebElement(By.xpath(xPaths.get(i))).getText()));
    }

    @ParameterizedTest(name = "Test change Current Password: {0} newPassword: {1}, confirmPassword: {2}")
    @ArgumentsSource(PasswordProvider.class)
    void testPasswordChange(String currentPassword, String newPassword, String confirmPassword, List<String> xPaths, List<String> expectedMessages) {
        openChangePassword();

        fillPasswordInputs(currentPassword, newPassword, confirmPassword);
        saveChanges();

        IntStream.range(0, xPaths.size()).forEach(i -> assertEquals(expectedMessages.get(i), getWebElement(By.xpath(xPaths.get(i))).getText()));
    }

    private void fillPasswordInputs(String currentPassword, String newPassword, String confirmPassword) {
        List<String> values = Arrays.asList(currentPassword, newPassword, confirmPassword);
        IntStream.range(0, inputXPaths.size()).forEach(i -> getWebElement(By.xpath(inputXPaths.get(i))).sendKeys(values.get(i)));
    }

    private void openChangePassword() {
        loginUser(EMAIL, PASSWORD);
        openProfile();
        openEditProfile();
        clickCheckBox();
    }

    private void saveChanges() {
        clickElementWithJS(driver.findElement(By.xpath(SAVE_CHANGE)));
    }
}
