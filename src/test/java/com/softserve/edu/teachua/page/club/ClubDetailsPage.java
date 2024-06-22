package com.softserve.edu.teachua.page.club;

import com.softserve.edu.teachua.modal.ClubCommentModal;
import com.softserve.edu.teachua.part.BasePart;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class ClubDetailsPage extends BasePart<ClubDetailsPage> {
    private WebElement feedback;
    private WebElement lastComment;
    private WebElement commentButton;
    private WebElement headerElement;
    private List<WebElement> comments;
    private ClubCommentModal clubCommentModal;
    private static final int DEFAULT_COMMENTS_SIZE = 5;
    private static final By CLUB_NAME = By.cssSelector(".club-name");
    private static final By LIST_COMMENT = By.cssSelector(".ant-comment-inner .ant-comment-content-detail > p");
    private static final By COMMENT_BUTTON = By.cssSelector("button.comment-button");
    private static final By FEEDBACK_COUNTER = By.cssSelector(".page-rating > .feedback");
    private static final By LAST_COMMENT_CONTENT = By.xpath("(//div[@class='ant-comment-content-detail']/p)[position()=1]");
    private static final By AUTH_MESSAGE_ERROR = By.xpath("//div[@class='ant-message-custom-content ant-message-error']");

    public ClubDetailsPage(WebDriver driver) {
        super(driver);
        initElements();
    }

    @Override
    protected ClubDetailsPage self() {
        return this;
    }

    private void initElements() {
        feedback = getElementBy(FEEDBACK_COUNTER);
        lastComment = getVisibleElement(LAST_COMMENT_CONTENT);
        comments = getElementsBy(LIST_COMMENT);
        headerElement = getElementBy(CLUB_NAME);
        commentButton = getElementBy(COMMENT_BUTTON);
    }

    private WebElement getCommentButton() {
        return commentButton;
    }

    private WebElement getHeader() {
        return headerElement;
    }

    private List<WebElement> getComments() {
        return comments;
    }

    private WebElement getFeedback() {
        return feedback;
    }

    private WebElement getLastComment() {
        return lastComment;
    }

    private void clickCommentButton() {
        getCommentButton().click();
    }

    private String getLastCommentText() {
        return getLastComment().getText();
    }

    private ClubCommentModal getClubCommentModal() {
        return clubCommentModal;
    }

    private List<String> getCommentsText() {
        return comments.stream().map(WebElement::getText).toList();
    }

    private boolean isCommentFound(String commentText) {
        return getCommentsText().stream()
                .anyMatch(text -> text.contains(commentText));
    }

    public ClubDetailsPage checkLastCommentText(String commentText) {
        Assertions.assertEquals(commentText, getLastCommentText(), "Verify user comment is displayed correctly");
        return this;
    }

    public ClubCommentModal openCommentModal() {
        clickCommentButton();
        return createClubCommentModal();
    }

    public ClubDetailsPage tryOpenCommentModal() {
        clickCommentButton();
        return this;
    }


    private ClubCommentModal createClubCommentModal() {
        clubCommentModal = new ClubCommentModal(driver);
        return getClubCommentModal();
    }

    public ClubDetailsPage checkClubDetailsHeader(String searchText) {
        return checkHeader(searchText, getHeader());
    }

    public ClubDetailsPage checkLastComments(String commentText) {
        Assertions.assertFalse(getComments().isEmpty(), "Comments should be not empty");
        Assertions.assertTrue(isCommentFound(commentText), "Comment should be contains in the list: " + commentText + getCommentsText());
        return this;
    }

    private int countComments() {
        return Integer.parseInt(getFeedback().getText().split(" ")[0]);
    }

    public void assertErrorLoginMessage() {
        final String expectedMessage = "Увійдіть або зареєструйтеся!";
        Assertions.assertEquals(expectedMessage, getVisibleElement(AUTH_MESSAGE_ERROR).getText(), "Error Message text should be " + expectedMessage);
    }

    public ClubDetailsPage showMore(int next) {
        int size = countComments();
        int expected = next + DEFAULT_COMMENTS_SIZE;
        Assertions.assertTrue(expected <= size, "");
        driver.findElement(By.cssSelector(".show-more-p")).click();
        comments = getNumberToBeMoreThan(LIST_COMMENT, expected - 1);
        return this;
    }
}
