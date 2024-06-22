package com.softserve.edu.teachua.modal;

import com.softserve.edu.teachua.entity.User;
import com.softserve.edu.teachua.page.HomePage;
import com.softserve.edu.teachua.part.BasePart;
import com.softserve.edu.teachua.part.TopPart;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import static com.softserve.edu.teachua.page.HomePage.MESSAGE_SUCCESS_XPATH;
import static com.softserve.edu.teachua.page.HomePage.SUCCESS_MESSAGE_TEXT;

public class LoginModal extends BasePart<LoginModal> {
    private WebElement emailInput;
    private WebElement passwordFeedbackIcon;
    private WebElement emailFeedbackIcon;
    private WebElement passwordInput;
    private WebElement signInButton;
    private WebElement loginHeader;
    private final By header = By.cssSelector("div.login-header");
    private final By loginButton = By.cssSelector("div.login-footer button");
    private static final String NOTICE_ERROR_SELECTOR = ".ant-message-notice.ant-message-notice-error";
    private final By EMAIL_FEEDBACK_ICON = By.xpath("//input[@id='basic_email']/ancestor::span[contains(@class, 'ant-input-affix-wrapper')]/descendant::span[contains(@class, 'ant-form-item-feedback-icon')]");
    private final By PASSWORD_FEEDBACK_ICON = By.xpath("//input[@id='basic_password']/ancestor::span[contains(@class, 'ant-input-affix-wrapper')]/descendant::span[contains(@class, 'ant-form-item-feedback-icon')]");
    private static final String TAG_ATTRIBUTE_CLASS = "class";
    public static final String LOGIN_HEADER = "Вхід";

    public LoginModal(WebDriver driver) {
        super(driver);
        initElements();
    }

    @Override
    protected LoginModal self() {
        return this;
    }

    private void initElements() {
        emailInput = getElementBy(By.id("basic_email"));
        passwordInput = getElementBy(By.id("basic_password"));
        signInButton = getElementBy(loginButton);
        loginHeader = getVisibleElement(header);
    }

    private WebElement getEmailInput() {
        return emailInput;
    }

    private WebElement getEmailFeedback() {
        return emailFeedbackIcon;
    }
    private WebElement getPasswordFeedback() {
        return passwordFeedbackIcon;
    }

    private String getEmailInputText() {
        return getEmailInput().getAttribute(TopPart.TAG_ATTRIBUTE_VALUE);
    }

    private void clearEmailInput() {
        getEmailInput().clear();
    }

    private void clickEmailInput() {
        getEmailInput().click();
    }

    private void setEmailInput(String email) {
        getEmailInput().sendKeys(email);
    }

    private WebElement getPasswordInput() {
        return passwordInput;
    }

    private String getPasswordInputText() {
        return getPasswordInput().getAttribute(TopPart.TAG_ATTRIBUTE_VALUE);
    }

    private void clearPasswordInput() {
        getPasswordInput().clear();
    }

    private void clickPasswordInput() {
        getPasswordInput().click();
    }

    private void setPasswordInput(String password) {
        getPasswordInput().sendKeys(password);
    }

    private WebElement getSignInButton() {
        return signInButton;
    }


    private String getSignInButtonText() {
        return getSignInButton().getAttribute(TopPart.TAG_ATTRIBUTE_VALUE);
    }

    private void clickSignInButton() {
        getSignInButton().click();
    }

    private void enterEmailInput(String email) {
        clickEmailInput();
        clearEmailInput();
        setEmailInput(email);
    }

    public String getLoginHeader() {
        return loginHeader.getText();
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

    private void assertFillEmailFeedbackIconsSuccess(boolean isSuccess) {
        Assertions.assertEquals(isSuccess, isFeedbackIconSuccess(getEmailFeedbackIcon()), "Email icon should be with class icon-success");
        Assertions.assertEquals(!isSuccess, isFeedbackIconError(getEmailFeedbackIcon()), "Email icon should be with class icon-error");
    }

    private void assertFillPasswordFeedbackIconsSuccess(boolean isSuccess) {
        Assertions.assertEquals(isSuccess, isFeedbackIconSuccess(getPasswordFeedbackIcon()), "Password icon should be with class icon-success");
        Assertions.assertEquals(!isSuccess, isFeedbackIconError(getPasswordFeedbackIcon()), "Password icon should be with class icon-error");
    }

    private boolean isFeedbackIconSuccess(WebElement element) {
        return getAttribute(element).contains("ant-form-item-feedback-icon-success");
    }

    private boolean isFeedbackIconError(WebElement element) {
        return getAttribute(element).contains("ant-form-item-feedback-icon-error");
    }

    private String getAttribute(WebElement element) {
        return element.getAttribute(TAG_ATTRIBUTE_CLASS);
    }

    public HomePage successfulLogin(User validUser) {
        fillLogin(validUser);
        assertFillEmailFeedbackIconsSuccess(true);
        assertFillPasswordFeedbackIconsSuccess(true);
        assertSuccessfulPopupMessage();
        return new HomePage(driver);
    }

    public LoginModal unsuccessfulLoginPage(User invalidUser) {
        fillLogin(invalidUser);
        assertFillEmailFeedbackIconsSuccess(true);
        assertFillPasswordFeedbackIconsSuccess(true);
        return self();
    }

    public String getErrorLoginMessage() {
        return getVisibleElement(By.cssSelector(NOTICE_ERROR_SELECTOR)).getText();
    }

    public String getSuccessLoginMessage() {
        return getVisibleElement(By.xpath(MESSAGE_SUCCESS_XPATH)).getText();
    }

    public WebElement getEmailFeedbackIcon() {
        emailFeedbackIcon = getVisibleElement(EMAIL_FEEDBACK_ICON);
        return getEmailFeedback();
    }

    public WebElement getPasswordFeedbackIcon() {
        passwordFeedbackIcon = getVisibleElement(PASSWORD_FEEDBACK_ICON);
        return getPasswordFeedback();
    }

    public void assertSuccessfulPopupMessage() {
        Assertions.assertEquals(SUCCESS_MESSAGE_TEXT, getSuccessLoginMessage());
    }
}
