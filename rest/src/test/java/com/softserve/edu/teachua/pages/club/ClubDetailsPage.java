package com.softserve.edu.teachua.pages.club;

import com.softserve.edu.teachua.pages.top.TopPart;
import org.openqa.selenium.WebElement;

public class ClubDetailsPage extends TopPart {

    private CommentsContainer commentsContainer;
    //
    private WebElement createCommentButton;

    public ClubDetailsPage() {
        initElements();
    }

    private void initElements() {
        // init elements
        commentsContainer = new CommentsContainer();
        createCommentButton = search.cssSelector("button.comment-button");
    }

    // Page Object

    // commentsContainer
    public CommentsContainer getCommentsContainer() {
        return commentsContainer;
    }

    // createCommentButton
    public WebElement getCreateCommentButton() {
        return createCommentButton;
    }

    public String getCreateCommentButtonText() {
        return getCreateCommentButton().getText();
    }

    public void clickCreateCommentButton() {
        getCreateCommentButton().click();
    }

    // Functional

    // Business Logic

    public ClubCommentModal openClubCommentModal() {
        clickCreateCommentButton();
        return new ClubCommentModal();
    }
}
