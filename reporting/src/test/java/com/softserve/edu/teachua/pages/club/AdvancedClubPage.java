package com.softserve.edu.teachua.pages.club;

import com.softserve.edu.teachua.tools.ReportUtils;
import org.openqa.selenium.WebElement;

public class AdvancedClubPage extends ClubPage {

    private WebElement advancedSearchLabel;

    public AdvancedClubPage() {
        initElements();
    }

    private void initElements() {
        ReportUtils.logInfo("Initializing AdvancedClubPage elements.");
        advancedSearchLabel = search.cssSelector("div.ant-layout-sider-children > div.club-list-label");
        ReportUtils.logInfo("Initialized advancedSearchLabel with CSS selector");
    }

    public WebElement getAdvancedSearchLabel() {
        ReportUtils.logInfo("Getting advancedSearchLabel element");
        return advancedSearchLabel;
    }

    public String getAdvancedSearchLabelText() {
        String text = getAdvancedSearchLabel().getText();
        ReportUtils.logInfo("Getting text of advancedSearchLabel element: " + text);
        return text;
    }

    @Override
    public AdvancedClubPage previousClubPagination() {
        ReportUtils.logAction("Navigating to the previous club page");
        clickPreviousClubPagination();
        return new AdvancedClubPage();
    }

    @Override
    public AdvancedClubPage nextClubPagination() {
        ReportUtils.logAction("Navigating to the next club page");
        clickNextClubPagination();
        return new AdvancedClubPage();
    }
}
