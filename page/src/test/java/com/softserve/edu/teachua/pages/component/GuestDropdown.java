package com.softserve.edu.teachua.pages.component;

import com.softserve.edu.teachua.pages.modal.LoginModal;
import com.softserve.edu.teachua.pages.part.BasePart;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class GuestDropdown extends BasePart<GuestDropdown> {
    private WebElement register;
    private WebElement login;
    private static final By DROPDOWN_MENU_LOGIN = By.cssSelector("li[data-menu-id*='login'] span.ant-dropdown-menu-title-content");
    private static final By DROPDOWN_MENU_REGISTER = By.cssSelector("li[data-menu-id*='register'] span.ant-dropdown-menu-title-content");

    public GuestDropdown(WebDriver driver) {
        super(driver);
        initElements();
    }

    @Override
    protected GuestDropdown self() {
        return this;
    }

    private void initElements() {
        register = getVisibleElement(DROPDOWN_MENU_REGISTER);
        login = getVisibleElement(DROPDOWN_MENU_LOGIN);
    }

    public void clickDropdownGuestLogin() {
        clickLogin();
    }

    private WebElement getRegister() {
        return register;
    }

    private String getRegisterText() {
        return getRegister().getText();
    }

    public void clickRegister() {
        getRegister().click();
    }

    private WebElement getLogin() {
        return login;
    }

    private String getLoginText() {
        return getLogin().getText();
    }

    private void clickLogin() {
        getLogin().click();
    }

    public LoginModal openLoginModal() {
        clickLogin();
        return new LoginModal(driver);
    }
}
