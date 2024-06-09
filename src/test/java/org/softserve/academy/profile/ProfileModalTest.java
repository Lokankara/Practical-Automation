package org.softserve.academy.profile;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.softserve.academy.runner.BaseTest;

import static org.junit.jupiter.api.Assertions.assertTrue;

class ProfileModalTest extends BaseTest {

    @Order(1)
    @DisplayName("1. Test successful login and check profile info")
    @ParameterizedTest(name = "User: {3} {2} login with email: {0}, password: {1}")
    @CsvFileSource(resources = "/sign.csv", numLinesToSkip = 1)
    void testSuccessfulLogin(String email, String password, String lastName, String firstName, String phone) {
        openModalWindow();

        fillAndAssertField(EMAIL_INPUT_XPATH, email);
        fillAndAssertField(PASSWORD_INPUT_XPATH, password);

        clickElementWithJS(getLoginButton());

        WebElement successMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(MESSAGE_SUCCESS_SELECTOR)));
        assertVisible(successMessage, "Success message");

        WebElement dropdownMenu = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@class='ant-dropdown-trigger user-profile']")));
        assertEnable(dropdownMenu, "After clicking the user icon Dropdown menu");
        dropdownMenu.click();

        WebElement profile = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[contains(text(),'Особистий кабінет')]")));
        assertEnable(profile, "After clicking the user Profile menu");
        clickElementWithJS(profile);

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
}
