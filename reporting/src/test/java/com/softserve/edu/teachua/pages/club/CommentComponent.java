package com.softserve.edu.teachua.pages.club;

import com.softserve.edu.teachua.tools.ReportUtils;
import com.softserve.edu.teachua.wraps.search.Search;
import com.softserve.edu.teachua.wraps.search.SearchStrategy;
import org.openqa.selenium.WebElement;

public class CommentComponent {

    protected Search search;
    private WebElement clubCard;
    private WebElement authorLabel;
    private WebElement datetimeLabel;
    private WebElement commentLabel;

    public CommentComponent(WebElement clubCard) {
        this.clubCard = clubCard;
        search = SearchStrategy.getSearch();
        initElements();
    }

    private void initElements() {
        ReportUtils.logInfo("Initializing elements on QubStudioPage");
        authorLabel = search.cssSelector("div.author-content > span.name", clubCard);
        datetimeLabel = search.cssSelector("div.author-content > span.datetime", clubCard);
        commentLabel = search.cssSelector("div.ant-comment-content-detail > p", clubCard);
    }

    public WebElement getAuthorLabel() {
        ReportUtils.logInfo("Retrieving author label element");
        return authorLabel;
    }

    public String getAuthorLabelText() {
        String text = getAuthorLabel().getText();
        ReportUtils.logInfo("Retrieved author label text: " + text);
        return text;
    }

    public WebElement getDatetimeLabel() {
        ReportUtils.logInfo("Retrieving datetime label element");
        return datetimeLabel;
    }

    public String getDatetimeLabelText() {
        String text = getDatetimeLabel().getText();
        ReportUtils.logInfo("Retrieved datetime label text: " + text);
        return text;
    }

    public WebElement getCommentLabel() {
        ReportUtils.logInfo("Retrieving comment label element");
        return commentLabel;
    }

    public String getCommentLabelText() {
        String text = getCommentLabel().getText();
        ReportUtils.logInfo("Retrieved comment label text: " + text);
        return text;
    }
}
