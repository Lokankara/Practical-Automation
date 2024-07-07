package com.softserve.edu.teachua.pages.page.club;

import com.softserve.edu.teachua.pages.part.TopSearchPart;
import com.softserve.edu.teachua.pages.modal.ClubInfoModal;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class ClubPage extends TopSearchPart {
    private WebElement clubTitleLink;
    private WebElement clubDetailsLink;
    private WebElement clubDescriptionLabel;
    private static final By DESCRIPTION = By.cssSelector("p.description");
    private static final By DETAILS = By.cssSelector("a.details-button > a");
    private static final By CARD = By.cssSelector("div.ant-card.ant-card-bordered.card div.name");

    public ClubPage(WebDriver driver) {
        super(driver);
        initElements();
    }

    private void initElements() {
        clubTitleLink = getElement(CARD);
        clubDetailsLink = getElement(DETAILS);
        clubDescriptionLabel = getElement(DESCRIPTION);
    }

    protected WebElement getClubTitleLink() { return clubTitleLink; }

    public String getClubTitleLinkText() {
        return getClubTitleLink().getText();
    }

    private void clickClubTitleLink() { getClubTitleLink().click(); }

    private WebElement getClubDescriptionLabel() {
        return clubDescriptionLabel;
    }

    private String getClubDescriptionLabelText() {
        return getClubDescriptionLabel().getText();
    }

    private void clickClubDescriptionLabel() {
        getClubDescriptionLabel().click();
    }

    private WebElement getClubDetailsLink() {
        return clubDetailsLink;
    }

    private String getClubDetailsLinkText() {
        return getClubDetailsLink().getText();
    }

    private void clickClubDetailsLink() {
        getClubDetailsLink().click();
    }

    public ClubInfoModal openClubInfoModal() {
        clickClubTitleLink();
        return new ClubInfoModal(driver);
    }

    public ClubDetailsPage gotoClubDetailsPage() {
        clickClubDetailsLink();
        return new ClubDetailsPage(driver);
    }
}
