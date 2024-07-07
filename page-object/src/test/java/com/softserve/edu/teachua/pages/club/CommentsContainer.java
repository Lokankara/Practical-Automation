package com.softserve.edu.teachua.pages.club;

import com.softserve.edu.teachua.data.CommentContents;
import com.softserve.edu.teachua.exception.ComponentNotFoundException;
import com.softserve.edu.teachua.pages.top.Waiter;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class CommentsContainer extends Waiter {

    private WebElement feedback;
    private WebElement showMoreButton;
    private List<WebElement> comments;
    private List<CommentComponent> commentComponents;
    private static final int DEFAULT_COMMENTS_SIZE = 5;
    public final String COMMENT_NOT_FOUND = "There is no comments that matches the search criteria: ";
    private static final By SHOW_MORE = By.cssSelector(".show-more-p");
    private static final By FEEDBACK_COUNTER = By.cssSelector(".page-rating > .feedback");
    private static final By LIST_COMMENT = By.xpath("//div[@class='ant-comment-content-detail']/p");

    public CommentsContainer(WebDriver driver) {
        super(driver);
        initElements();
    }

    private void initElements() {
        comments = fetchListComments();
        feedback = driver.findElement(FEEDBACK_COUNTER);
        this.commentComponents = initializeCommentComponents();
    }

    private List<CommentComponent> initializeCommentComponents() {
        return fetchListComments().stream()
                .map(element -> new CommentComponent(driver, element))
                .toList();
    }

    private List<WebElement> fetchListComments() {
        comments = driver.findElements(LIST_COMMENT);
        return getComments();
    }

    private List<CommentComponent> getCommentComponents() {
        return commentComponents;
    }

    private WebElement getFeedback() {
        return feedback;
    }

    protected List<WebElement> getComments() {
        return comments;
    }

    private WebElement getShowMoreButton() {
        return showMoreButton;
    }

    public List<String> getCommentsText() {
        return getComments().stream().map(WebElement::getText).toList();
    }

    private boolean isCommentFound(String commentText) {
        return getCommentComponents()
                .stream()
                .anyMatch(comment -> comment.getCommentText().contains(commentText));
    }

    private void showMoreComments(int next) {
        int clicks = next / DEFAULT_COMMENTS_SIZE;
        for (int i = 0; i < clicks; i++) {
            clickShowMoreButton();
            comments = waitUntilExpectedSize(LIST_COMMENT, DEFAULT_COMMENTS_SIZE * (i + 2));
            initializeCommentComponents();
        }
    }

    private void checkSizeComments(int next) {
        int size = countCommentsFromFeedback();
        int expected = next + DEFAULT_COMMENTS_SIZE;
        Assertions.assertTrue(expected <= size,
                "Expected more comments than currently available");
    }

    public void showNextComments(int next) {
        checkSizeComments(next);
        showMoreButton = driver.findElement(SHOW_MORE);
        scrollToElement(getShowMoreButton());
        showMoreComments(next);
    }

    public void clickShowMoreButton() {
        getShowMoreButton().click();
    }

    public void assertLastComments(CommentContents comment) {
        Assertions.assertFalse(getComments().isEmpty(), "Comments should be not empty");
        Assertions.assertTrue(isCommentFound(comment.getText()),
                "Comment should be contains in the list: " + comment.getText() + getCommentsText());
    }

    public CommentComponent untilCommentIsFoundByPartialTitle(CommentContents commentContents) {
        while (!isCommentFound(commentContents.getText())) {
            if (!isShowMoreButtonPresent()) {
                throw new ComponentNotFoundException(COMMENT_NOT_FOUND, commentContents.getText());
            }
            showMoreComments(calculateExpectedComments(countCommentsFromFeedback()));
            checkSizeComments(countCommentsFromFeedback());
            fetchListComments();
            this.commentComponents = initializeCommentComponents();
        }
        return getCommentComponentByPartialTitle(commentContents.getText());
    }

    private CommentComponent getCommentComponentByPartialTitle(String partialTitle) {
        return getCommentComponents().stream()
                .filter(comment -> comment.getCommentText().contains(partialTitle))
                .findFirst()
                .orElseThrow(() -> new ComponentNotFoundException(COMMENT_NOT_FOUND, partialTitle));
    }

    private int countCommentsFromFeedback() {
        return Integer.parseInt(getFeedback().getText().split(" ")[0]);
    }

    private int calculateExpectedComments(int next) {
        return next + DEFAULT_COMMENTS_SIZE;
    }

    private boolean isShowMoreButtonPresent() {
        return !driver.findElements(SHOW_MORE).isEmpty();
    }
}
