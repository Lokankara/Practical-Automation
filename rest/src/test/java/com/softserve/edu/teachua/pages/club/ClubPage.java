package com.softserve.edu.teachua.pages.club;

import com.softserve.edu.teachua.exception.PaginationException;
import com.softserve.edu.teachua.pages.top.TopSearchPart;

public class ClubPage extends TopSearchPart {

    private ClubsContainer clubsContainer;

    public ClubPage() {
        initElements();
    }

    private void initElements() {
        // init elements
        clubsContainer = new ClubsContainer();
    }

    // Page Object

    // clubsContainer
    public ClubsContainer getClubContainer() {
        return clubsContainer;
    }

    // Functional

    public boolean isExistClubByPartialTitle(String partialTitle) {
        boolean isFound = getClubContainer().isExistClubComponentByPartialTitle(partialTitle);
        while (!isFound && getClubContainer().isEnableNextPageLink()) {
            getClubContainer().clickNextPageLink();
            initElements();
            isFound = getClubContainer().isExistClubComponentByPartialTitle(partialTitle);
        }
        return isFound;
    }

    public void clickPreviousClubPagination() {
        if (!getClubContainer().isEnablePreviousPageLink()) {
            throw new PaginationException("The previous page is not available");
        }
        getClubContainer().clickPreviousPageLink();
    }

    public void clickNextClubPagination() {
        if (!getClubContainer().isEnableNextPageLink()) {
            throw new PaginationException("The next page is not available");
        }
        getClubContainer().clickNextPageLink();
    }

    // Business Logic

    public ClubPage previousClubPagination() {
        clickPreviousClubPagination();
        return new ClubPage();
    }

    public ClubPage nextClubPagination() {
        clickNextClubPagination();
        return new ClubPage();
    }

    public ClubPage chooseClubPaginationNumber(int numberPage) {
        getClubContainer().clickPageLinkByNumber(numberPage);
        return new ClubPage();
    }
}
