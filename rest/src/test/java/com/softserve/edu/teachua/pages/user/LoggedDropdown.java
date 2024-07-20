package com.softserve.edu.teachua.pages.user;

import com.softserve.edu.teachua.wraps.search.Search;
import com.softserve.edu.teachua.wraps.search.SearchStrategy;
import org.openqa.selenium.WebElement;

public class LoggedDropdown {

    protected Search search;
    //
    private WebElement addClubLink;
    private WebElement addCenterLink;
    private WebElement searchCertificatesLink;
    private WebElement personalProfileLink;
    private WebElement signOutLink;

    public LoggedDropdown() {
        search = SearchStrategy.getSearch();
        initElements();
    }

    private void initElements() {
        addClubLink = search.cssSelector("li[data-menu-id*='club'] > span.ant-dropdown-menu-title-content");
        addCenterLink = search.cssSelector("li[data-menu-id*='centre'] > span");
        searchCertificatesLink = search.cssSelector("li[data-menu-id*='certificates'] > span");
        personalProfileLink = search.cssSelector("li[data-menu-id*='profile'] > span");
        signOutLink = search.cssSelector("li[data-menu-id*='logout'] > span");
    }

    // Page Object

    public WebElement getAddClubLink() {
        return addClubLink;
    }

    public String getAddClubLinkText() {
        return getAddClubLink().getText();
    }

    public void clickAddClubLink() {
        getAddClubLink().click();
    }

    public WebElement getAddCenterLink() {
        return addCenterLink;
    }

    public String getAddCenterLinkText() {
        return getAddCenterLink().getText();
    }

    public void clickAddCenterLink() {
        getAddCenterLink().click();
    }

    public WebElement getSearchCertificatesLink() {
        return searchCertificatesLink;
    }

    public String getSearchCertificatesLinkText() {
        return getSearchCertificatesLink().getText();
    }

    public void clickSearchCertificatesLink() {
        getSearchCertificatesLink().click();
    }

    public WebElement getPersonalProfileLink() {
        return personalProfileLink;
    }

    public String getPersonalProfileLinkText() {
        return getPersonalProfileLink().getText();
    }

    public void clickPersonalProfileLink() {
        getPersonalProfileLink().click();
    }

    public WebElement getSignOutLink() {
        return signOutLink;
    }

    public String getSignOutLinkText() {
        return getSignOutLink().getText();
    }

    public void clickSignOutLink() {
        getSignOutLink().click();
    }

    // Functional

    // Business Logic

}
