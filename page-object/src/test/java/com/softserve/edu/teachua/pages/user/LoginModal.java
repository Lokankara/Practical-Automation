package com.softserve.edu.teachua.pages.user;

import com.softserve.edu.teachua.entity.User;
import com.softserve.edu.teachua.pages.menu.HomePage;
import com.softserve.edu.teachua.pages.top.TopPart;
import com.softserve.edu.teachua.pages.top.Waiter;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class LoginModal extends Waiter {
    private WebElement emailInput;
    private WebElement passwordInput;
    private WebElement emailFeedbackIcon;
    private WebElement passwordFeedbackIcon;
    private WebElement signInButton;
    public static final String ERROR_MASSAGE = "Введено невірний пароль або email";
    private static final By NOTICE_ERROR_SELECTOR = By.cssSelector(".ant-message-notice.ant-message-notice-error");
    public static final String SUCCESS_MESSAGE_TEXT = "Ви успішно залогувалися!";
    private static final By EMAIL_FEEDBACK_ICON = By.xpath("//input[@id='basic_email']/ancestor::span[contains(@class, 'ant-input-affix-wrapper')]/descendant::span[contains(@class, 'ant-form-item-feedback-icon')]");
    private static final By PASSWORD_FEEDBACK_ICON = By.xpath("//input[@id='basic_password']/ancestor::span[contains(@class, 'ant-input-affix-wrapper')]/descendant::span[contains(@class, 'ant-form-item-feedback-icon')]");
    public static final By MESSAGE_SUCCESS_XPATH = By.xpath("//div[contains(@class, 'ant-message-notice-content')]");


    public LoginModal(WebDriver driver) {
        super(driver);
        initElements();
    }

    private void initElements() {
        emailInput = driver.findElement(By.id("basic_email"));
        passwordInput = driver.findElement(By.id("basic_password"));
        signInButton = driver.findElement(By.cssSelector("div.login-footer button"));
    }

    private WebElement getEmailFeedback() {
        return emailFeedbackIcon;
    }

    private WebElement getPasswordFeedback() {
        return passwordFeedbackIcon;
    }

    public WebElement getEmailInput() {
        return emailInput;
    }

    public String getEmailInputText() {
        return getEmailInput().getAttribute(TopPart.TAG_ATTRIBUTE_VALUE);
    }

    public void clearEmailInput() {
        getEmailInput().clear();
    }

    public void clickEmailInput() {
        getEmailInput().click();
    }

    public void setEmailInput(String email) {
        getEmailInput().sendKeys(email);
    }

    public WebElement getPasswordInput() {
        return passwordInput;
    }

    public String getPasswordInputText() {
        return getPasswordInput().getAttribute(TopPart.TAG_ATTRIBUTE_VALUE);
    }

    public void clearPasswordInput() {
        getPasswordInput().clear();
    }

    public void clickPasswordInput() {
        getPasswordInput().click();
    }

    public void setPasswordInput(String password) {
        getPasswordInput().sendKeys(password);
    }

    public WebElement getSignInButton() {
        return signInButton;
    }

    public String getSignInButtonText() {
        return getSignInButton().getAttribute(TopPart.TAG_ATTRIBUTE_VALUE);
    }

    public void clickSignInButton() {
        getSignInButton().click();
    }

    private void enterEmailInput(String email) {
        clickEmailInput();
        clearEmailInput();
        setEmailInput(email);
    }

    private void enterPasswordInput(String password) {
        clickPasswordInput();
        clearPasswordInput();
        setPasswordInput(password);
    }

    public void fillLogin(User user) {
        enterEmailInput(user.email());
        enterPasswordInput(user.password());
        clickSignInButton();
    }

    public HomePage successfulLogin(User validUser) {
        fillLogin(validUser);
        return new HomePage(driver);
    }

    public LoginModal unsuccessfulLoginPage(User invalidUser) {
        fillLogin(invalidUser);
        return new LoginModal(driver);
    }

    public WebElement getEmailFeedbackIcon() {
        emailFeedbackIcon = waitForVisibilityOfElement(EMAIL_FEEDBACK_ICON);
        return getEmailFeedback();
    }

    public WebElement getPasswordFeedbackIcon() {
        passwordFeedbackIcon = waitForVisibilityOfElement(PASSWORD_FEEDBACK_ICON);
        return getPasswordFeedback();
    }

    public String getSuccessLoginMessage() {
        return waitForVisibilityOfElement(MESSAGE_SUCCESS_XPATH).getText();
    }

    public void assertSuccessfulPopupMessage() {
        Assertions.assertEquals(SUCCESS_MESSAGE_TEXT, getSuccessLoginMessage());
    }
}
