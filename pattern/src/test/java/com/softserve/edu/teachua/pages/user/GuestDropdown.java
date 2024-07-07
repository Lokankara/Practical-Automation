package com.softserve.edu.teachua.pages.user;

import com.softserve.edu.teachua.pages.top.BaseComponent;
import org.openqa.selenium.WebElement;

public class GuestDropdown extends BaseComponent {
    private WebElement register;
    private WebElement login;

    public GuestDropdown() {
        initElements();
    }

    private void initElements() {
        register = search.cssSelector("li[data-menu-id*='register'] span.ant-dropdown-menu-title-content");
        login = search.cssSelector("li[data-menu-id*='login'] span.ant-dropdown-menu-title-content");
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
        return new LoginModal();
    }
}
