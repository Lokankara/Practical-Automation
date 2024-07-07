package com.softserve.edu.teachua.pages.component;

import com.softserve.edu.teachua.pages.modal.AddCenterModal;
import com.softserve.edu.teachua.pages.modal.AddClubModal;
import com.softserve.edu.teachua.pages.part.TopPart;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class LoggedDropdown extends TopPart {

    private final WebDriver driver;
    private WebElement addClubLink;
    private WebElement addCenterLink;
    private WebElement searchCertificatesLink;
    private WebElement personalProfileLink;
    private WebElement signOutLink;
    private AddClubModal addClubModal;
    private AddCenterModal addCenterModal;
    private static final By addClubMenu = By.cssSelector("li[data-menu-id*='add_club']");
    private static final By addCentreMenu = By.cssSelector("li[data-menu-id*='add_centre']");
    private static final By searchCertificatesMenu = By.cssSelector("li[data-menu-id*='search_certificates']");
    private static final By profileMenu = By.cssSelector("li[data-menu-id*='profile']");
    private static final By logoutMenu = By.cssSelector("li[data-menu-id*='logout']");

    public LoggedDropdown(WebDriver driver) {
        super(driver);
        this.driver = driver;
        initElements();
    }

    private void initElements() {
        addClubLink = getElement(addClubMenu);
        addCenterLink = getElement(addCentreMenu);
        searchCertificatesLink = getElement(searchCertificatesMenu);
        personalProfileLink = getElement(profileMenu);
        signOutLink = getElement(logoutMenu);
    }

    public void clickAddCenterLink() {
        clickAddCenter();
        addCenterModal = new AddCenterModal(driver);
    }

    private void clickAddCenter() {
        addCenterLink.click();
    }

    private void clickAddClub() {
        addClubLink.click();
    }

    private WebElement getSearchCertificatesLink() {
        return searchCertificatesLink;
    }

    private WebElement getSignOutLink() {
        return signOutLink;
    }

    private WebElement getPersonalProfileLink() {
        return personalProfileLink;
    }

    public void clickSearchCertificatesLink() {
        getSearchCertificatesLink().click();
    }

    public void clickPersonalProfileLink() {
        getPersonalProfileLink().click();
    }

    public void clickDropdownLoggedSignOut() {
        getSignOutLink().click();
    }

    public AddCenterModal getAddCenterModal() {
        return addCenterModal;
    }

    public AddClubModal getAddClubModal() {
        return addClubModal;
    }

    public void clickAddClubLink() {
        clickAddClub();
        addClubModal = new AddClubModal(driver);
    }
}
