package com.softserve.edu.teachua.pages.user;

import com.softserve.edu.teachua.pages.top.TopPart;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import static com.softserve.edu.teachua.data.TestData.BASE_URL;

public class LoggedDropdown extends TopPart {
    private WebElement addClubLink;
    private WebElement addCenterLink;
    private WebElement searchCertificatesLink;
    private WebElement personalProfileLink;
    private WebElement signOutLink;
    private static final By ADD_CLUB_MENU = By.cssSelector("li[data-menu-id*='add_club']");
    private static final By ADD_CENTRE_MENU = By.cssSelector("li[data-menu-id*='add_centre']");
    private static final By SEARCH_CERTIFICATES_MENU = By.cssSelector("li[data-menu-id*='search_certificates']");
    private static final By PROFILE_MENU = By.cssSelector("li[data-menu-id*='profile']");
    private static final By LOGOUT_MENU = By.cssSelector("li[data-menu-id*='logout']");

    public LoggedDropdown(WebDriver driver) {
        super(driver);
        this.driver = driver;
        initElements();
    }

    @Override
    protected void assertCurrentURL() {
        Assertions.assertEquals(BASE_URL, driver.getCurrentUrl());
    }

    private void initElements() {
        addClubLink = findElement(ADD_CLUB_MENU);
        addCenterLink = findElement(ADD_CENTRE_MENU);
        searchCertificatesLink = findElement(SEARCH_CERTIFICATES_MENU);
        personalProfileLink = findElement(PROFILE_MENU);
        signOutLink = findElement(LOGOUT_MENU);
    }

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
}
