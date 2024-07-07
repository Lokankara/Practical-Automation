package com.softserve.edu.teachua.pages.club;

import com.softserve.edu.teachua.pages.top.BaseComponent;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class CommentComponent extends BaseComponent {
    private final WebElement clubCard;

    public CommentComponent(WebElement clubCard) {
        this.clubCard = clubCard;
    }

    public WebElement getAuthorLabel() {
        return clubCard.findElement(By.cssSelector("div.author-content > span.name"));
    }

    public String getAuthorLabelText() {
        return getAuthorLabel().getText();
    }

    public WebElement getDatetimeLabel() {
        return clubCard.findElement(By.cssSelector("div.author-content > span.datetime"));
    }

    public String getDatetimeLabelText() {
        return getDatetimeLabel().getText();
    }

    public WebElement getCommentLabel() {
        return clubCard.findElement(By.cssSelector("div.ant-comment-content-detail > p"));
    }

    public String getCommentLabelText() {
        return getCommentLabel().getText();
    }
}
