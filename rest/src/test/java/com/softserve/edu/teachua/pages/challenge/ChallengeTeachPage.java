package com.softserve.edu.teachua.pages.challenge;

import com.softserve.edu.teachua.wraps.browser.DriverUtils;
import org.openqa.selenium.WebElement;

public class ChallengeTeachPage extends ChallengePage {

    private WebElement commentLabel;
    private WebElement webinarIframe;

    public ChallengeTeachPage() {
        initElements();
    }

    private void initElements() {
        commentLabel = search.xpath("//p[contains(text(), 'Вуйтік')]");
        webinarIframe = search.xpath("//iframe[contains(@src, '/JMAF_pSOBws')]");
    }

    // Page Object

    // commentLabel
    public WebElement getCommentLabel() {
        return commentLabel;
    }

    public String getCommentLabelText() {
        return getCommentLabel().getText();
    }

    // webinarIframe
    public WebElement getWebinarIframe() {
        return webinarIframe;
    }

    public void clickWebinarIframe() {
        getWebinarIframe().click();
    }

    // Functional

    // Business Logic

    public YoutubeFrame gotoYoutubeFrame() {
        DriverUtils.scrollToElement(getCommentLabel());
        DriverUtils.switchToFrame(getWebinarIframe());
        return new YoutubeFrame();
    }
}
