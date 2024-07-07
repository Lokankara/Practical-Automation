package com.softserve.edu.teachua.pages.user;

import com.softserve.edu.teachua.pages.menu.HomePage;
import com.softserve.edu.teachua.pages.top.TopPart;
import com.softserve.edu.teachua.tools.ReportUtils;
import com.softserve.edu.teachua.wraps.search.Search;
import com.softserve.edu.teachua.wraps.search.SearchStrategy;
import org.openqa.selenium.WebElement;

import java.util.List;

public class LoginModal {
    public static final String POPUP_MESSAGE_UNSUCCESSFULLY = "Введено невірний пароль або email";

    protected Search search;
    private WebElement emailInput;
    //private WebElement emailFeedbackIcon; // TODO
    private WebElement passwordInput;
    //private WebElement passwordFeedbackIcon; // TODO
    private WebElement signInButton;

    public LoginModal() {
        search = SearchStrategy.getSearch();
        initElements();
    }

    private void initElements() {
        ReportUtils.logInfo("Initializing elements in LoginModal");
        emailInput = search.id("basic_email");
        passwordInput = search.id("basic_password");
        signInButton = search.cssSelector("div.login-footer button");
    }

    public WebElement getEmailInput() {
        ReportUtils.logInfo("Get Email Input element");
        return emailInput;
    }

    public String getEmailInputText() {
        String text = getEmailInput().getAttribute(TopPart.TAG_ATTRIBUTE_VALUE);
        ReportUtils.logInfo("Retrieved email input text: " + text);
        return text;
    }

    public void clearEmailInput() {
        ReportUtils.logAction("Clearing email input");
        getEmailInput().clear();
    }

    public void clickEmailInput() {
        ReportUtils.logAction("Clicking on email input");
        getEmailInput().click();
    }

    public void setEmailInput(String email) {
        ReportUtils.logAction("Entering email input: " + email);
        getEmailInput().sendKeys(email);
    }

    public WebElement getPasswordInput() {
        ReportUtils.logInfo("Get password Input element");
        return passwordInput;
    }

    public String getPasswordInputText() {
        String text = getPasswordInput().getAttribute(TopPart.TAG_ATTRIBUTE_VALUE);
        ReportUtils.logInfo("Retrieved password input text: " + text);
        return text;
    }

    public void clearPasswordInput() {
        ReportUtils.logAction("Clearing password input");
        getPasswordInput().clear();
    }

    public void clickPasswordInput() {
        ReportUtils.logAction("Clicking on password input");
        getPasswordInput().click();
    }

    public void setPasswordInput(String password) {
        ReportUtils.logAction("Entering password input");
        getPasswordInput().sendKeys(password);
    }


    public WebElement getSignInButton() {
        ReportUtils.logInfo("Get signIn Button element");
        return signInButton;
    }

    public String getSignInButtonText() {
        String text = getSignInButton().getAttribute(TopPart.TAG_ATTRIBUTE_VALUE);
        ReportUtils.logInfo("Retrieved sign-in button text: " + text);
        return text;
    }

    public void clickSignInButton() {
        ReportUtils.logAction("Clicking on sign-in button");
        getSignInButton().click();
    }

    private void enterEmailInput(String email) {
        clickEmailInput();
        clearEmailInput();
        setEmailInput(email);
        ReportUtils.logAction("Entering email input: " + email);
    }

    private void enterPasswordInput(String password) {
        clickPasswordInput();
        clearPasswordInput();
        setPasswordInput(password);
        ReportUtils.logAction("Entering password input: " + password);
    }

    public void fillLogin(String email, String password) {
        ReportUtils.logAction("Filling login with email: " + email + " and password " + password);
        enterEmailInput(email);
        enterPasswordInput(password);
        clickSignInButton();
        ReportUtils.logInfo("Filled login with email: " + email + " and password " + password);
    }

    public String getPopupMessageLabelText() {
        search = SearchStrategy.setExplicitExistText();
        search.isLocatedCss(TopPart.POPUP_MESSAGE_CSSSELECTOR);
        search = SearchStrategy.restoreStrategy();

        List<WebElement> popupMessageLabel = search.cssSelectors(TopPart.POPUP_MESSAGE_CSSSELECTOR);
        if (popupMessageLabel.isEmpty()) {
            ReportUtils.logInfo("Popup message label text not found");
            return "";
        }

        String labelText = popupMessageLabel.get(0).getText();
        ReportUtils.logInfo("Getting popup message label text: " + labelText);
        return labelText;
    }

    public HomePage successfulLogin(String email, String password) {
        ReportUtils.logAction("Performing successful login with email: " + email);
        fillLogin(email, password);
        return new HomePage();
    }

    public LoginModal unsuccessfulLoginPage(String email, String password) {
        ReportUtils.logAction("Performing unsuccessful login with email: " + email);
        fillLogin(email, password);
        return new LoginModal();
    }
}
