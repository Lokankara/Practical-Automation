package com.softserve.edu.teachua.pages.club;

import org.openqa.selenium.WebElement;

public class AdvancedClubPage extends ClubPage {

    private WebElement advancedSearchLabel;

    public AdvancedClubPage() {
        super();
        initElements();
    }

    private void initElements() {
        advancedSearchLabel = search.cssSelector("div.ant-layout-sider-children > div.club-list-label");
    }

    public WebElement getAdvancedSearchLabel() {
        return advancedSearchLabel;
    }

    public String getAdvancedSearchLabelText() {
        return getAdvancedSearchLabel().getText();
    }

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
