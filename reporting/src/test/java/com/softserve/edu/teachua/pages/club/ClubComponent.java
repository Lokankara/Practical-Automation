package com.softserve.edu.teachua.pages.club;

import com.softserve.edu.teachua.tools.ReportUtils;
import com.softserve.edu.teachua.wraps.search.Search;
import com.softserve.edu.teachua.wraps.search.SearchStrategy;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.stream.Collectors;

public class ClubComponent {

    protected Search search;
    private WebElement clubCard;
    private WebElement titleLink;
    private List<WebElement> categoriesLabel;
    private WebElement descriptionLabel;
    private WebElement addressLabel;
    private WebElement detailsButton;

    public ClubComponent(WebElement clubCard) {
        this.clubCard = clubCard;
        search = SearchStrategy.getSearch();
        initElements();
    }

    private void initElements() {
        ReportUtils.logInfo("Initializing club component elements.");
        titleLink = search.cssSelector("div.title", clubCard);
        categoriesLabel = search.cssSelectors("div.tags.club-tags span.name", clubCard);
        descriptionLabel = search.cssSelector("p.description", clubCard);
        addressLabel = search.cssSelector("div.address", clubCard);
        detailsButton = search.cssSelector("a.details-button > a", clubCard);
    }

    public WebElement getTitleLink() {
        ReportUtils.logInfo("Getting title link element.");
        return titleLink;
    }

    public String getTitleLinkText() {
        ReportUtils.logInfo("Getting text from title link.");
        return getTitleLink().getText();
    }

    public void clickTitleLink() {
        ReportUtils.logAction("Clicking on title link.");
        getTitleLink().click();
    }

    public List<WebElement> getCategoriesLabel() {
        ReportUtils.logInfo("Getting categories labels.");
        return categoriesLabel;
    }

    public List<String> getCategoriesLabelText() {
        List<String> categoriesNames = getCategoriesLabel().stream().map(WebElement::getText).collect(Collectors.toList());
        ReportUtils.logInfo("Getting text from categories labels: " + categoriesNames);
        return categoriesNames;
    }

    public WebElement getDescriptionLabel() {
        ReportUtils.logInfo("Getting description label element.");
        return descriptionLabel;
    }

    public String getDescriptionLabelText() {
        String text = getDescriptionLabel().getText();
        ReportUtils.logInfo("Getting text from description label: " + text);
        return text;
    }

    public WebElement getAddressLabel() {
        ReportUtils.logInfo("Getting address label element.");
        return addressLabel;
    }

    public String getAddressLabelText() {
        String text = getAddressLabel().getText();
        ReportUtils.logInfo("Getting text from address label: " + text);
        return text;
    }

    public WebElement getDetailsButton() {
        ReportUtils.logInfo("Getting details button element.");
        return detailsButton;
    }

    public String getDetailsButtonText() {
        String text = getDetailsButton().getText();
        ReportUtils.logInfo("Getting text from details button" + text);
        return text;
    }

    public void clickDetailsButton() {
        ReportUtils.logAction("Clicking on details button.");
        getDetailsButton().click();
    }

    public ClubInfoModal openClubInfoModal() {
        ReportUtils.logAction("Opening club info modal by clicking title link.");
        clickTitleLink();
        return new ClubInfoModal();
    }

    public ClubDetailsPage openClubDetailsPage() {
        ReportUtils.logAction("Opening club details page by clicking details button.");
        clickDetailsButton();
        return new ClubDetailsPage();
    }
}
