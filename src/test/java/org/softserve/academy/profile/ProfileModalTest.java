package org.softserve.academy.profile;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.softserve.academy.runner.BaseTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ProfileModalTest extends BaseTest {

    @Test
    @Order(1)
    @DisplayName("1. Test check profile header")
    void testProfileHeader() {
        loginUser(EMAIL, PASSWORD);
        openProfile();

        WebElement profileHeader = driver.findElement(By.xpath("//div[@class='content-title']"));
        assertVisible(profileHeader, "profile Header");
        assertTextEquals("Мій профіль", profileHeader, "profile Header");
        isTestSuccessful = true;
    }

    @Order(2)
    @DisplayName("2. Test check profile info")
    @ParameterizedTest(name = "User: {3} {2} login with email: {0}, password: {1}")
    @CsvFileSource(resources = "/sign.csv", numLinesToSkip = 1)
    void testProfileInfo(String email, String password, String lastName, String firstName, String phone) {
        loginUser(email, password);
        openProfile();

        WebElement phoneElement = driver.findElement(By.xpath("//div[@class='user-phone-data']"));
        WebElement emailElement = driver.findElement(By.xpath("//div[@class='user-email-data']"));
        WebElement fullNameElement = driver.findElement(By.xpath("//div[@class='user-name']"));

        assertVisible(phoneElement, "Phone after clicking the user profile");
        assertVisible(emailElement, "Email after clicking the user profile");
        assertVisible(fullNameElement, "Fullname after clicking the user profile");
        assertTextEquals(email, emailElement, "After clicking the user profile Email");
        assertTrue(phoneElement.getText().contains(phone), "Phone does not match the " + lastName);
        assertTrue(fullNameElement.getText().contains(lastName), "Fullname does not match the " + lastName);
        assertTrue(fullNameElement.getText().contains(firstName), "Fullname does not match the " + firstName);
        isTestSuccessful = true;
    }

    @Test
    @Order(3)
    @DisplayName("3. Test edit profile button")
    void testEditProfileButton() {
        loginUser(EMAIL, PASSWORD);
        openProfile();

        WebElement editButton = driver.findElement(By.xpath("//div[@class='edit-button']"));
        assertVisible(editButton, "Edit user profile");
        assertEnable(editButton, "Edit user profile");
        assertTextEquals("Редагувати профіль", editButton, "Edit user profile text");
        isTestSuccessful = true;
    }

    @Order(4)
    @ParameterizedTest(name = "User: {3} {2} login with email: {0}, password: {1}")
    @CsvFileSource(resources = "/sign.csv", numLinesToSkip = 1)
    @DisplayName("4. Test edit profile modal")
    void testEditProfileModal(String email, String password, String lastName, String firstName, String phone) {
        loginUser(email, password);
        openProfile();
        clickEditProfile();

        WebElement lastNameInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='edit_lastName']")));
        assertVisible(lastNameInput, "Last name input is null");
        assertTrue(lastNameInput.isEnabled(), "Last name input is not editable");
//        assertAttributeEquals(lastName, lastNameInput, "value");
        System.out.println(lastNameInput.getAttribute("value"));

        WebElement firstNameInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='edit_firstName']")));
        assertVisible(firstNameInput, "First name input is null");
        assertTrue(firstNameInput.isEnabled(), "First name input is not editable");
//        assertAttributeEquals(firstName, firstNameInput, "value");

        WebElement phoneInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='edit_phone']")));
        assertVisible(phoneInput, "Phone input is null");
        assertTrue(phoneInput.isEnabled(), "Phone input is not editable");
        assertTrue(phoneInput.getAttribute("value").contains(phone), phone);

        WebElement emailInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='edit_email']")));
        assertVisible(emailInput, "Email input is not visible");
        assertFalse(emailInput.isEnabled(), "Email input should be readonly");
        assertAttributeEquals(email, emailInput, "value");
        isTestSuccessful = true;
    }

    @Order(5)
    @ParameterizedTest(name = "User: {3} {2} login with email: {0}, password: {1}")
    @CsvFileSource(resources = "/sign.csv", numLinesToSkip = 1)
    @DisplayName("5. Test edit profile modal Password inputs")
    void testEditProfileModalPassword(String email, String password, String lastName, String firstName) {
        loginUser(email, password);
        openProfile();
        clickEditProfile();

        WebElement checkbox = driver.findElement(By.xpath(".//input[@name='checkbox']"));
        List<WebElement> elements = driver.findElements(By.xpath("//*[contains(@class, 'ant-input-affix-wrapper') and contains(@class, 'ant-input-password') and contains(@class, 'user-edit-box')]"));
        clickElementWithJS(checkbox);

        elements.forEach(element -> assertTrue(element.isDisplayed(), "The element is not displayed."));

        WebElement currentPassword = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='edit_currentPassword']")));
        WebElement newPassword = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='edit_password']")));
        WebElement confirmPassword = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='edit_confirmPassword']")));

        assertTrue(currentPassword.isDisplayed());
        assertTrue(newPassword.isDisplayed());
        assertTrue(confirmPassword.isDisplayed());
        assertTrue(currentPassword.isEnabled());
        assertTrue(newPassword.isEnabled());
        assertTrue(confirmPassword.isEnabled());

        clickElementWithJS(checkbox);

        assertTrue(elements.isEmpty(), "The list of elements is empty.");
        isTestSuccessful = true;
    }

    private void clickEditProfile() {
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@class='edit-button']"))).click();
        WebElement editTitle = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='menu-title']")));
        assertTextEquals("Особистий кабінет", editTitle, "edit Title user profile text");
        WebElement editHeader = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='content-title']")));
        assertTextEquals("Мій профіль", editHeader, "Edit user profile text");
    }

    private void openProfile() {
        WebElement dropdownMenu = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@class='ant-dropdown-trigger user-profile']")));
        assertEnable(dropdownMenu, "After clicking the user icon Dropdown menu");
        dropdownMenu.click();
        WebElement profile = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[contains(text(),'Особистий кабінет')]")));
        assertEnable(profile, "After clicking the user Profile menu");
        clickElementWithJS(profile);
    }
}
