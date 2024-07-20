package com.softserve.edu.teachua.service.selenium.comment;

import com.softserve.edu.teachua.pages.club.CommentsContainer;

public interface CommentSeleniumService {
    CommentsContainer findComments(String clubName);
}
