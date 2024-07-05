package com.softserve.edu.teachua.pages.challenge;

import com.softserve.edu.teachua.pages.top.TopPart;
import com.softserve.edu.teachua.tools.ReportUtils;
import com.softserve.edu.teachua.wraps.browser.DriverUtils;
import com.softserve.edu.teachua.wraps.search.Search;
import com.softserve.edu.teachua.wraps.search.SearchStrategy;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;

public class YoutubeFrame {

    protected Search search;
    private WebElement videoPlayerLink;
    private WebElement videoPlayButton;
    private WebElement youtubeLink;

    public YoutubeFrame() {
        search = SearchStrategy.getSearch();
        initElements();
    }

    private void initElements() {
        videoPlayerLink = search.cssSelector("div.html5-video-player");
        ReportUtils.logInfo("Initialized videoPlayerLink with CSS selector: html5-video-player");
        videoPlayButton = search.cssSelector("button[aria-label='Play']");
        ReportUtils.logInfo("Initialized videoPlayButton with CSS selector: button 'Play'");
        youtubeLink = search.cssSelector("a[title='Watch on YouTube']");
        ReportUtils.logInfo("Initialized youtubeLink with CSS title 'Watch on YouTube'");
    }

    public WebElement getVideoPlayerLink() {
        ReportUtils.logInfo("Getting videoPlayerLink element");
        return videoPlayerLink;
    }

    public void clickVideoPlayerLink() {
        ReportUtils.logAction("Clicking videoPlayerLink element");
        getVideoPlayerLink().click();
    }

    public WebElement getVideoPlayButton() {
        ReportUtils.logInfo("Getting videoPlayButton element");
        return videoPlayButton;
    }

    public void clickVideoPlayButton() {
        ReportUtils.logAction("Clicking videoPlayButton element");
        getVideoPlayButton().sendKeys(Keys.ENTER);
    }

    public WebElement getYoutubeLink() {
        ReportUtils.logInfo("Getting youtubeLink element");
        return youtubeLink;
    }

    public String getYoutubeLinkText() {
        ReportUtils.logInfo("Getting text of youtubeLink element");
        return getYoutubeLink().getAttribute(TopPart.TAG_ATTRIBUTE_HREF);
    }

    public void clickYoutubeLink() {
        ReportUtils.logAction("Clicking youtubeLink element");
        getYoutubeLink().click();
    }

    public YoutubeFrame playVideoContent() {
        ReportUtils.logAction("Playing video content by clicking videoPlayButton");
        clickVideoPlayButton();
        return this;
    }

    public ChallengeTeachPage gotoChallengeTeachPage() {
        ReportUtils.logAction("Switching to default content and navigating to ChallengeTeachPage");
        DriverUtils.switchToDefaultContent();
        return new ChallengeTeachPage();
    }
}
