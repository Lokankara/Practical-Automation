package org.softserve.academy.profile;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.softserve.academy.provider.InValidProfileUserProvider;
import org.softserve.academy.provider.InfoUserProvider;
import org.softserve.academy.provider.PasswordProvider;
import org.softserve.academy.provider.ValidProfileUserProvider;
import org.softserve.academy.runner.ProfileBaseTest;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.softserve.academy.provider.UserInfoData.NEW_PASSWORD;
import static org.softserve.academy.provider.UserInfoData.WRONG_PASSWORD;

class ChangeProfileTest extends ProfileBaseTest {

    @ParameterizedTest
    @CsvFileSource(resources = "/passwords.csv", numLinesToSkip = 1)
    void testChangePasswordSuccess(String current, String newPass, String confirm) {
        loginUser(EMAIL, current);
        openProfile();
        openEditProfile();
        clickCheckBox();
        fillPasswordInputs(current, newPass, confirm);
        saveChanges();

        assertSuccessMessage();
        isTestSuccessful = true;
    }

    @DisplayName("2. Test check profile info")
    @ParameterizedTest(name = "User: {1} {0} with phone {2}")
    @CsvFileSource(resources = "/edit-profile-user.csv", numLinesToSkip = 1)
    void testUserInfoSuccessChange(String lastName, String firstName, String phone) {
        loginUser(EMAIL, PASSWORD);
        openChangePassword();
        updateUserInfo(lastName, firstName, phone);
        saveChanges();

        assertSuccessMessage();
        isTestSuccessful = true;
    }

    @DisplayName("3. Test Success User Info Change With Password")
    @ParameterizedTest(name = "Test Success User {4} {3} with password {0}")
    @ArgumentsSource(ValidProfileUserProvider.class)
    void testUserInfoSuccessChangeWithPassword(
            String current, String newPass, String confirm, String lastName, String firstName, String phone) {
        loginUser(EMAIL, current);
        openProfile();
        openEditProfile();
        clickCheckBox();
        fillPasswordInputs(current, newPass, confirm);
        updateUserInfo(lastName, firstName, phone);
        saveChanges();

        assertSuccessMessage();
        isTestSuccessful = true;
    }

    @Test
    @DisplayName("5. Test Change profile with wrong current password")
    void testChangeWrongCurrentPassword() {
        loginUser(EMAIL, PASSWORD);
        openProfile();
        openEditProfile();
        clickCheckBox();
        fillPasswordInputs(WRONG_PASSWORD, NEW_PASSWORD, NEW_PASSWORD);
        saveChanges();

        assertErrorMessage();
        isTestSuccessful = true;
    }

    @DisplayName("5. Test Change profile with special symbols")
    @CsvFileSource(resources = "/invalid-passwords.csv", numLinesToSkip = 1)
    @ParameterizedTest(name = "5. Test Change profile with special symbol: {0}")
    void testChangeProfileWithSpecialSymbol(String password) {
        final String expected = "Пароль повинен містити великі/маленькі літери латинського алфавіту, цифри та спеціальні символи";
        loginUser(EMAIL, PASSWORD);
        openProfile();
        openEditProfile();
        clickCheckBox();

        fillPasswordInputs(PASSWORD, password, password);
        saveChanges();

        assertEquals(expected, getVisibleElement(By.xpath("//div[@id='edit_password_help']")).getText(),
                String.format("Error messages retrieved from edit password: %s help does not match expected", password));
        isTestSuccessful = true;
    }

    @ParameterizedTest(name = "{index}: {0}, {1}, {2}, {3}, {4}, {5}")
    @DisplayName("6. Test unsuccessful change with invalid values")
    @ArgumentsSource(InValidProfileUserProvider.class)
    void testUnSuccessfulChangeWithInValidValue(
            String lastName, String firstName, String phone, String current, String newPass,
            String confirm, List<String> expectedMessages, List<String> xPaths) {

        loginUser(EMAIL, PASSWORD);
        openProfile();
        openEditProfile();
        clickCheckBox();
        updateUserInfo(lastName, firstName, phone);
        fillPasswordInputs(current, newPass, confirm);
        saveChanges();

        IntStream.range(0, xPaths.size()).forEach(i ->
                assertEquals(expectedMessages.get(i), getVisibleElement(By.xpath(xPaths.get(i))).getText(),
                        "Error messages from element do not match expected by xpath: " + xPaths.get(i)));

        isTestSuccessful = true;
    }

    @ParameterizedTest(name = "{index}: Test change User info: {0} {1}, phone: {2}")
    @ArgumentsSource(InfoUserProvider.class)
    void testUserInfoChange(
            String lastName, String firstName, String phone,
            List<String> xPaths, List<String> expectedMessages) {
        loginUser(EMAIL, PASSWORD);
        openChangePassword();

        updateUserInfo(lastName, firstName, phone);
        saveChanges();

        IntStream.range(0, xPaths.size()).forEach(i ->
                assertEquals(expectedMessages.get(i),
                        getVisibleElement(By.xpath(xPaths.get(i))).getText(),
                        "Error messages from element does not match expected"));
    }

    @ParameterizedTest(name = "Test change info Current password: {0} newPassword: {1}, confirmPassword: {2}")
    @ArgumentsSource(PasswordProvider.class)
    void testPasswordChange(
            String currentPassword, String newPassword, String confirmPassword,
            List<String> xPaths, List<String> expectedMessages) {
        loginUser(EMAIL, PASSWORD);
        openChangePassword();
        clickCheckBox();
        fillPasswordInputs(currentPassword, newPassword, confirmPassword);
        saveChanges();

        IntStream.range(0, xPaths.size()).forEach(i ->
                assertEquals(expectedMessages.get(i),
                        getVisibleElement(By.xpath(xPaths.get(i))).getText(),
                        String.format("Error messages from label for password: %s do not match expected by xpath %s", currentPassword, xPaths.get(i))));
    }

    private void fillUserInput(WebElement element, String value) {
        element.sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.DELETE));
        element.sendKeys(value);
    }

    private void fillPasswordInputs(
            String currentPassword, String newPassword, String confirmPassword) {
        List<String> values = Arrays.asList(currentPassword, newPassword, confirmPassword);
        IntStream.range(0, inputPasswordXPaths.size()).forEach(i ->
                getVisibleElement(By.xpath(inputPasswordXPaths.get(i))).sendKeys(values.get(i)));
    }

    private void updateUserInfo(String lastName, String firstName, String phone) {
        fillUserInput(getElementByXpath(inputUserXPaths[0]), lastName);
        fillUserInput(getElementByXpath(inputUserXPaths[1]), firstName);
        fillUserInput(getElementByXpath(inputUserXPaths[2]), phone);
    }

    private void openChangePassword() {
        openProfile();
        openEditProfile();
    }

    private void saveChanges() {
        clickElementWithJS(getElementByXpath(SAVE_CHANGE));
    }
}
