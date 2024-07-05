package com.softserve.edu.teachua.pages.club;

import com.softserve.edu.teachua.tools.ReportUtils;
import com.softserve.edu.teachua.wraps.search.Search;
import com.softserve.edu.teachua.wraps.search.SearchStrategy;
import org.openqa.selenium.WebElement;

public class ClubCommentModal {

    private final Search search;
    private WebElement rateStar5Link;
    private WebElement typeCommentArea;
    private WebElement sendCommentButton;

    public ClubCommentModal() {
        search = SearchStrategy.getSearch();
        initElements();
    }

    private void initElements() {
        ReportUtils.logInfo("Initializing ClubCommentModal elements.");
        rateStar5Link = search.cssSelector("div.ant-form-item div[aria-posinset='5']");
        ReportUtils.logInfo("Initialized rateStar5Link with CSS selector");
        typeCommentArea = search.id("comment-edit_commentText");
        ReportUtils.logInfo("Initialized typeCommentArea with ID: comment-edit_commentText");
        sendCommentButton = search.cssSelector("button.do-comment-button");
        ReportUtils.logInfo("Initialized sendCommentButton with CSS selector");
    }

    public WebElement getRateStar5Link() {
        ReportUtils.logInfo("Getting rateStar5Link element");
        return rateStar5Link;
    }

    public void clickRateStar5Link() {
        ReportUtils.logAction("Clicking rateStar5Link element");
        getRateStar5Link().click();
    }

    public WebElement getTypeCommentArea() {
        ReportUtils.logInfo("Getting typeCommentArea element");
        return typeCommentArea;
    }

    public String getTypeCommentAreaText() {
        ReportUtils.logInfo("Getting text of typeCommentArea element");
        return getTypeCommentArea().getText();
    }

    public void clearTypeCommentArea() {
        ReportUtils.logAction("Clearing typeCommentArea element");
        getTypeCommentArea().clear();
    }

    public void clickTypeCommentArea() {
        ReportUtils.logAction("Clicking typeCommentArea element");
        getTypeCommentArea().click();
    }

    public void setTypeCommentArea(String commentText) {
        ReportUtils.logAction("Setting text in typeCommentArea element: " + commentText);
        getTypeCommentArea().sendKeys(commentText);
    }

    public WebElement getSendCommentButton() {
        ReportUtils.logInfo("Getting sendCommentButton element");
        return sendCommentButton;
    }

    public String getSendCommentButtonText() {
        String text = getSendCommentButton().getText();
        ReportUtils.logInfo("Getting text of sendCommentButton element: " + text);
        return text;
    }

    public void clickSendCommentButton() {
        ReportUtils.logAction("Clicking sendCommentButton element");
        getSendCommentButton().click();
    }

    private void enterCommentArea(String commentText) {
        ReportUtils.logAction("Entering comment area with text: " + commentText);
        clickTypeCommentArea();
        clearTypeCommentArea();
        setTypeCommentArea(commentText);
    }

    public void acceptComment(String commentText) {
        ReportUtils.logAction("Accepting comment with text: " + commentText);
        clickRateStar5Link();
        enterCommentArea(commentText);
        clickSendCommentButton();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            ReportUtils.logError(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public ClubDetailsPage submitComment(String commentText) {
        ReportUtils.logAction("Submitting comment with text: " + commentText);
        acceptComment(commentText);
        return new ClubDetailsPage();
    }
}
