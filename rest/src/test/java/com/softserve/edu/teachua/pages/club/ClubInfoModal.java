package com.softserve.edu.teachua.pages.club;

import com.softserve.edu.teachua.wraps.search.Search;
import com.softserve.edu.teachua.wraps.search.SearchStrategy;
import org.openqa.selenium.WebElement;

public class ClubInfoModal {

    protected Search search;

    private WebElement ariaCloseButton;
    private WebElement clubTitleLabel;
    private WebElement clubMoreButton;
    private WebElement clubDescriptionLabel;

    public ClubInfoModal() {
        search = SearchStrategy.getSearch();
        initElements();
    }

    private void initElements() {
        // init elements
        ariaCloseButton = search.cssSelector("button[aria-label='Close']");
        clubTitleLabel = search.cssSelector("div.title div.club-name");
        clubMoreButton = search.cssSelector("button.more-button");
        clubDescriptionLabel = search.cssSelector("div.about-club div.description");
    }

    // Page Object

    // ariaCloseButton
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

    // clubDescriptionLabel
    public WebElement getClubDescriptionLabel() {
        return clubDescriptionLabel;
    }

    public String getClubDescriptionLabelText() {
        return getClubDescriptionLabel().getText();
    }

    // Functional

    // Business Logic

    public ClubPage closeClubInfoModal() {
        clickAriaCloseButton();
        return new ClubPage();
    }

    // moreAboutClub
    public ClubDetailsPage gotoClubDetailsPage() {
        clickClubMoreButton();
        return new ClubDetailsPage();
    }

}
