package com.softserve.edu.teachua.pages.club;

import com.softserve.edu.teachua.tools.ReportUtils;
import com.softserve.edu.teachua.wraps.search.Search;
import com.softserve.edu.teachua.wraps.search.SearchStrategy;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

public class CommentsContainer {

    public final String COMMENT_NOT_FOUND = "There is no comments that matches the search criteria.";
    private static final String COMMENT_COMPONENT_CSSSELECTOR = "li div.ant-comment";
    protected Search search;
    private List<CommentComponent> commentComponents;
    private WebElement showMoreButton;

    public CommentsContainer() {
        ReportUtils.logInfo("Initialized elements in CommentsContainer");
        search = SearchStrategy.getSearch();
        initElements();
    }

    private void initElements() {
        commentComponents = new ArrayList<>();
        search.cssSelectors(COMMENT_COMPONENT_CSSSELECTOR).forEach(current -> commentComponents.add(new CommentComponent(current)));
        ReportUtils.logInfo("Added CommentComponents: " + commentComponents);
        if (!getCommentComponents().isEmpty()) {
            showMoreButton = search.cssSelector("button.show-more-button");
            ReportUtils.logInfo("Initialized show more button");
        }
    }

    public List<CommentComponent> getCommentComponents() {
        ReportUtils.logInfo("Retrieving comment components");
        return commentComponents;
    }

    public WebElement getShowMoreButton() {
        if (showMoreButton == null) {
            ReportUtils.logError(COMMENT_NOT_FOUND);
            throw new RuntimeException(COMMENT_NOT_FOUND);
        }
        ReportUtils.logInfo("Retrieving show more button");
        return showMoreButton;
    }

    public String getShowMoreButtonText() {
        String text = getShowMoreButton().getText();
        ReportUtils.logInfo("Retrieved show more button text: " + text);
        return text;
    }

    public void clickShowMoreButton() {
        ReportUtils.logAction("Clicking show more button");
        getShowMoreButton().click();
    }

    public int getCommentComponentsCount() {
        int count = getCommentComponents().size();
        ReportUtils.logInfo("Retrieved comment components count: " + count);
        return count;
    }

    public List<String> getClubComponentAuthors() {
        List<String> clubComponentAuthors = getCommentComponents().stream().map(CommentComponent::getAuthorLabelText).toList();
        ReportUtils.logInfo("Retrieved club component authors: " + clubComponentAuthors);
        return clubComponentAuthors;
    }

    public List<String> getClubComponentComments() {
        List<String> clubComponentComments = getCommentComponents().stream().map(CommentComponent::getDatetimeLabelText).toList();
        ReportUtils.logInfo("Retrieved club component comments: " + clubComponentComments);
        return clubComponentComments;
    }

    public CommentComponent getFirstCommentComponent() {
        if (getCommentComponentsCount() == 0) {
            ReportUtils.logError(COMMENT_NOT_FOUND);
            throw new RuntimeException(COMMENT_NOT_FOUND);
        }
        CommentComponent firstComponent = getCommentComponents().get(0);
        ReportUtils.logInfo("Retrieved first comment component: " + firstComponent);
        return firstComponent;
    }

    public CommentComponent getClubComponentByPartialAuthor(String partialAuthor) {
        CommentComponent result = getCommentComponents().stream()
                .filter(current -> current.getAuthorLabelText().toLowerCase().contains(partialAuthor.toLowerCase()))
                .findFirst()
                .orElse(null);
        if (result == null) {
            String errorMessage = "Comment partialAuthor: " + partialAuthor + " not Found.";
            ReportUtils.logError(errorMessage);
            throw new RuntimeException(errorMessage);
        }
        ReportUtils.logInfo("Retrieved comment component by partial author: " + result);
        return result;
    }

    public CommentComponent getClubComponentByDateTime(String dateTime) {
        CommentComponent result = getCommentComponents().stream()
                .filter(current -> current.getDatetimeLabelText().equalsIgnoreCase(dateTime))
                .findFirst()
                .orElse(null);
        if (result == null) {
            String errorMessage = "Comment dateTime: " + dateTime + " not Found.";
            ReportUtils.logError(errorMessage);
            throw new RuntimeException(errorMessage);
        }
        ReportUtils.logInfo("Retrieved comment component by date time: " + result);
        return result;
    }

    public CommentComponent getClubComponentByPartialDate(String partialDate) {
        CommentComponent result = getCommentComponents().stream()
                .filter(current -> current.getDatetimeLabelText().toLowerCase().contains(partialDate.toLowerCase()))
                .findFirst()
                .orElse(null);
        if (result == null) {
            String errorMessage = "Comment partialDate: " + partialDate + " not Found.";
            ReportUtils.logError(errorMessage);
            throw new RuntimeException(errorMessage);
        }
        ReportUtils.logInfo("Retrieved comment component by partial date: " + result);
        return result;
    }

    public boolean isExistClubComponentByPartialAuthor(String partialAuthor) {
        boolean isFound = getClubComponentAuthors().stream()
                .anyMatch(current -> current.toLowerCase().contains(partialAuthor.toLowerCase()));
        ReportUtils.logInfo("Existence check for club component by partial author: " + partialAuthor + " - " + isFound);
        return isFound;
    }
}
