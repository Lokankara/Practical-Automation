package com.softserve.edu.teachua.pages.challenge;

import com.softserve.edu.teachua.pages.top.BaseFramePart;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class YoutubeFrame extends BaseFramePart {
    private WebElement youtubeTitleLink;
    private WebElement playButton;
    private static final By VIDEO_TITLE = By.cssSelector(".ytp-title-link");
    private static final By LARGE_PLAY_BUTTON = By.cssSelector("button.ytp-large-play-button");

    public YoutubeFrame(WebDriver driver) {
        super(driver);
        initElements();
    }

    private void initElements() {
        youtubeTitleLink = driver.findElement(VIDEO_TITLE);
        playButton = driver.findElement(LARGE_PLAY_BUTTON);
    }

    private WebElement getPlayButton() {
        return playButton;
    }

    private WebElement getYoutubeTitleLink() {
        return youtubeTitleLink;
    }

    public String getYoutubeTitleText() {return getYoutubeTitleLink().getText();}

    public YoutubeFrame playVideo() {
        clickPlayButton();
        return this;
    }

    private void clickPlayButton() {
        getPlayButton().click();
    }

    public ChallengeTeachPage gotoChallengeTeachPage() {
        driver.switchTo().defaultContent();
        return new ChallengeTeachPage(driver);
    }

    public YoutubeFrame assertVideoLink(String expectedUrl) {
        String iframeSrc = getYoutubeTitleLink().getAttribute("href");
        Assertions.assertTrue(iframeSrc.contains(expectedUrl),
                String.format("Expected Link %s, but found: %s", expectedUrl, iframeSrc));
        return this;
    }

    public YoutubeFrame isVideoPlaying() {
        Boolean isInvisible = waitForInvisibilityOfElement(getPlayButton());
        Assertions.assertTrue(isInvisible, "Expected Play button not visible after clicked");
        return this;
    }

    public YoutubeFrame assertHeader(String paragraph) {
        Assertions.assertTrue(getYoutubeTitleText().contains(paragraph),
                String.format("Expected '%s' to be contained in the header, but got '%s'", paragraph, getYoutubeTitleText()));
        return this;
    }
}
