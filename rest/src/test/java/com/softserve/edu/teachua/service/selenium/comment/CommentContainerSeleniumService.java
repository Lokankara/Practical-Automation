package com.softserve.edu.teachua.service.selenium.comment;

import com.softserve.edu.teachua.data.Cities;
import com.softserve.edu.teachua.pages.club.CommentsContainer;
import com.softserve.edu.teachua.service.selenium.SeleniumService;

public class CommentContainerSeleniumService extends SeleniumService implements CommentSeleniumService {

    @Override
    public CommentsContainer findComments(String clubName) {
        return loadApplication()
                .gotoClubPage()
                .chooseCity(Cities.KYIV_CITY)
                .getClubContainer()
                .getClubComponentByPartialTitle(clubName)
                .openClubDetailsPage().getCommentsContainer();
    }
}
