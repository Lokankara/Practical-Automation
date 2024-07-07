package com.softserve.edu.teachua.pages.club;

import com.softserve.edu.teachua.pages.top.TopSearchPart;
import com.softserve.edu.teachua.tools.ReportUtils;

public class ClubPage extends TopSearchPart {

    private ClubsContainer clubsContainer;

    public ClubPage() {
        initElements();
    }

    private void initElements() {
        ReportUtils.logInfo("Initializing elements on ClubPage");
        clubsContainer = new ClubsContainer();
    }

    public ClubsContainer getClubContainer() {
        ReportUtils.logAction("get Club Container");
        return clubsContainer;
    }

    public boolean isExistClubByPartialTitle(String partialTitle) {
        ReportUtils.logAction("Checking for club with partial title: " + partialTitle);
        boolean isFound = getClubContainer().isExistClubComponentByPartialTitle(partialTitle);
        int pageCount = 1;

        while (!isFound && getClubContainer().isEnableNextPageLink()) {
            pageCount++;
            ReportUtils.logInfo("Navigating to page " + pageCount + " for club search");
            getClubContainer().clickNextPageLink();
            initElements();
            isFound = getClubContainer().isExistClubComponentByPartialTitle(partialTitle);
        }

        ReportUtils.logInfo(isFound ? "Club with partial title '" + partialTitle + "' found on page " + pageCount : "Club with partial title '" + partialTitle + "' not found after searching through " + pageCount + " pages");

        return isFound;
    }

    public void clickPreviousClubPagination() {
        if (!getClubContainer().isEnablePreviousPageLink()) {
            ReportUtils.logError("The previous page is not available");
            throw new RuntimeException("The previous page is not available");
        }
        getClubContainer().clickPreviousPageLink();
        ReportUtils.logAction("Clicking previous club pagination");
    }

    public void clickNextClubPagination() {
        if (!getClubContainer().isEnableNextPageLink()) {
            ReportUtils.logError("The next page is not available");
            throw new RuntimeException("The next page is not available");
        }
        getClubContainer().clickNextPageLink();
        ReportUtils.logAction("Clicking next club pagination");
    }

    public ClubPage previousClubPagination() {
        clickPreviousClubPagination();
        ReportUtils.logAction("Navigating to previous club pagination");
        return new ClubPage();
    }

    public ClubPage nextClubPagination() {
        clickNextClubPagination();
        ReportUtils.logAction("Navigating to next club pagination");
        return new ClubPage();
    }

    public ClubPage chooseClubPaginationNumber(int numberPage) {
        getClubContainer().clickPageLinkByNumber(numberPage);
        ReportUtils.logAction("Choosing club pagination number: " + numberPage);
        return new ClubPage();
    }
}
