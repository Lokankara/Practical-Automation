package com.softserve.edu.teachua.pages.challenge;

import com.softserve.edu.teachua.pages.top.BaseComponent;
import com.softserve.edu.teachua.pages.top.TopPart;
import com.softserve.edu.teachua.wraps.browser.DriverUtils;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;

public class YoutubeFrame extends BaseComponent {

    private WebElement videoPlayerLink;
    private WebElement videoPlayButton;
    private WebElement youtubeLink;

    public YoutubeFrame() {
        initElements();
    }

    private void initElements() {
        videoPlayerLink = search.cssSelector("div.html5-video-player");
        videoPlayButton = search.cssSelector("button.ytp-large-play-button");
        youtubeLink = search.cssSelector("a[title='Watch on YouTube']");
    }

    public WebElement getVideoPlayerLink() {
        return videoPlayerLink;
    }

    public void clickVideoPlayerLink() {
        getVideoPlayerLink().click();
    }

    public WebElement getVideoPlayButton() {
        return videoPlayButton;
    }

    public void clickVideoPlayButton() {
        getVideoPlayButton().click();
    }

    public WebElement getYoutubeLink() {
        return youtubeLink;
    }

    public String getYoutubeLinkText() {
        return getYoutubeLink().getAttribute(TopPart.TAG_ATTRIBUTE_HREF);
    }

    public void clickYoutubeLink() {
        getYoutubeLink().click();
    }

    public YoutubeFrame playVideoContent() {
        clickVideoPlayButton();
        return this;
    }

    public ChallengeTeachPage gotoChallengeTeachPage() {
        DriverUtils.getInstance().getDriver().switchTo().defaultContent();
        return new ChallengeTeachPage();
    }

    public YoutubeFrame stopVideoContent() {
        clickYoutubeLink();
        return this;
    }
}
