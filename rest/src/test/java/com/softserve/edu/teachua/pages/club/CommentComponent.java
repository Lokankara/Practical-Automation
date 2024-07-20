package com.softserve.edu.teachua.pages.club;

import com.softserve.edu.teachua.wraps.search.Search;
import com.softserve.edu.teachua.wraps.search.SearchStrategy;
import org.openqa.selenium.WebElement;

public class CommentComponent {

    protected Search search;
    //
    private WebElement clubCard;
    //
    private WebElement authorLabel;
    private WebElement datetimeLabel;
    private WebElement commentLabel;

    public CommentComponent(WebElement clubCard) {
        this.clubCard = clubCard;
        search = SearchStrategy.getSearch();
        initElements();
    }

    private void initElements() {
        // init elements
        authorLabel = search.cssSelector("div.author-content > span.name", clubCard);
        datetimeLabel = search.cssSelector("div.author-content > span.datetime", clubCard);
        commentLabel = search.cssSelector("div.ant-comment-content-detail > p", clubCard);
    }

    // Page Object

    public WebElement getAuthorLabel() {
        return authorLabel;
    }

    public String getAuthorLabelText() {
        return getAuthorLabel().getText();
    }

    public WebElement getDatetimeLabel() {
        return datetimeLabel;
    }

    public String getDatetimeLabelText() {
        return getDatetimeLabel().getText();
    }

    // commentLabel
    public WebElement getCommentLabel() {
        return commentLabel;
    }

    public String getCommentLabelText() {
        return getCommentLabel().getText();
    }

    // Functional

    // Business Logic

}
