package com.softserve.edu.teachua.pages.user;

import com.softserve.edu.teachua.data.user.IUser;
import com.softserve.edu.teachua.pages.menu.HomePage;
import com.softserve.edu.teachua.pages.top.BaseModal;
import com.softserve.edu.teachua.pages.top.TopPart;
import com.softserve.edu.teachua.wraps.browser.DriverUtils;
import org.apache.log4j.Logger;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

import static com.softserve.edu.teachua.pages.top.TopPart.POPUP_MESSAGE_SUCCESSFULLY;

public class LoginModal extends BaseModal {
    protected static final Logger logger = Logger.getLogger(LoginModal.class.getName());
    private static final String EMAIL_FEEDBACK_XPATH = "//input[@id='basic_email']/ancestor::span[contains(@class, 'ant-input-affix-wrapper')]/descendant::span[contains(@class, 'ant-form-item-feedback-icon')]";
    private static final String PASSWORD_FEEDBACK_XPATH = "//input[@id='basic_password']/ancestor::span[contains(@class, 'ant-input-affix-wrapper')]/descendant::span[contains(@class, 'ant-form-item-feedback-icon')]";
    public static final String MESSAGE_SUCCESS_XPATH = "//div[contains(@class, 'ant-message-notice-content')]";
    public static final String POPUP_MESSAGE_UNSUCCESSFULLY = "Введено невірний пароль або email";
    private WebElement emailInput;
    private WebElement emailFeedbackIcon;
    private WebElement passwordInput;
    private WebElement passwordFeedbackIcon;
    private WebElement signInButton;

    public LoginModal() {
        initElements();
    }

    private void initElements() {
        emailInput = search.id("basic_email");
        passwordInput = search.id("basic_password");
        signInButton = search.cssSelector("div.login-footer button");
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

    private WebElement getEmailFeedback() {
        return emailFeedbackIcon;
    }

    private WebElement getPasswordFeedback() {
        return passwordFeedbackIcon;
    }

    public void assertSuccessfulPopupMessage() {
        Assertions.assertEquals(POPUP_MESSAGE_SUCCESSFULLY, getSuccessLoginMessage());
    }

    public String getSuccessLoginMessage() {
        return search.xpath(MESSAGE_SUCCESS_XPATH).getText();
    }

    public WebElement getEmailFeedbackIcon() {
        emailFeedbackIcon = search.xpath(EMAIL_FEEDBACK_XPATH);
        return getEmailFeedback();
    }

    public WebElement getPasswordFeedbackIcon() {
        passwordFeedbackIcon = search.xpath(PASSWORD_FEEDBACK_XPATH);
        return getPasswordFeedback();
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

    public void fillLogin(IUser user) {
        enterEmailInput(user.getEmail());
        enterPasswordInput(user.getPassword());
        clickSignInButton();
    }

    public String getPopupMessageLabelText() {
        new WebDriverWait(DriverUtils.getInstance().getDriver(), Duration.ofSeconds(10)).until(
                (ExpectedCondition<Boolean>) driver -> {
                    WebElement popup = search.cssSelector(TopPart.POPUP_MESSAGE_CSS);
                    logger.warn("\tpopup.getText() = " + popup.getText());
                    return !popup.getText().isEmpty();
                }
        );

        List<WebElement> popupMessageLabel = search.cssSelectors(TopPart.POPUP_MESSAGE_CSS);
        logger.info("\tpopupMessageLabel.size() = " + popupMessageLabel.size());
        logger.info("\tpopupMessageLabel.get(0).getText() = " + popupMessageLabel.get(0).getText());
        return popupMessageLabel.isEmpty() ? "" : popupMessageLabel.get(0).getText();
    }

    public HomePage successfulLogin(IUser validUser) {
        fillLogin(validUser);
        return new HomePage();
    }

    public LoginModal unsuccessfulLoginPage(IUser invalidUser) {
        fillLogin(invalidUser);
        return new LoginModal();
    }
}
