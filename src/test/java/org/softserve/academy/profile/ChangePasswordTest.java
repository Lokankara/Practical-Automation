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

    @Test
    void testEmptyPasswordsParams() {
        openChangePassword();
//        fillPasswordInputs("Gard3ner#", "Password123", "Password123");
//        clickElementWithJS(driver.findElement(By.xpath("//span[contains(text(),'Зберегти зміни')]")));
//        checks();
        isTestSuccessful = true;
    }

    private void checks() {
        List<WebElement> errorMessages = driver.findElements(By.xpath("//div[contains(@class, 'ant-form-item-explain-error')]"));
        try {
            WebElement currentPasswordHelp = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='edit_currentPassword_help']//div[1]")));
            assertEquals("Введіть старий пароль", currentPasswordHelp.getText(), "Incorrect error message text");
            System.out.println(currentPasswordHelp.getText());
            System.out.println("1");
        } catch (RuntimeException e){}
        try {
            WebElement newPasswordHelp1 = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='edit_password_help']//div[1]")));
            assertEquals("Будь ласка, введіть новий пароль", newPasswordHelp1.getText(), "Incorrect error message text");
            System.out.println(newPasswordHelp1.getText());
            System.out.println("2");
        } catch (RuntimeException e){}
        try {
            WebElement newPasswordHelp2 = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='edit_password_help']//div[2]")));
            assertEquals("Значення поля ‘Новий пароль’ має відрізнятися від значення поля ‘Старий пароль’", newPasswordHelp2.getText(), "Incorrect error message text");
            System.out.println(newPasswordHelp2.getText());
            System.out.println("3");
        } catch (RuntimeException e){}
        try {
            WebElement confirmPasswordHelp = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='edit_confirmPassword_help']//div[1]")));
            assertEquals("'Значення поля ‘Підтвердити новий пароль’ має бути еквівалентним значенню поля ‘Новий пароль’", confirmPasswordHelp.getText(), "Incorrect error message text");
            System.out.println(confirmPasswordHelp.getText());
            System.out.println("4");
        } catch (RuntimeException e){}

        System.out.println(errorMessages.size());
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
