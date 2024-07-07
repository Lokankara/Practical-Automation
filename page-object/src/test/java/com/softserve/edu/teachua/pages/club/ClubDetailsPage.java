package com.softserve.edu.teachua.pages.club;

import com.softserve.edu.teachua.data.ClubContents;
import com.softserve.edu.teachua.data.CommentContents;
import com.softserve.edu.teachua.pages.top.TopPart;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import static com.softserve.edu.teachua.data.TestData.BASE_URL;

public class ClubDetailsPage extends TopPart {
    private WebElement headerLabel;
    private WebElement commentButton;
    private ClubCommentModal clubCommentModal;
    private CommentsContainer commentsContainer;
    private static final By CLUB_NAME = By.cssSelector(".club-name");
    private static final By COMMENT_BUTTON = By.cssSelector("button.comment-button");

    public ClubDetailsPage(WebDriver driver) {
        super(driver);
        initElements();
    }

    @Override
    protected void assertCurrentURL() {
        Assertions.assertTrue(driver.getCurrentUrl().contains(BASE_URL));
    }

    private void initElements() {
        headerLabel = findElement(CLUB_NAME);
        commentsContainer = new CommentsContainer(driver);
    }

    public CommentsContainer getCommentContainer() {
        return commentsContainer;
    }

    private WebElement getCommentButton() {
        createCommentButton();
        return commentButton;
    }

    private void createCommentButton() {
        commentButton = findElement(COMMENT_BUTTON);
    }

    private WebElement getHeaderLabel() {
        return headerLabel;
    }

    private String getHeaderLabelText() {
        return getHeaderLabel().getText();
    }

    public void clickCommentButton() {
        getCommentButton().click();
    }

    private ClubCommentModal getClubCommentModal() {
        return clubCommentModal;
    }

    private ClubCommentModal createClubCommentModal() {
        clubCommentModal = new ClubCommentModal(driver);
        return getClubCommentModal();
    }

    public ClubDetailsPage checkLastCommentText(CommentContents content) {
        getCommentContainer().assertLastComments(content);
        return this;
    }

    public ClubCommentModal openClubCommentModal() {
        clickCommentButton();
        return createClubCommentModal();
    }

    public CommentComponent untilCommentIsFound(CommentContents commentContents) {
        return getCommentContainer().untilCommentIsFoundByPartialTitle(commentContents);
    }

    public ClubDetailsPage showMoreComments(int next) {
        getCommentContainer().showNextComments(next);
        return this;
    }

    public ClubDetailsPage assertDetailsHeader(ClubContents contents) {
        Assertions.assertEquals(getHeaderLabelText(), contents.getClub());
        return this;
    }

    public ClubDetailsPage assertInfoModalHeader(String clubName) {
        Assertions.assertNotNull(getHeaderLabel());
        Assertions.assertTrue(getHeaderLabel().isDisplayed());
        Assertions.assertNotNull(getHeaderLabelText());
        Assertions.assertTrue(getHeaderLabelText().contains(clubName),
                String.format("Should be %s, but was %s", getHeaderLabelText(), clubName));
        return this;
    }

    public void assertCommentComponent(CommentContents commentContents) {
        CommentComponent commentComponent = untilCommentIsFound(commentContents);
        Assertions.assertTrue(commentComponent.getCommentText().contains(commentContents.getText()),
                String.format("Should be %s, but was %s", commentComponent.getCommentText(), commentContents.getText()));
    }
}
