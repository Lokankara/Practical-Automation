package com.softserve.edu.teachua.pages.club;

import com.softserve.edu.teachua.pages.top.TopSearchPart;
import org.openqa.selenium.WebElement;

public class ClubPageOld extends TopSearchPart {

    private WebElement clubTitleLink;
    private WebElement clubDescriptionLabel;
    private WebElement clubDetailsLink;

    public ClubPageOld() {
        super();
        initElements();
    }

    private void initElements() {
        clubTitleLink = search.cssSelector("div.ant-card.ant-card-bordered.card div.name");
        clubDescriptionLabel = search.cssSelector("p.description");
        clubDetailsLink = search.cssSelector("a.details-button > a");
    }

    public WebElement getClubTitleLink() {
        return clubTitleLink;
    }

    public String getClubTitleLinkText() {
        return getClubTitleLink().getText();
    }

    public void clickClubTitleLink() {
        getClubTitleLink().click();
    }

    public WebElement getClubDescriptionLabel() {
        return clubDescriptionLabel;
    }

    public String getClubDescriptionLabelText() {
        return getClubDescriptionLabel().getText();
    }

    public void clickClubDescriptionLabel() {
        getClubDescriptionLabel().click();
    }

    public WebElement getClubDetailsLink() {
        return clubDetailsLink;
    }

    public String getClubDetailsLinkText() {
        return getClubDetailsLink().getText();
    }

    public void clickClubDetailsLink() {
        getClubDetailsLink().click();
    }

    public ClubInfoBaseModal openClubInfoModal() {
        clickClubTitleLink();
        return new ClubInfoBaseModal();
    }

    public ClubDetailsPage gotoClubDetailsPage() {
        clickClubDetailsLink();
        return new ClubDetailsPage();
    }
}
