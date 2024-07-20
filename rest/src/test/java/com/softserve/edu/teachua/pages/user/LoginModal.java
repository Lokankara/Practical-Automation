package com.softserve.edu.teachua.pages.user;

import com.softserve.edu.teachua.pages.menu.HomePage;
import com.softserve.edu.teachua.pages.top.TopPart;
import com.softserve.edu.teachua.wraps.search.Search;
import com.softserve.edu.teachua.wraps.search.SearchStrategy;
import org.openqa.selenium.WebElement;
import java.util.List;

public class LoginModal {
    public static final String POPUP_MESSAGE_UNSUCCESSFULLY = "Введено невірний пароль або email";

    protected Search search;
    private WebElement emailInput;
    private WebElement passwordInput;
    private WebElement signInButton;
    private WebElement closeIcon;

    public LoginModal() {
        search = SearchStrategy.getSearch();
        initElements();
    }

    private void initElements() {
        // error for headless
        search = SearchStrategy.setExplicitExistText();
        search.isLocatedCss("a.restore-password-button");
        search = SearchStrategy.restoreStrategy();
        //
        emailInput = search.id("basic_email");
        passwordInput = search.id("basic_password");
        signInButton = search.cssSelector("div.login-footer button");
        closeIcon = search.cssSelector("span.ant-modal-close-icon");

    }

    // Page Object

    // emailInput
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

    // passwordInput
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

    // signInButton
    public WebElement getSignInButton() {
        return signInButton;
    }

    public String getSignInButtonText() {
        return getSignInButton().getAttribute(TopPart.TAG_ATTRIBUTE_VALUE);
    }

    public void clickSignInButton() {
        getSignInButton().click();
    }

    private void clickCloseIcon() {
        closeIcon.click();
    }

    // Functional

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

    public void fillLogin(String email, String password) {
        enterEmailInput(email);
        enterPasswordInput(password);
        clickSignInButton();
    }

    public String getPopupMessageLabelText() {
        search = SearchStrategy.setExplicitExistText();
        search.isLocatedCss(TopPart.POPUP_MESSAGE_CSSSELECTOR);
        search = SearchStrategy.restoreStrategy();
        //
        List<WebElement> popupMessageLabel = search.cssSelectors(TopPart.POPUP_MESSAGE_CSSSELECTOR);
        System.out.println("\tpopupMessageLabel.size() = " + popupMessageLabel.size());
        System.out.println("\tpopupMessageLabel.get(0).getText() = " + popupMessageLabel.get(0).getText());
        if (popupMessageLabel.isEmpty()) {
            return "";
        }
        return popupMessageLabel.get(0).getText();
    }

    // Business Logic

    public HomePage successfulLogin(String email, String password) {
        fillLogin(email, password);
        return new HomePage();
    }

    public LoginModal unsuccessfulLoginPage(String email, String password) {
        fillLogin(email, password);
        return new LoginModal();
    }

    public HomePage closeModal() {
        clickCloseIcon();
        return new HomePage();
    }
}
