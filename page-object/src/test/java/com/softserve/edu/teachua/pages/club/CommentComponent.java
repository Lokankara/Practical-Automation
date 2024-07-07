package com.softserve.edu.teachua.pages.club;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class CommentComponent {

    protected WebDriver driver;
    private WebElement authorLabel;
    private WebElement commentLabel;
    private WebElement datetimeLabel;
    private final WebElement clubCard;

    public CommentComponent(WebDriver driver, WebElement clubCard) {
        this.driver = driver;
        this.clubCard = clubCard;
        initElements();
    }

    private void initElements() {
        authorLabel = driver.findElement(By.cssSelector(".author-content > span.name"));
        datetimeLabel = driver.findElement(By.cssSelector(".author-content > span.datetime"));
        commentLabel = driver.findElement(By.cssSelector(".ant-comment-content-detail"));
    }

    private WebElement getClubCard() {
        return clubCard;
    }

    private WebElement getAuthorLabel() {
        return authorLabel;
    }

    private WebElement getDatetimeLabel() {
        return datetimeLabel;
    }

    private WebElement getCommentLabel() {
        return commentLabel;
    }

    public String getClubCardText() {
        return getClubCard().getText();
    }

    public String getAuthorText() {
        return getAuthorLabel().getText();
    }

    public String getDatetimeText() {
        return getDatetimeLabel().getText();
    }

    public String getCommentText() {
        return getCommentLabel().getText();
    }
}
