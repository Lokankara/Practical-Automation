package com.softserve.edu.teachua.pages.top;

import com.softserve.edu.teachua.pages.club.AdvancedClubPage;
import com.softserve.edu.teachua.pages.club.ClubNotFoundPage;
import com.softserve.edu.teachua.pages.club.ClubPage;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;

public abstract class TopSearchPart extends TopPart {

    private WebElement searchInput;
    private WebElement advancedSearchButton;

    public TopSearchPart() {
        initElements();
    }

    private void initElements() {
        // init elements
        searchInput = search.cssSelector("input.ant-select-selection-search-input");
        advancedSearchButton = search.cssSelector("span.advanced-icon[aria-label='control']");
    }

    // Page Object

    // searchInput
    public WebElement getSearchInput() {
        return searchInput;
    }

    public String getSearchInputText() {
        return getSearchInput().getAttribute(TAG_ATTRIBUTE_VALUE);
    }

    public void clearSearchInput() {
        getSearchInput().clear();
    }

    public void clickSearchInput() {
        getSearchInput().click();
    }

    public void setSearchInput(String text) {
        getSearchInput().sendKeys(text + Keys.ARROW_RIGHT);
    }

    // advancedSearchButton
    public WebElement getAdvancedSearchButton() {
        return advancedSearchButton;
    }

    public void clickAdvancedSearchButton() {
        getAdvancedSearchButton().click();
    }

    // Functional

    // searchInput
    private void fillSearchInput(String searchText) {
        clickSearchInput();
        clearSearchInput();
        setSearchInput(searchText);
    }

    // Business Logic

    public ClubPage successfulSearchClub(String searchText) {
        fillSearchInput(searchText);
        return new ClubPage();
    }

    public ClubNotFoundPage unsuccessfulSearchClub(String searchText) {
        fillSearchInput(searchText);
        return new ClubNotFoundPage();
    }

    public AdvancedClubPage gotoAdvancedClubPage() {
        clickAdvancedSearchButton();
        return new AdvancedClubPage();
    }
}
