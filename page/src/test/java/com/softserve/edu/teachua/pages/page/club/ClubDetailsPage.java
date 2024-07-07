package com.softserve.edu.teachua.pages.page.club;

import com.softserve.edu.teachua.pages.modal.ClubCommentModal;
import com.softserve.edu.teachua.pages.part.BasePart;
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
    private WebElement showMoreButton;
    private List<WebElement> comments;
    private ClubCommentModal clubCommentModal;
    private static final int DEFAULT_COMMENTS_SIZE = 5;
    private static final String EXPECTED_MESSAGE = "Увійдіть або зареєструйтеся!";
    private static final By CLUB_NAME = By.cssSelector(".club-name");
    private static final By SHOW_MORE = By.cssSelector(".show-more-p");
    private static final By COMMENT_BUTTON = By.cssSelector("button.comment-button");
    private static final By FEEDBACK_COUNTER = By.cssSelector(".page-rating > .feedback");
    private static final By LIST_COMMENT = By.cssSelector(".ant-comment-inner .ant-comment-content-detail > p");
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
        feedback = getElement(FEEDBACK_COUNTER);
        comments = getElements(LIST_COMMENT);
        headerElement = getElement(CLUB_NAME);
        commentButton = getElement(COMMENT_BUTTON);
    }

    private WebElement getComment() {return lastComment;}

    private WebElement getShowMoreButton() {return showMoreButton;}

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
        lastComment = getVisibleElement(LAST_COMMENT_CONTENT);
        return getComment();
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
        return getComments().stream().map(WebElement::getText).toList();
    }

    private boolean isCommentFound(String commentText) {
        return getCommentsText()
                .stream()
                .anyMatch(text -> text.contains(commentText));
    }

    private ClubCommentModal createClubCommentModal() {
        clubCommentModal = new ClubCommentModal(driver);
        return getClubCommentModal();
    }

    private int countComments() {
        return Integer.parseInt(getFeedback().getText().split(" ")[0]);
    }

    private void showMoreComments(int next) {
        int clicks = next / DEFAULT_COMMENTS_SIZE;
        for (int i = 0; i < clicks; i++) {
            getShowMoreButton().click();
            comments = getCommentsUntilExpectedSize(LIST_COMMENT, DEFAULT_COMMENTS_SIZE * (i + 1));
        }
    }

    private void checkSizeComments(int next) {
        int size = countComments();
        int expected = next + DEFAULT_COMMENTS_SIZE;
        Assertions.assertTrue(expected <= size,
                "Expected more comments than currently available");
    }

    public ClubDetailsPage checkLastCommentText(String commentText) {
        Assertions.assertEquals(commentText, getLastCommentText(),
                "Verify user comment is displayed correctly");
        return this;
    }

    public ClubCommentModal openCommentModal() {
        clickCommentButton();
        return createClubCommentModal();
    }

    public ClubDetailsPage openClubDetailsPage() {
        clickCommentButton();
        return this;
    }

    public ClubDetailsPage assertClubDetailsHeader(String searchText) {
        return checkHeader(searchText, getHeader());
    }

    public ClubDetailsPage assertLastComments(String commentText) {
        Assertions.assertFalse(getComments().isEmpty(), "Comments should be not empty");
        Assertions.assertTrue(isCommentFound(commentText),
                "Comment should be contains in the list: " + commentText + getCommentsText());
        return this;
    }

    public void assertErrorLoginMessage() {
        Assertions.assertEquals(EXPECTED_MESSAGE, getVisibleElement(AUTH_MESSAGE_ERROR).getText(),
                "Error Message text should be " + EXPECTED_MESSAGE);
    }

    public ClubDetailsPage showNextComments(int next) {
        checkSizeComments(next);
        showMoreButton = getElement(SHOW_MORE);
        scrollToElement(getShowMoreButton());
        showMoreComments(next);
        return this;
    }
}
