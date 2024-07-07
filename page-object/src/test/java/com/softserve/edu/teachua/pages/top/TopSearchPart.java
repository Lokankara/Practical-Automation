package com.softserve.edu.teachua.pages.top;

import com.softserve.edu.teachua.pages.club.AdvancedClubPage;
import com.softserve.edu.teachua.pages.club.ClubNotFoundPage;
import com.softserve.edu.teachua.pages.club.ClubPage;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public abstract class TopSearchPart extends TopPart {

    private WebElement searchInput;
    private WebElement advancedSearch;
    private static final By SEARCH_INPUT = By.cssSelector("input.ant-select-selection-search-input");
    private static final By ADVANCED_ICON = By.cssSelector(".anticon-control.advanced-icon");

    public TopSearchPart(WebDriver driver) {
        super(driver);
        initElements();
    }

    private void initElements() {
        searchInput = driver.findElement(SEARCH_INPUT);
        advancedSearch = driver.findElement(ADVANCED_ICON);
    }

    private WebElement getSearchInput() {
        return searchInput;
    }

    private WebElement getAdvancedSearch() {
        return advancedSearch;
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

    private void fillSearchInput(String searchText) {
        clickSearchInput();
        clearSearchInput();
        setSearchInput(searchText);
    }

    private void clickToAdvancedSearch() {
        getAdvancedSearch().click();
    }

    public ClubPage successfulSearchClub(String searchText) {
        fillSearchInput(searchText);
        return new ClubPage(driver);
    }

    public ClubNotFoundPage unsuccessfulSearchClub(String searchText) {
        fillSearchInput(searchText);
        return new ClubNotFoundPage(driver);
    }

    public ClubPage gotoAdvancedClubPage() {
        clickToAdvancedSearch();
        return new AdvancedClubPage(driver);
    }
}
