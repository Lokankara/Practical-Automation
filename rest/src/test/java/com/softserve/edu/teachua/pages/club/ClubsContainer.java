package com.softserve.edu.teachua.pages.club;

import com.softserve.edu.teachua.exception.ComponentNotFoundException;
import com.softserve.edu.teachua.exception.PaginationException;
import com.softserve.edu.teachua.pages.top.TopPart;
import com.softserve.edu.teachua.wraps.search.Search;
import com.softserve.edu.teachua.wraps.search.SearchStrategy;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

public class ClubsContainer {

    public final String CLUBS_NOT_FOUND = "There is no club that matches the search criteria.";
    private static final String CLUBS_COMPONENT_CSSSELECTOR = "div.ant-card.ant-card-bordered";
    private static final String PAGINATION_NUMBERS_XPATHSELECTOR = "//li[contains(@class,'ant-pagination-item')]/a[text()='%d']";

    protected Search search;
    private List<ClubComponent> clubComponents;
    private WebElement previousItem;
    private WebElement nextItem;
    private WebElement previousPageLink;
    private WebElement nextPageLink;

    public ClubsContainer() {
        search = SearchStrategy.getSearch();
        initElements();
    }

    private void initElements() {
        search = SearchStrategy.setImplicitStrategy();
        if (search.isLocatedCss(CLUBS_COMPONENT_CSSSELECTOR)) {
            String firstText = search
                    .cssSelectors("div.ant-card.ant-card-bordered.card div.name").get(0).getText();
            search = SearchStrategy.restoreStrategy();
            search = SearchStrategy.setExplicitInvisibily();
            search.isInvisibilyCss("div.ant-card.ant-card-bordered.card div.name", firstText);
        }
        search = SearchStrategy.restoreStrategy();
        //
        // init elements
        clubComponents = new ArrayList<>();
        for (WebElement current : search.cssSelectors(CLUBS_COMPONENT_CSSSELECTOR)) {
            // DriverUtils.scrollToElement(current); // Optional
            clubComponents.add(new ClubComponent(current));
        }
        if (clubComponents.isEmpty()) {
            throw new RuntimeException(CLUBS_NOT_FOUND);
        }
        previousItem = search.cssSelector("li[title='Previous Page']");
        nextItem = search.cssSelector("li[title='Next Page']");
        previousPageLink = search.cssSelector("li[title='Previous Page'] > button");
        nextPageLink = search.cssSelector("li[title='Next Page'] > button");
    }

    // Page Object

    // clubComponents
    public List<ClubComponent> getClubComponents() {
        return clubComponents;
    }

    // previousItem

    public WebElement getPreviousItem() {
        return previousItem;
    }

    public String getPreviousItemClassAttribute() {
        return getPreviousItem().getAttribute(TopPart.TAG_ATTRIBUTE_CLASS);
    }

    public boolean isEnablePreviousPageLink() {
        return !getPreviousItemClassAttribute().contains("pagination-disabled");
    }

    // nextItem
    public WebElement getNextItem() {
        return nextItem;
    }

    public String getNextItemClassAttribute() {
        return getNextItem().getAttribute(TopPart.TAG_ATTRIBUTE_CLASS);
    }

    public boolean isEnableNextPageLink() {
        return !getNextItemClassAttribute().contains("pagination-disabled");
    }

    // previousPageLink
    public WebElement getPreviousPageLink() {
        return previousPageLink;
    }

    public String getPreviousPageLinkText() {
        return getPreviousPageLink().getText();
    }

    public void clickPreviousPageLink() {
        getPreviousPageLink().click();
    }

    // nextPageLink
    public WebElement getNextPageLink() {
        return nextPageLink;
    }

    public String getNextPageLinkText() {
        return getNextPageLink().getText();
    }

    public void clickNextPageLink() {
        getNextPageLink().click();
    }

    // Functional

    public int getClubComponentsCount() {
        return getClubComponents().size();
    }

    public List<String> getClubComponentTitles() {
        return getClubComponents().stream().map(ClubComponent::getTitleLinkText).toList();

    }

    public ClubComponent getFirstClubComponent() {
        if (getClubComponentsCount() == 0) {
            throw new ComponentNotFoundException(CLUBS_NOT_FOUND);
        }
        return getClubComponents().get(0);
    }

    public ClubComponent getClubComponentByTitle(String clubTitle) {
        return getClubComponents().stream().filter(current -> current.getTitleLinkText()
                .equalsIgnoreCase(clubTitle)).findFirst().orElseThrow(() -> new ComponentNotFoundException("ClubTitle: " + clubTitle + " not Found."));
    }

    public ClubComponent getClubComponentByPartialTitle(String partialTitle) {
        return getClubComponents().stream().filter(current -> current.getTitleLinkText().toLowerCase()
                .contains(partialTitle.toLowerCase())).findFirst().orElseThrow(() -> new ComponentNotFoundException("Club partialTitle: " + partialTitle + " not Found."));
    }

    public boolean isExistClubComponentByPartialTitle(String partialTitle) {
        return getClubComponentTitles().stream().anyMatch(current -> current.toLowerCase().contains(partialTitle.toLowerCase()));
    }

    public void clickPageLinkByNumber(int numberPage) {
        List<WebElement> paginationNumbers = search
                .xpaths(String.format(PAGINATION_NUMBERS_XPATHSELECTOR, numberPage));
        if (!paginationNumbers.isEmpty()) {
            paginationNumbers.get(0).click();
        }
        throw new PaginationException("Pagination Number: " + numberPage + " not Found.");
    }
}
