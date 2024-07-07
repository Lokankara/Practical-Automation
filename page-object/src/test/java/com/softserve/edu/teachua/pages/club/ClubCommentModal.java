package com.softserve.edu.teachua.pages.club;

import com.softserve.edu.teachua.pages.top.Waiter;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class ClubCommentModal extends Waiter {
    private WebElement commentField;
    private WebElement commentButton;
    private static final String EXPECTED_MESSAGE = "Увійдіть або зареєструйтеся!";
    private static final By COMMENT_FIELD = By.id("comment-edit_commentText");
    private static final By COMMENT_BUTTON = By.cssSelector("button.do-comment-button");
    private static final By LAST_STAR = By.xpath("(//*[@id='comment-edit_rate']//li[contains(@class, 'ant-rate-star')]/div)[last()]");
    private static final By AUTH_MESSAGE_ERROR = By.xpath("//div[@class='ant-message-custom-content ant-message-error']");

    public ClubCommentModal(WebDriver driver) {
        super(driver);
        initElements();
    }

    private void initElements() {
    }

    private WebElement getCommentField() {
        createCommentField();
        return commentField;
    }

    private void createCommentField() {
        commentField = driver.findElement(COMMENT_FIELD);
    }

    private WebElement getCommentButton() {
        createCommentButton();
        return commentButton;
    }

    private void createCommentButton() {
        commentButton = driver.findElement(COMMENT_BUTTON);
    }

    private void clickCommentButton() {
        getCommentButton().click();
    }

    private WebElement getLastStar() {
        return waitForElementToBeClickable(LAST_STAR);
    }

    private void clickLastStar() {
        getLastStar().click();
    }

    private void fillTextArea(String commentText) {
        getCommentField().clear();
        getCommentField().sendKeys(commentText);
    }

    public void assertErrorLoginMessage() {
        Assertions.assertEquals(EXPECTED_MESSAGE, waitForVisibilityOfElement(AUTH_MESSAGE_ERROR).getText(),
                "Error Message text should be " + EXPECTED_MESSAGE);
    }

    public ClubDetailsPage submitComment(String commentText) {
        fillTextArea(commentText);
        clickLastStar();
        clickCommentButton();
        return new ClubDetailsPage(driver);
    }
}
