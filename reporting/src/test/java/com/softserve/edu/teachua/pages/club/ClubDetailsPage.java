package com.softserve.edu.teachua.pages.club;

import com.softserve.edu.teachua.pages.top.TopPart;
import com.softserve.edu.teachua.tools.ReportUtils;
import org.openqa.selenium.WebElement;

public class ClubDetailsPage extends TopPart {

    private CommentsContainer commentsContainer;
    private WebElement createCommentButton;

    public ClubDetailsPage() {
        initElements();
    }

    private void initElements() {
        ReportUtils.logInfo("Initializing ClubDetailsPage elements.");
        commentsContainer = new CommentsContainer();
        createCommentButton = search.cssSelector("button.comment-button");
    }

    public CommentsContainer getCommentsContainer() {
        ReportUtils.logInfo("Getting comments container.");
        return commentsContainer;
    }

    public WebElement getCreateCommentButton() {
        ReportUtils.logInfo("Getting create comment button element.");
        return createCommentButton;
    }

    public String getCreateCommentButtonText() {
        String text = getCreateCommentButton().getText();
        ReportUtils.logInfo("Getting text from create comment button: " + text);
        return text;
    }

    public void clickCreateCommentButton() {
        ReportUtils.logAction("Clicking create comment button.");
        getCreateCommentButton().click();
    }

    public ClubCommentModal openClubCommentModal() {
        ReportUtils.logAction("Opening club comment modal by clicking create comment button.");
        clickCreateCommentButton();
        return new ClubCommentModal();
    }
}
