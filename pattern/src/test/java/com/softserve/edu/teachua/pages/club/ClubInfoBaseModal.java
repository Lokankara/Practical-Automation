package com.softserve.edu.teachua.pages.club;

import com.softserve.edu.teachua.pages.top.BaseModal;
import org.openqa.selenium.WebElement;

public class ClubInfoBaseModal extends BaseModal {

    private WebElement ariaCloseButton;
    private WebElement clubTitleLabel;
    private WebElement clubMoreButton;
    private WebElement clubDescriptionLabel;

    public ClubInfoBaseModal() {
        initElements();
    }

    private void initElements() {
        ariaCloseButton = search.cssSelector("button[aria-label='Close']");
        clubTitleLabel = search.cssSelector("div.title div.club-name");
        clubMoreButton = search.cssSelector("button.more-button");
        clubDescriptionLabel = search.cssSelector("div.about-club div.description");
    }

    public WebElement getAriaCloseButton() {
        return ariaCloseButton;
    }

    public void clickAriaCloseButton() {
        getAriaCloseButton().click();
    }

    public WebElement getClubTitleLabel() {
        return clubTitleLabel;
    }

    public String getClubTitleLabelText() {
        return getClubTitleLabel().getText();
    }

    public WebElement getClubMoreButton() {
        return clubMoreButton;
    }

    public String getClubMoreButtonText() {
        return getClubMoreButton().getText();
    }

    public void clickClubMoreButton() {
        getClubMoreButton().click();
    }

    public WebElement getClubDescriptionLabel() {
        return clubDescriptionLabel;
    }

    public String getClubDescriptionLabelText() {
        return getClubDescriptionLabel().getText();
    }

    public ClubPage closeClubInfoModal() {
        clickAriaCloseButton();
        return new ClubPage();
    }

    public ClubDetailsPage gotoClubDetailsPage() {
        clickClubMoreButton();
        return new ClubDetailsPage();
    }
}
