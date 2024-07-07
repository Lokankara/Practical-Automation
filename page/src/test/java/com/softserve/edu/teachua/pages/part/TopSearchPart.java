package com.softserve.edu.teachua.pages.part;

import com.softserve.edu.teachua.pages.page.club.ClubNotFoundPage;
import com.softserve.edu.teachua.pages.page.club.ClubPage;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public abstract class TopSearchPart extends TopPart {
    private WebElement searchInput;
    private static final By clubItem = By.cssSelector(".club-item");
    private static final By CARDS = By.cssSelector("div.ant-card.ant-card-bordered.card");
    private static final By SEARCH_INPUT = By.cssSelector("input.ant-select-selection-search-input");

    public TopSearchPart(WebDriver driver) {
        super(driver);
        initElements();
    }

    private void initElements() {
        searchInput = getElement(SEARCH_INPUT);
    }

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

    private void fillSearchInput(String searchText) {
        clickSearchInput();
        clearSearchInput();
        setSearchInput(searchText);
    }

    public ClubPage successfulSearchClub(String searchText) {
        fillSearchInput(searchText);
        getElementsToBeNot(getElements(CARDS).size(), clubItem);
        return new ClubPage(driver);
    }

    public ClubNotFoundPage unsuccessfulSearchClub(String searchText) {
        fillSearchInput(searchText);
        return new ClubNotFoundPage(driver);
    }
}
