package com.softserve.edu.teachua.pages.modal;

import com.softserve.edu.teachua.pages.part.BasePart;
import com.softserve.edu.teachua.pages.page.club.ClubPage;
import com.softserve.edu.teachua.pages.page.club.ClubDetailsPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class ClubInfoModal extends BasePart<ClubInfoModal> {
    private WebElement header;
    private WebElement closeIcon;
    private WebElement clubDetailsButton;
    private static final By CLUB_NAME = By.cssSelector(".club-name");
    private static final By CLOSE_ICON = By.cssSelector("span.ant-modal-close-icon");
    private static final By MORE_BUTTON = By.cssSelector("button.ant-btn-default.flooded-button.more-button");

    public ClubInfoModal(WebDriver driver) {
        super(driver);
        initElements();
    }

    @Override
    protected ClubInfoModal self() {
        return this;
    }

    private void initElements() {
        clubDetailsButton = getElement(MORE_BUTTON);
        closeIcon = getElement(CLOSE_ICON);
        header = getElement(CLUB_NAME);
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
