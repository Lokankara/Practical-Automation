package com.softserve.edu.teachua.pages.club;

import com.softserve.edu.teachua.data.ClubContents;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class ClubInfoModal extends ClubDetailsPage {
    private WebElement closeIcon;
    private WebElement headerLabel;
    private WebElement descriptionLabel;
    private WebElement clubDetailsButton;
    private static final By CLUB_NAME = By.cssSelector(".club-name");
    private static final By DESCRIPTION = By.cssSelector(".description");
    private static final By CLOSE_ICON = By.cssSelector("span.ant-modal-close-icon");
    private static final By MORE_BUTTON = By.cssSelector("button.ant-btn-default.flooded-button.more-button");

    public ClubInfoModal(WebDriver driver) {
        super(driver);
        initElements();
    }

    private void initElements() {
        clubDetailsButton = findElement(MORE_BUTTON);
        descriptionLabel = findElement(DESCRIPTION);
        closeIcon = findElement(CLOSE_ICON);
        headerLabel = findElement(CLUB_NAME);
    }

    private WebElement getDescriptionLabel() {
        return descriptionLabel;
    }

    private WebElement getHeaderLabel() {
        return headerLabel;
    }

    private String getDescriptionText() {
        return getDescriptionLabel().getText();
    }

    private String getHeaderText() {
        return getHeaderLabel().getText();
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

    public ClubInfoModal assertHeaderText() {
        Assertions.assertNotNull(getHeaderLabel(), "Header label is null");
        Assertions.assertTrue(getHeaderLabel().isDisplayed(),
                String.format("Header label '%s' is not displayed", getHeaderLabel().getText()));
        Assertions.assertNotNull(getHeaderLabel().getText(), "Header label text is null");
        return this;
    }

    public ClubInfoModal assertClubContent(ClubContents contents) {
        Assertions.assertEquals(contents.getClub(), getHeaderText(),
                "Club link text does not match expected value");
        Assertions.assertTrue(getDescriptionText().contains(contents.getDescriptions()),
                String.format("Description text does not match expected value %s, but was %s", getDescriptionText(), contents.getDescriptions()));
        return this;
    }
}
