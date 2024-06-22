package com.softserve.edu.teachua.component;

import com.softserve.edu.teachua.modal.LoginModal;
import com.softserve.edu.teachua.part.BasePart;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class GuestDropdown extends BasePart<GuestDropdown> {
    private WebElement register;
    private WebElement login;
    private final By dropdownMenuLogin = By.cssSelector("li[data-menu-id*='login'] span.ant-dropdown-menu-title-content");
    private final By dropdownMenuRegister = By.cssSelector("li[data-menu-id*='register'] span.ant-dropdown-menu-title-content");

    public GuestDropdown(WebDriver driver) {
        super(driver);
        initElements();
    }

    @Override
    protected GuestDropdown self() {
        return this;
    }

    private void initElements() {
        register = getVisibleElement(dropdownMenuRegister);
        login = getVisibleElement(dropdownMenuLogin);
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
