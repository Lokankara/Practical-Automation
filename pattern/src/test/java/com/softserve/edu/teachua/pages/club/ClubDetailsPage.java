package com.softserve.edu.teachua.pages.club;

import com.softserve.edu.teachua.pages.top.TopPart;
import org.openqa.selenium.WebElement;

public class ClubDetailsPage extends TopPart {

    private CommentsContainer commentsContainer;
    private WebElement createCommentButton;
    private WebElement authorContentLabel;
    private WebElement datetimeLabel;
    private WebElement commentContentLabel;

    public ClubDetailsPage() {
        super();
        initElements();
    }

    private void initElements() {
        commentsContainer = new CommentsContainer();
        createCommentButton = search.cssSelector("button.comment-button");
        authorContentLabel = search.cssSelector("div.ant-spin-container li:first-child div.author-content > span.name");
        datetimeLabel = search.cssSelector("div.ant-spin-container li:first-child div.author-content > span.datetime");
        commentContentLabel = search.cssSelector("div.ant-spin-container li:first-child div.ant-comment-content-detail > p");
    }

    public CommentsContainer getCommentsContainer() {
        return commentsContainer;
    }

    public WebElement getCreateCommentButton() {
        return createCommentButton;
    }

    public String getCreateCommentButtonText() {
        return getCreateCommentButton().getText();
    }

    public void clickCreateCommentButton() {
        getCreateCommentButton().click();
    }

    public ClubCommentModal openClubCommentModal() {
        clickCreateCommentButton();
        return new ClubCommentModal();
    }
}
