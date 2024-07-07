package com.softserve.edu.teachua.pages.modal;

import com.softserve.edu.teachua.pages.part.BasePart;
import com.softserve.edu.teachua.pages.page.club.ClubDetailsPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class ClubCommentModal extends BasePart<ClubCommentModal> {
    private WebElement field;
    private WebElement commentButton;
    private static final By COMMENT_FIELD = By.id("comment-edit_commentText");
    private static final By COMMENT_BUTTON = By.cssSelector("button.do-comment-button");
    private static final By LAST_STAR = By.xpath("(//*[@id='comment-edit_rate']//li[contains(@class, 'ant-rate-star')]/div)[last()]");

    public ClubCommentModal(WebDriver driver) {
        super(driver);
        initElements();
    }

    @Override
    protected ClubCommentModal self() {
        return this;
    }

    private void initElements() {
        field = getElement(COMMENT_FIELD);
        commentButton = getElement(COMMENT_BUTTON);
    }

    private WebElement getField() {return field;}

    private WebElement getCommentButton() {return commentButton;}

    private void clickCommentButton() {
        getCommentButton().click();
    }

    private WebElement getLastStar() {
        return getClickableElement(LAST_STAR);
    }

    private void clickLastStar() {
        getLastStar().click();
    }

    private void fillTextArea(String commentText) {
        getField().clear();
        getField().sendKeys(commentText);
    }

    public ClubDetailsPage submitComment(String commentText) {
        fillTextArea(commentText);
        clickLastStar();
        clickCommentButton();
        return new ClubDetailsPage(driver);
    }
}
