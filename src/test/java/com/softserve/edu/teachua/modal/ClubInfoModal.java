package com.softserve.edu.teachua.modal;

import com.softserve.edu.teachua.part.BasePart;
import com.softserve.edu.teachua.page.club.ClubPage;
import com.softserve.edu.teachua.page.club.ClubDetailsPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class ClubInfoModal extends BasePart<ClubInfoModal> {
    private WebElement header;
    private WebElement closeIcon;
    private WebElement clubDetailsButton;

    public ClubInfoModal(WebDriver driver) {
        super(driver);
        initElements();
    }

    @Override
    protected ClubInfoModal self() {
        return this;
    }

    private void initElements() {
        clubDetailsButton = getElementBy(By.cssSelector("button.ant-btn-default.flooded-button.more-button"));
        closeIcon = getElementBy(By.cssSelector("span.ant-modal-close-icon"));
        header = getElementBy(By.cssSelector(".club-name"));
    }

    private WebElement getHeader() {
        return header;
    }

    private WebElement getCloseIcon() {
        return closeIcon;
    }

    private WebElement getClubDetailsButton() {
        return clubDetailsButton;
    }

    private void clickClubDetailsButton() {
        getClubDetailsButton().click();
    }

    private void closeIcon() {
        getCloseIcon().click();
    }

    public ClubPage closeClubInfoModal() {
        closeIcon();
        return new ClubPage(driver);
    }

    public ClubDetailsPage gotoClubDetailsPage() {
        clickClubDetailsButton();
        return new ClubDetailsPage(driver);
    }

    public ClubInfoModal checkInfoModalHeader(String searchText) {
        return checkHeader(searchText, getHeader());
    }
}
