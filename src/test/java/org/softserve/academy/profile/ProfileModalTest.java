package org.softserve.academy.profile;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.softserve.academy.runner.ProfileBaseTest;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ProfileModalTest extends ProfileBaseTest {

    @Test
    @Order(1)
    @DisplayName("1. Test check profile header")
    void testProfileHeader() {
        loginUser(EMAIL, PASSWORD);
        openProfile();

        assertTextEquals("Мій профіль", getElementByXpath("//div[@class='content-title']"), "profile Header");
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
                assertContains(getElementByXpath(labelUserXPaths.get(i)), labelUserXPaths.get(i), expectedTexts.get(i)));
        isTestSuccessful = true;
    }

    @Test
    @Order(3)
    @DisplayName("3. Test edit profile button")
    void testEditProfileButton() {
        loginUser(EMAIL, PASSWORD);
        openProfile();
        WebElement editButton = getElementByXpath(EDIT_BUTTON);

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
        clickElementWithJS(getElementByXpath(SAVE_CHANGE));

        assertSuccessMessage();
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
            assertSuccessMessage();
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
        final boolean[] shouldBeEnabled = {true, true, true, false};
        final String[] expectedValues = {lastName, firstName, phone, email};
        loginUser(email, password);
        openProfile();
        openEditProfile();

        IntStream.range(0, inputUserXPaths.length).forEach(i ->
                assertInput(getVisibleElement(By.xpath(inputUserXPaths[i])), expectedValues[i], shouldBeEnabled[i]));
        isTestSuccessful = true;
    }

    @Order(5)
    @ParameterizedTest(name = "Test edit profile modal login with email: {0}, password: {1}")
    @CsvFileSource(resources = "/sign.csv", numLinesToSkip = 1)
    @DisplayName("5. Test edit profile modal Password inputs")
    void testEditProfileModalPassword(String email, String password) {
        loginUser(email, password);
        openProfile();
        openEditProfile();
        clickCheckBox();

        driver.findElements(By.xpath(EDIT_INPUTS)).forEach(element ->
                assertTrue(element.isDisplayed(), "The element should be displayed."));

        inputPasswordXPaths.forEach(inputXPath ->
                assertEnable(getVisibleElement(By.xpath(inputXPath)), "Element with " + inputXPath));

        clickCheckBox();

        assertTrue(driver.findElements(By.xpath(EDIT_INPUTS)).isEmpty(), "The list of elements should be empty.");
        isTestSuccessful = true;
    }
}
