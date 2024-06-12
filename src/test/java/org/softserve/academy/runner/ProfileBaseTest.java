package org.softserve.academy.runner;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public abstract class ProfileBaseTest extends BaseTest {
    protected static final List<String> inputPasswordXPaths = Arrays.asList("//input[@id='edit_currentPassword']", "//input[@id='edit_password']", "//input[@id='edit_confirmPassword']");
    protected static final List<String> labelUserXPaths = Arrays.asList("//div[@class='user-phone-data']", "//div[@class='user-email-data']", "//div[@class='user-name']");
    protected static final String[] inputUserXPaths = {"//input[@id='edit_lastName']", "//input[@id='edit_firstName']", "//input[@id='edit_phone']", "//input[@id='edit_email']"};
    protected static final String EDIT_INPUTS = "//*[contains(@class, 'ant-input-affix-wrapper') and contains(@class, 'ant-input-password') and contains(@class, 'user-edit-box')]";
    protected static final String DROPDOWN_USER_PROFILE = "//div[@class='ant-dropdown-trigger user-profile']";
    protected static final String BOX_INPUT = "//span[contains(@class, 'user-edit-box')]//input";
    protected static final String SAVE_CHANGE = "//span[contains(text(),'Зберегти зміни')]";
    protected static final String CABINET = "//a[contains(text(),'Особистий кабінет')]";
    protected static final String EDIT_BUTTON = "//div[@class='edit-button']";

    protected void openEditProfile() {
        getVisibleElement(By.xpath(EDIT_BUTTON)).click();
        WebElement editTitle = getVisibleElement(By.xpath("//div[@class='menu-title']"));
        assertTextEquals("Особистий кабінет", editTitle, "edit Title user profile text");
        WebElement editHeader = getVisibleElement(By.xpath("//div[@class='content-title']"));
        assertTextEquals("Мій профіль", editHeader, "Edit user profile text");
    }

    protected void openProfile() {
        WebElement dropdownMenu = getClickableElement(By.xpath(DROPDOWN_USER_PROFILE));
        assertEnable(dropdownMenu, "After clicking the user icon Dropdown menu");
        dropdownMenu.click();
        
        WebElement profile = getClickableElement(By.xpath(CABINET));
        assertEnable(profile, "After clicking the user Profile menu");
        clickElementWithJS(profile);
    }

    protected void clickCheckBox() {
        clickElementWithJS(getClickableElement(By.xpath("//input[@name='checkbox']")));
    }

    protected void assertSuccessMessage() {
        assertEquals("Профіль змінено успішно",
                getVisibleElement(By.xpath(SUCCESS_MESSAGE)).getText(),
                "The success message displayed does not match the expected message: " + SUCCESS_MESSAGE);
    }

    protected void assertErrorMessage() {
        assertEquals("Введено невірний пароль",
                getVisibleElement(By.xpath(ERROR_MESSAGE)).getText(),
                "The error message displayed does not match the expected message: " + ERROR_MESSAGE);
    }

    protected void assertInput(WebElement element, String expected, boolean actual) {
        assertVisible(element, String.format("The input element for %s should be visible.", element.getText()));
        assertEquals(element.isEnabled(), actual, String.format("The input element for %s should %s", element.getText(), actual ? "be enabled." : "be disabled."));
        assertTrue(element.getAttribute("value").contains(expected), String.format("The value of the input element for %s should contain the expected text.", element.getText()));
    }
}
