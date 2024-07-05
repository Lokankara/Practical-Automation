package com.softserve.edu.teachua.pages.user;

import com.softserve.edu.teachua.tools.ReportUtils;
import com.softserve.edu.teachua.wraps.search.Search;
import com.softserve.edu.teachua.wraps.search.SearchStrategy;
import org.openqa.selenium.WebElement;

public class GuestDropdown {

    protected Search search;
    private WebElement register;
    private WebElement login;

    public GuestDropdown() {
        search = SearchStrategy.getSearch();
        initElements();
    }

    private void initElements() {
        ReportUtils.logInfo("Initializing elements in GuestDropdown");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            ReportUtils.logError(e.getMessage());
            throw new RuntimeException(e);
        }
        register = search.cssSelector("li[data-menu-id*='register'] span.ant-dropdown-menu-title-content");
        login = search.cssSelector("li[data-menu-id*='login'] span.ant-dropdown-menu-title-content");
    }

    public WebElement getRegister() {
        ReportUtils.logInfo("Retrieving register element");
        return register;
    }

    public String getRegisterText() {
        String text = getRegister().getText();
        ReportUtils.logInfo("Retrieved register text: " + text);
        return text;
    }

    public void clickRegister() {
        ReportUtils.logAction("Clicking register element");
        getRegister().click();
    }

    public WebElement getLogin() {
        ReportUtils.logInfo("Retrieving login element");
        return login;
    }

    public String getLoginText() {
        String text = getLogin().getText();
        ReportUtils.logInfo("Retrieved login text: " + text);
        return text;
    }

    public void clickLogin() {
        ReportUtils.logAction("Clicking login element");
        getLogin().click();
    }

    public LoginModal openLoginModal() {
        ReportUtils.logAction("Opening login modal");
        clickLogin();
        return new LoginModal();
    }
}
