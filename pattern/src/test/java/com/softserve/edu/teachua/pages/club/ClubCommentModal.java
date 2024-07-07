package com.softserve.edu.teachua.pages.club;

import com.softserve.edu.teachua.pages.top.BaseModal;
import org.openqa.selenium.WebElement;

public class ClubCommentModal extends BaseModal {

    private WebElement rateStar5Link;
    private WebElement typeCommentArea;
    private WebElement sendCommentButton;

    public ClubCommentModal() {
        super();
        initElements();
    }

    private void initElements() {
        rateStar5Link = search.cssSelector("div.ant-form-item div[aria-posinset='5']");
        typeCommentArea = search.id("comment-edit_commentText");
        sendCommentButton = search.cssSelector("button.do-comment-button");
    }

    public WebElement getRateStar5Link() {
        return rateStar5Link;
    }

    public void clickRateStar5Link() {
        getRateStar5Link().click();
    }

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

    public WebElement getSendCommentButton() {
        return sendCommentButton;
    }

    public String getSendCommentButtonText() {
        return getSendCommentButton().getText();
    }

    public void clickSendCommentButton() {
        getSendCommentButton().click();
    }

    private void enterCommentArea(String commentText) {
        clickTypeCommentArea();
        clearTypeCommentArea();
        setTypeCommentArea(commentText);
    }

    public void acceptComment(String commentText) {
        clickRateStar5Link();
        enterCommentArea(commentText);
        clickSendCommentButton();
    }

    public ClubDetailsPage submitComment(String commentText) {
        acceptComment(commentText);
        return new ClubDetailsPage();
    }
}
