package com.softserve.edu.teachua.pages.club;

import com.softserve.edu.teachua.wraps.search.Search;
import com.softserve.edu.teachua.wraps.search.SearchStrategy;
import org.openqa.selenium.WebElement;

import java.util.List;

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
        // init elements
        titleLink = search.cssSelector("div.title", clubCard);
        categoriesLabel = search.cssSelectors("div.tags.club-tags span.name", clubCard);
        descriptionLabel = search.cssSelector(".description", clubCard); // "p.description" error for center
        addressLabel = search.cssSelector("div.address", clubCard);
        detailsButton = search.cssSelector("a.details-button > a", clubCard);
    }

    public WebElement getTitleLink() {
        return titleLink;
    }

    public String getTitleLinkText() {
        return getTitleLink().getText();
    }

    public void clickTitleLink() {
        getTitleLink().click();
    }

    public List<WebElement> getCategoriesLabel() {
        return categoriesLabel;
    }

    public List<String> getCategoriesLabelText() {
        return getCategoriesLabel().stream().map(WebElement::getText).toList();
    }

    public WebElement getDescriptionLabel() {
        return descriptionLabel;
    }

    public String getDescriptionLabelText() {
        return getDescriptionLabel().getText();
    }

    // addressLabel
    public WebElement getAddressLabel() {
        return addressLabel;
    }

    public String getAddressLabelText() {
        return getAddressLabel().getText();
    }

    public WebElement getDetailsButton() {
        return detailsButton;
    }

    public String getDetailsButtonText() {
        return getDetailsButton().getText();
    }

    public void clickDetailsButton() {
        getDetailsButton().click();
    }

    // Functional

    // Business Logic

    public ClubInfoModal openClubInfoModal() {
        clickTitleLink();
        return new ClubInfoModal();
    }

    public ClubDetailsPage openClubDetailsPage() {
        clickDetailsButton();
        return new ClubDetailsPage();
    }
}
