package com.softserve.edu.teachua.pages.user;

import com.softserve.edu.teachua.wraps.search.Search;
import com.softserve.edu.teachua.wraps.search.SearchStrategy;
import org.openqa.selenium.WebElement;

public class GuestDropdown {

    protected Search search;
    //
    private WebElement register;
    private WebElement login;

    public GuestDropdown() {
        search = SearchStrategy.getSearch();
        initElements();
    }

    private void initElements() {
        search = SearchStrategy.setExplicitExistText();
        search.isLocatedCss("li[data-menu-id*='login'] span.ant-dropdown-menu-title-content");
        search = SearchStrategy.restoreStrategy();
        //
        // init elements
        register = search.cssSelector("li[data-menu-id*='register'] span.ant-dropdown-menu-title-content");
        login = search.cssSelector("li[data-menu-id*='login'] span.ant-dropdown-menu-title-content");
    }

    // Page Object

    // register
    public WebElement getRegister() {
        return register;
    }

    public String getRegisterText() {
        return getRegister().getText();
    }

    public void clickRegister() {
        getRegister().click();
    }

    // login
    public WebElement getLogin() {
        return login;
    }

    public String getLoginText() {
        return getLogin().getText();
    }

    public void clickLogin() {
        getLogin().click();
    }

    // Functional

    // Business Logic

    public LoginModal openLoginModal() {
        clickLogin();
        return new LoginModal();
    }
}
