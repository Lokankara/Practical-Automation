package com.softserve.edu.teachua.component;

import com.softserve.edu.teachua.modal.AddCenterModal;
import com.softserve.edu.teachua.modal.AddClubModal;
import com.softserve.edu.teachua.part.TopPart;
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
    private AddCenterModal addCenterModal;
    private AddClubModal addClubModal;

    public LoggedDropdown(WebDriver driver) {
        super(driver);
        this.driver = driver;
        initElements();
    }

    private void initElements() {
        addClubLink = driver.findElement(By.cssSelector("li[data-menu-id*='add_club']"));
        addCenterLink = driver.findElement(By.cssSelector("li[data-menu-id*='add_centre']"));
        searchCertificatesLink = driver.findElement(By.cssSelector("li[data-menu-id*='search_certificates']"));
        personalProfileLink = driver.findElement(By.cssSelector("li[data-menu-id*='profile']"));
        signOutLink = driver.findElement(By.cssSelector("li[data-menu-id*='logout']"));
    }

    public void clickAddClubLink() {
        addClubLink.click();
        addClubModal = new AddClubModal(driver);
    }

    public void clickAddCenterLink() {
        addCenterLink.click();
        addCenterModal = new AddCenterModal(driver);
    }

    public void clickSearchCertificatesLink() {
        searchCertificatesLink.click();
    }

    public void clickPersonalProfileLink() {
        personalProfileLink.click();
    }

    public void clickDropdownLoggedSignOut() {
        signOutLink.click();
    }

    public AddCenterModal getAddCenterModal() {
        return addCenterModal;
    }

    public AddClubModal getAddClubModal() {
        return addClubModal;
    }
}
