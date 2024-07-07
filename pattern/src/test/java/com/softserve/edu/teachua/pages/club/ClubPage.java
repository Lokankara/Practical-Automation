package com.softserve.edu.teachua.pages.club;

import com.softserve.edu.teachua.exceptions.PaginationException;
import com.softserve.edu.teachua.pages.top.TopSearchPart;

public class ClubPage extends TopSearchPart {

    private ClubsContainer clubsContainer;

    public ClubPage() {
        super();
        initElements();
    }

    private void initElements() {
        clubsContainer = new ClubsContainer();
    }

    public ClubsContainer getClubContainer() {
        return clubsContainer;
    }
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
