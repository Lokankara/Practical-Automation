package org.softserve.academy.profile;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.softserve.academy.provider.InfoUserProvider;
import org.softserve.academy.provider.LoginProvider;
import org.softserve.academy.provider.PasswordProvider;
import org.softserve.academy.provider.ProfileUserProvider;
import org.softserve.academy.runner.ProfileBaseTest;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
    @ArgumentsSource(ProfileUserProvider.class)
    void testUserInfoSuccessChangeWithPassword(String current, String newPass, String confirm, String lastName, String firstName, String phone) {
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
    void testChangeWrongCurrentPassword() {
        final String password = "NewPassword123!";
        loginUser(EMAIL, PASSWORD);
        openProfile();
        openEditProfile();
        clickCheckBox();
        fillPasswordInputs("TestPassword123!", password, password);
        saveChanges();

        assertErrorMessage();
        isTestSuccessful = true;
    }

    @ParameterizedTest(name = "Test change Current lastName: {0} firstName: {1}, phone: {2}")
    @ArgumentsSource(InfoUserProvider.class)
    void testUserInfoChange(String lastName, String firstName, String phone, List<String> xPaths, List<String> expectedMessages) {
        loginUser(EMAIL, PASSWORD);
        openChangePassword();

        updateUserInfo(lastName, firstName, phone);
        saveChanges();

        IntStream.range(0, xPaths.size()).forEach(i ->
                assertEquals(expectedMessages.get(i), getVisibleElement(By.xpath(xPaths.get(i))).getText()));
    }

    @ParameterizedTest(name = "Test change Current Password: {0} newPassword: {1}, confirmPassword: {2}")
    @ArgumentsSource(PasswordProvider.class)
    void testPasswordChange(String currentPassword, String newPassword, String confirmPassword, List<String> xPaths, List<String> expectedMessages) {
        loginUser(EMAIL, PASSWORD);
        openChangePassword();
        clickCheckBox();
        fillPasswordInputs(currentPassword, newPassword, confirmPassword);
        saveChanges();

        IntStream.range(0, xPaths.size()).forEach(i ->
                assertEquals(expectedMessages.get(i), getVisibleElement(By.xpath(xPaths.get(i))).getText()));
    }

    private void fillUserInput(WebElement element, String value) {
        element.sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.DELETE));
        element.sendKeys(value);
    }

    private void fillPasswordInputs(String currentPassword, String newPassword, String confirmPassword) {
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
