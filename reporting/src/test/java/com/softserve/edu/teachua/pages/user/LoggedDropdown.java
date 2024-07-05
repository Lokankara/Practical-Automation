package com.softserve.edu.teachua.pages.user;

import com.softserve.edu.teachua.tools.ReportUtils;
import com.softserve.edu.teachua.wraps.search.Search;
import com.softserve.edu.teachua.wraps.search.SearchStrategy;
import org.openqa.selenium.WebElement;

public class LoggedDropdown {

    protected Search search;
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
        ReportUtils.logInfo("Initializing elements in LoggedDropdown");

        addClubLink = search.cssSelector("li[data-menu-id*='club'] > span.ant-dropdown-menu-title-content");
        addCenterLink = search.cssSelector("li[data-menu-id*='centre'] > span");
        searchCertificatesLink = search.cssSelector("li[data-menu-id*='certificates'] > span");
        personalProfileLink = search.cssSelector("li[data-menu-id*='profile'] > span");
        signOutLink = search.cssSelector("li[data-menu-id*='logout'] > span");
    }

    public WebElement getAddClubLink() {
        ReportUtils.logInfo("Retrieving addClubLink");
        return addClubLink;
    }

    public String getAddClubLinkText() {
        String text = getAddClubLink().getText();
        ReportUtils.logInfo("Retrieved addClubLink text: " + text);
        return text;
    }

    public void clickAddClubLink() {
        ReportUtils.logAction("Clicking addClubLink");
        getAddClubLink().click();
    }

    public WebElement getAddCenterLink() {
        ReportUtils.logInfo("Retrieving addCenterLink");
        return addCenterLink;
    }

    public String getAddCenterLinkText() {
        String text = getAddCenterLink().getText();
        ReportUtils.logInfo("Retrieved addCenterLink text: " + text);
        return text;
    }

    public void clickAddCenterLink() {
        ReportUtils.logAction("Clicking addCenterLink");
        getAddCenterLink().click();
    }

    public WebElement getSearchCertificatesLink() {
        ReportUtils.logInfo("Retrieving searchCertificatesLink");
        return searchCertificatesLink;
    }

    public String getSearchCertificatesLinkText() {
        String text = getSearchCertificatesLink().getText();
        ReportUtils.logInfo("Retrieved searchCertificatesLink text: " + text);
        return text;
    }

    public void clickSearchCertificatesLink() {
        ReportUtils.logAction("Clicking searchCertificatesLink");
        getSearchCertificatesLink().click();
    }

    public WebElement getPersonalProfileLink() {
        ReportUtils.logInfo("Retrieving personalProfileLink");
        return personalProfileLink;
    }

    public String getPersonalProfileLinkText() {
        String text = getPersonalProfileLink().getText();
        ReportUtils.logInfo("Retrieved personalProfileLink text: " + text);
        return text;
    }

    public void clickPersonalProfileLink() {
        ReportUtils.logAction("Clicking personalProfileLink");
        getPersonalProfileLink().click();
    }

    public WebElement getSignOutLink() {
        ReportUtils.logInfo("Retrieving signOutLink");
        return signOutLink;
    }

    public String getSignOutLinkText() {
        String text = getSignOutLink().getText();
        ReportUtils.logInfo("Retrieved signOutLink text: " + text);
        return text;
    }

    public void clickSignOutLink() {
        ReportUtils.logAction("Clicking signOutLink");
        getSignOutLink().click();
    }
}
