package org.softserve.academy.profile;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.softserve.academy.provider.PasswordProvider;
import org.softserve.academy.runner.ProfileBaseTest;

import java.util.List;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ChangePasswordTest extends ProfileBaseTest {

    @ParameterizedTest
    @CsvFileSource(resources = "/passwords.csv", numLinesToSkip = 1)
    void testChangePasswordSuccess(String current, String newPass, String confirm) {
        loginUser(EMAIL, current);
        openProfile();
        clickEditProfile();
        clickElementWithJS(wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@name='checkbox']"))));

        fillPasswordInputs(current, newPass, confirm);
        clickElementWithJS(driver.findElement(By.xpath("//span[contains(text(),'Зберегти зміни')]")));

        WebElement successMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[contains(text(),'Профіль змінено успішно')]")));
        assertEquals("Профіль змінено успішно", successMessage.getText());
        isTestSuccessful = true;
    }

    @Test
    void testChangeWrongCurrentPassword() {
        loginUser(EMAIL, PASSWORD);
        openProfile();
        clickEditProfile();
        clickElementWithJS(wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@name='checkbox']"))));

        fillPasswordInputs("TestPassword123!", "NewPassword123!", "NewPassword123!");
        clickElementWithJS(driver.findElement(By.xpath("//span[contains(text(),'Зберегти зміни')]")));

        WebElement errorMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[contains(text(),'Введено невірний пароль')]")));
        assertEquals("Введено невірний пароль", errorMessage.getText());
        isTestSuccessful = true;
    }

    @ParameterizedTest(name = "Test change Current Password: {0} newPassword: {1}, confirmPassword: {2}")
    @ArgumentsSource(PasswordProvider.class)
    void testPasswordChange(String currentPassword, String newPassword, String confirmPassword, List<String> xpaths, List<String> expectedMessages) {
        openChangePassword();

        fillPasswordInputs(currentPassword, newPassword, confirmPassword);
        clickElementWithJS(driver.findElement(By.xpath("//span[contains(text(),'Зберегти зміни')]")));
        IntStream.range(0, xpaths.size()).forEach(
                i -> assertEquals(expectedMessages.get(i),
                wait.until(ExpectedConditions.visibilityOfElementLocated(
                        By.xpath(xpaths.get(i)))).getText()));
    }

    private static void fillPasswordInputs(String currentPassword, String newPassword, String confirmPassword) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='edit_currentPassword']"))).sendKeys(currentPassword);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='edit_password']"))).sendKeys(newPassword);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='edit_confirmPassword']"))).sendKeys(confirmPassword);
    }

    private void openChangePassword() {
        loginUser(EMAIL, PASSWORD);
        openProfile();
        clickEditProfile();
        clickElementWithJS(wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@name='checkbox']"))));
    }
}
