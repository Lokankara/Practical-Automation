package com.softserve.edu.teachua.pages.club;

import com.softserve.edu.teachua.exception.ComponentNotFoundException;
import com.softserve.edu.teachua.wraps.search.Search;
import com.softserve.edu.teachua.wraps.search.SearchStrategy;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

public class CommentsContainer {

    public static final String COMMENT_NOT_FOUND = "There is no comments that matches the search criteria.";
    private static final String COMMENT_COMPONENT_CSSSELECTOR = "li div.ant-comment";
    //
    protected Search search;
    //
    private List<CommentComponent> commentComponents;
    private WebElement showMoreButton;

    public CommentsContainer() {
        search = SearchStrategy.getSearch();
        initElements();
    }

    private void initElements() {
        // init elements
        commentComponents = new ArrayList<>();
        for (WebElement current : search.cssSelectors(COMMENT_COMPONENT_CSSSELECTOR)) {
            commentComponents.add(new CommentComponent(current));
        }
        if (!getCommentComponents().isEmpty()) {
            showMoreButton = search.cssSelector("button.show-more-button");
        }
    }

    // Page Object

    // commentComponents
    public List<CommentComponent> getCommentComponents() {
        return commentComponents;
    }

    // showMoreButton
    public WebElement getShowMoreButton() {
        if (showMoreButton == null) {
            throw new ComponentNotFoundException(COMMENT_NOT_FOUND);
        }
        return showMoreButton;
    }

    public String getShowMoreButtonText() {
        return getShowMoreButton().getText();
    }

    public void clickShowMoreButton() {
        getShowMoreButton().click();
    }

    // Functional

    public int getCommentComponentsCount() {
        return getCommentComponents().size();
    }

    public List<String> getClubComponentAuthors() {
        return getCommentComponents().stream().map(CommentComponent::getAuthorLabelText).toList();

    }

    public List<String> getClubComponentComments() {
        return getCommentComponents().stream().map(CommentComponent::getDatetimeLabelText).toList();
    }

    public CommentComponent getFirstCommentComponent() {
        if (getCommentComponentsCount() == 0) {
            throw new RuntimeException(COMMENT_NOT_FOUND);
        }
        return getCommentComponents().get(0);
    }

    public CommentComponent getClubComponentByPartialAuthor(String partialAuthor) {
        return getCommentComponents()
                .stream()
                .filter(current -> current.getAuthorLabelText().toLowerCase()
                        .contains(partialAuthor.toLowerCase()))
                .findFirst()
                .orElseThrow(() -> new ComponentNotFoundException("Comment partialAuthor: " + partialAuthor + " not Found."));
    }

    public CommentComponent getClubComponentByDateTime(String dateTime) {
        return getCommentComponents()
                .stream()
                .filter(current -> current.getDatetimeLabelText()
                        .equalsIgnoreCase(dateTime))
                .findFirst()
                .orElseThrow(() -> new ComponentNotFoundException("Comment dateTime: " + dateTime + " not Found."));
    }

    public CommentComponent getClubComponentByPartialDate(String partialDate) {
        return getCommentComponents()
                .stream()
                .filter(current -> current.getDatetimeLabelText()
                        .toLowerCase().contains(partialDate.toLowerCase()))
                .findFirst()
                .orElseThrow(() -> new ComponentNotFoundException("Comment partialDate: " + partialDate + " not Found."));
    }

    public boolean isExistClubComponentByPartialAuthor(String partialAuthor) {
        return getClubComponentAuthors()
                .stream()
                .anyMatch(current -> current.toLowerCase()
                        .contains(partialAuthor.toLowerCase()));
    }
}
