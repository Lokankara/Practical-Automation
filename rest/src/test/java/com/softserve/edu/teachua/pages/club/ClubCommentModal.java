package com.softserve.edu.teachua.pages.club;

import com.softserve.edu.teachua.wraps.search.Search;
import com.softserve.edu.teachua.wraps.search.SearchStrategy;
import org.openqa.selenium.WebElement;

public class ClubCommentModal {

    protected Search search;

    private WebElement rateStar5Link;
    private WebElement typeCommentArea;
    private WebElement sendCommentButton;

    public ClubCommentModal() {
        search = SearchStrategy.getSearch();
        initElements();
    }

    private void initElements() {
        // init elements
        rateStar5Link = search.cssSelector("div.ant-form-item div[aria-posinset='5']");
        typeCommentArea = search.id("comment-edit_commentText");
        sendCommentButton = search.cssSelector("button.do-comment-button");
    }

    // Page Object

    // rateStar5Link
    public WebElement getRateStar5Link() {
        return rateStar5Link;
    }

    public void clickRateStar5Link() {
        getRateStar5Link().click();
    }

    // typeCommentArea
    public WebElement getTypeCommentArea() {
        return typeCommentArea;
    }

    public String getTypeCommentAreaText() {
        return getTypeCommentArea().getText();
    }

    public void clearTypeCommentArea() {
        getTypeCommentArea().clear();
    }

    public void clickTypeCommentArea() {
        getTypeCommentArea().click();
    }

    public void setTypeCommentArea(String commentText) {
        getTypeCommentArea().sendKeys(commentText);
    }

    // sendCommentButton
    public WebElement getSendCommentButton() {
        return sendCommentButton;
    }

    public String getSendCommentButtonText() {
        return getSendCommentButton().getText();
    }

    public void clickSendCommentButton() {
        getSendCommentButton().click();
    }

    // Functional

    private void enterCommentArea(String commentText) {
        clickTypeCommentArea();
        clearTypeCommentArea();
        setTypeCommentArea(commentText);
    }

    public void acceptComment(String commentText) {
        clickRateStar5Link();
        enterCommentArea(commentText);
        clickSendCommentButton();
        //
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        //
    }

    // Business Logic

    public ClubDetailsPage submitComment(String commentText) {
        acceptComment(commentText);
        return new ClubDetailsPage();
    }
}
