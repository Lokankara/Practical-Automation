package com.softserve.edu.teachua.pages.club;

import org.openqa.selenium.WebElement;

public class AdvancedClubPage extends ClubPage {

    private WebElement advancedSearchLabel;

    public AdvancedClubPage() {
        initElements();
    }

    private void initElements() {
        advancedSearchLabel = search.cssSelector("div.ant-layout-sider-children > div.club-list-label");
    }

    // Page Object

    // advancedSearchLabel
    public WebElement getAdvancedSearchLabel() {
        return advancedSearchLabel;
    }

    public String getAdvancedSearchLabelText() {
        return getAdvancedSearchLabel().getText();
    }

    // Functional

    // Business Logic

    @Override
    public AdvancedClubPage previousClubPagination() {
        clickPreviousClubPagination();
        return new AdvancedClubPage();
    }

    @Override
    public AdvancedClubPage nextClubPagination() {
        clickNextClubPagination();
        return new AdvancedClubPage();
    }
}
