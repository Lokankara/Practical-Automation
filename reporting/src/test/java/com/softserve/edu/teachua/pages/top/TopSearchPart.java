package com.softserve.edu.teachua.pages.top;

import com.softserve.edu.teachua.pages.club.AdvancedClubPage;
import com.softserve.edu.teachua.pages.club.ClubNotFoundPage;
import com.softserve.edu.teachua.pages.club.ClubPage;
import com.softserve.edu.teachua.tools.ReportUtils;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;

public abstract class TopSearchPart extends TopPart {

    private WebElement searchInput;
    private WebElement advancedSearchButton;

    public TopSearchPart() {
        initElements();
    }

    private void initElements() {
        ReportUtils.logInfo("Initializing elements in TopSearchPart");
        searchInput = search.cssSelector("input.ant-select-selection-search-input");
        advancedSearchButton = search.cssSelector("span.advanced-icon[aria-label='control']");
    }

    public WebElement getSearchInput() {
        ReportUtils.logInfo("Retrieving search input element");
        return searchInput;
    }

    public String getSearchInputText() {
        String text = getSearchInput().getAttribute(TAG_ATTRIBUTE_VALUE);
        ReportUtils.logInfo("Retrieved search input text: " + text);
        return text;
    }

    public void clearSearchInput() {
        ReportUtils.logAction("Clearing search input");
        getSearchInput().clear();
    }

    public void clickSearchInput() {
        ReportUtils.logAction("Clicking search input");
        getSearchInput().click();
    }

    public void setSearchInput(String text) {
        ReportUtils.logAction("Setting search input to: " + text);
        getSearchInput().sendKeys(text + Keys.ARROW_RIGHT);
    }

    public WebElement getAdvancedSearchButton() {
        ReportUtils.logInfo("Retrieving advanced search button element");
        return advancedSearchButton;
    }

    public void clickAdvancedSearchButton() {
        ReportUtils.logAction("Clicking advanced search button");
        getAdvancedSearchButton().click();
    }

    private void fillSearchInput(String searchText) {
        ReportUtils.logAction("Filling search input with text: " + searchText);
        clickSearchInput();
        clearSearchInput();
        setSearchInput(searchText);
    }

    public ClubPage successfulSearchClub(String searchText) {
        ReportUtils.logInfo("Performing successful club search with text: " + searchText);
        fillSearchInput(searchText);
        return new ClubPage();
    }

    public ClubNotFoundPage unsuccessfulSearchClub(String searchText) {
        ReportUtils.logInfo("Performing unsuccessful club search with text: " + searchText);
        fillSearchInput(searchText);
        return new ClubNotFoundPage();
    }

    public AdvancedClubPage gotoAdvancedClubPage() {
        ReportUtils.logAction("Navigating to advanced club page");
        clickAdvancedSearchButton();
        return new AdvancedClubPage();
    }
}
