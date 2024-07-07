package com.softserve.edu.teachua.pages.user;

import com.softserve.edu.teachua.pages.top.Waiter;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class GuestDropdown extends Waiter {
    private WebElement register;
    private WebElement login;
    private static final By LOGIN_DROPDOWN = By.cssSelector("li[data-menu-id*='login'] span.ant-dropdown-menu-title-content");
    private static final By REGISTER_DROPDOWN = By.cssSelector("li[data-menu-id*='register'] span.ant-dropdown-menu-title-content");

    public GuestDropdown(WebDriver driver) {
        super(driver);
        this.driver = driver;
        initElements();
    }

    private void initElements() {
        register = waitForVisibilityOfElement(REGISTER_DROPDOWN);
        login = waitForVisibilityOfElement(LOGIN_DROPDOWN);
    }

    public WebElement getRegister() {
        return register;
    }

    public String getRegisterText() {
        return getRegister().getText();
    }

    public void clickRegister() {
        getRegister().click();
    }

    public WebElement getLogin() {
        return login;
    }

    public String getLoginText() {
        return getLogin().getText();
    }

    public void clickLogin() {
        getLogin().click();
    }

    public LoginModal openLoginModal() {
        clickLogin();
        return new LoginModal(driver);
    }
}
