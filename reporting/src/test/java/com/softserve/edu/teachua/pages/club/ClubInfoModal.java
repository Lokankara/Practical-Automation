package com.softserve.edu.teachua.pages.club;

import com.softserve.edu.teachua.tools.ReportUtils;
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
        ReportUtils.logInfo("Initializing elements on QubStudioPage");
        ariaCloseButton = search.cssSelector("button[aria-label='Close']");
        clubTitleLabel = search.cssSelector("div.title div.club-name");
        clubMoreButton = search.cssSelector("button.more-button");
        clubDescriptionLabel = search.cssSelector("div.about-club div.description");
    }

    public WebElement getAriaCloseButton() {
        ReportUtils.logInfo("Getting Aria Close button element");
        return ariaCloseButton;
    }

    public void clickAriaCloseButton() {
        ReportUtils.logAction("Clicking on Aria Close button");
        getAriaCloseButton().click();
    }

    public WebElement getClubTitleLabel() {
        ReportUtils.logInfo("Getting Club Title label element");
        return clubTitleLabel;
    }

    public String getClubTitleLabelText() {
        String text = getClubTitleLabel().getText();
        ReportUtils.logInfo("Got Club Title label text: " + text);
        return text;
    }

    public WebElement getClubMoreButton() {
        ReportUtils.logInfo("Getting Club More button element");
        return clubMoreButton;
    }

    public String getClubMoreButtonText() {
        String text = getClubMoreButton().getText();
        ReportUtils.logInfo("Got Club More button text: " + text);
        return text;
    }

    public void clickClubMoreButton() {
        ReportUtils.logAction("Clicking on Club More button");
        getClubMoreButton().click();
    }

    public WebElement getClubDescriptionLabel() {
        ReportUtils.logInfo("Getting Club Description label element");
        return clubDescriptionLabel;
    }

    public String getClubDescriptionLabelText() {
        String text = getClubDescriptionLabel().getText();
        ReportUtils.logInfo("Got Club Description label text: " + text);
        return text;
    }

    public ClubPage closeClubInfoModal() {
        ReportUtils.logAction("Closing Club Info modal");
        clickAriaCloseButton();
        return new ClubPage();
    }

    public ClubDetailsPage gotoClubDetailsPage() {
        ReportUtils.logAction("Navigating to Club Details page");
        clickClubMoreButton();
        return new ClubDetailsPage();
    }
}
