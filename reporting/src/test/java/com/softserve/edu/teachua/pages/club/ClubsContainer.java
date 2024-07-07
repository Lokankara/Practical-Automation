package com.softserve.edu.teachua.pages.club;

import com.softserve.edu.teachua.pages.top.TopPart;
import com.softserve.edu.teachua.tools.ReportUtils;
import com.softserve.edu.teachua.wraps.search.Search;
import com.softserve.edu.teachua.wraps.search.SearchStrategy;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            ReportUtils.logError("Thread sleep interrupted: " + e.getMessage());
            throw new RuntimeException(e);
        }

        clubComponents = new ArrayList<>();
        search.cssSelectors(CLUBS_COMPONENT_CSSSELECTOR).forEach(current -> clubComponents.add(new ClubComponent(current)));

        if (clubComponents.isEmpty()) {
            ReportUtils.logError(CLUBS_NOT_FOUND);
            throw new RuntimeException(CLUBS_NOT_FOUND);
        }
        previousItem = search.cssSelector("li[title='Previous Page']");
        nextItem = search.cssSelector("li[title='Next Page']");
        previousPageLink = search.cssSelector("li[title='Previous Page'] > button");
        nextPageLink = search.cssSelector("li[title='Next Page'] > button");
    }


    public List<ClubComponent> getClubComponents() {
        ReportUtils.logInfo("Getting club components");
        return clubComponents;
    }

    public WebElement getPreviousItem() {
        ReportUtils.logInfo("Getting previous item");
        return previousItem;
    }

    public String getPreviousItemClassAttribute() {
        ReportUtils.logInfo("Getting class attribute of previous item");
        return getPreviousItem().getAttribute(TopPart.TAG_ATTRIBUTE_CLASS);
    }

    public boolean isEnablePreviousPageLink() {
        ReportUtils.logInfo("Checking if previous page link is enabled");
        return !getPreviousItemClassAttribute().contains("pagination-disabled");
    }

    public WebElement getNextItem() {
        ReportUtils.logInfo("Getting next item");
        return nextItem;
    }

    public String getNextItemClassAttribute() {
        String attribute = getNextItem().getAttribute(TopPart.TAG_ATTRIBUTE_CLASS);
        ReportUtils.logInfo("Getting class attribute of next item: " + attribute);
        return attribute;
    }

    public boolean isEnableNextPageLink() {
        ReportUtils.logInfo("Checking if next page link is enabled");
        return !getNextItemClassAttribute().contains("pagination-disabled");
    }

    public WebElement getPreviousPageLink() {
        ReportUtils.logInfo("Getting previous page link");
        return previousPageLink;
    }

    public String getPreviousPageLinkText() {
        String text = getPreviousPageLink().getText();
        ReportUtils.logInfo("Getting text of previous page link: " + text);
        return text;
    }

    public void clickPreviousPageLink() {
        ReportUtils.logAction("Clicking on previous page link");
        getPreviousPageLink().click();
    }

    public WebElement getNextPageLink() {
        ReportUtils.logInfo("Getting next page link");
        return nextPageLink;
    }

    public String getNextPageLinkText() {
        String text = getNextPageLink().getText();
        ReportUtils.logInfo("Getting text of next page link: " + text);
        return text;
    }

    public void clickNextPageLink() {
        ReportUtils.logAction("Clicking on next page link");
        getNextPageLink().click();
    }

    public int getClubComponentsCount() {
        int size = getClubComponents().size();
        ReportUtils.logInfo("Getting count of club components: " + size);
        return size;
    }

    public List<String> getClubComponentTitles() {
        List<String> clubComponentNames = getClubComponents().stream().map(ClubComponent::getTitleLinkText).collect(Collectors.toList());
        ReportUtils.logInfo("Retrieved club component titles: " + clubComponentNames);
        return clubComponentNames;
    }

    public ClubComponent getFirstClubComponent() {
        if (getClubComponentsCount() == 0) {
            ReportUtils.logError("No club components found");
            throw new RuntimeException("Clubs not found");
        }
        ReportUtils.logInfo("Retrieved first club component");
        return getClubComponents().get(0);
    }

    public ClubComponent getClubComponentByTitle(String clubTitle) {
        ClubComponent result = getClubComponents().stream().filter(current -> current.getTitleLinkText().equalsIgnoreCase(clubTitle)).findFirst().orElse(null);
        if (result == null) {
            ReportUtils.logError("Club title not found: " + clubTitle);
            throw new RuntimeException("ClubTitle: " + clubTitle + " not found");
        }
        ReportUtils.logInfo("Found club component by title: " + clubTitle);
        return result;
    }

    public ClubComponent getClubComponentByPartialTitle(String partialTitle) {
        ClubComponent result = getClubComponents().stream().filter(current -> current.getTitleLinkText().toLowerCase()
                .contains(partialTitle.toLowerCase())).findFirst().orElse(null);
        if (result == null) {
            ReportUtils.logError("Club partial title not found: " + partialTitle);
            throw new RuntimeException("Club partialTitle: " + partialTitle + " not Found.");
        }
        ReportUtils.logAction("Found club component by partial title: " + partialTitle);
        return result;
    }

    public boolean isExistClubComponentByPartialTitle(String partialTitle) {
        boolean isFound = getClubComponentTitles().stream().anyMatch(
                current -> current.toLowerCase().contains(partialTitle.toLowerCase()));
        ReportUtils.logInfo(!isFound ? "Club partial title not found: " + partialTitle
                : "Found club component by partial title: " + partialTitle);
        return isFound;
    }

    public void clickPageLinkByNumber(int numberPage) {
        List<WebElement> paginationNumbers = search.xpaths(String.format(PAGINATION_NUMBERS_XPATHSELECTOR, numberPage));
        if (!paginationNumbers.isEmpty()) {
            paginationNumbers.get(0).click();
            ReportUtils.logAction("Clicked pagination link by number: " + numberPage);
            return;
        }
        ReportUtils.logError("Pagination number not found: " + numberPage);
        throw new RuntimeException("Pagination Number: " + numberPage + " not found");
    }
}

