package com.softserve.edu.teachua.pages.challenge;

import com.softserve.edu.teachua.wraps.browser.DriverUtils;
import org.openqa.selenium.WebElement;

public class ChallengeTeachPage extends ChallengePage {

    private WebElement commentLabel;
    private WebElement webinarIframe;

    public ChallengeTeachPage() {
        super();
        initElements();
    }

    private void initElements() {
        commentLabel = search.xpath("//p[contains(text(), 'Вуйтік')]");
        webinarIframe = search.xpath("//iframe[contains(@src, '/JMAF_pSOBws')]");
    }

    public WebElement getCommentLabel() {
        return commentLabel;
    }

    public String getCommentLabelText() {
        return getCommentLabel().getText();
    }

    public WebElement getWebinarIframe() {
        return webinarIframe;
    }

    public void clickWebinarIframe() {
        getWebinarIframe().click();
    }

    public YoutubeFrame gotoYoutubeFrame() {
        DriverUtils.getInstance().getDriver().switchTo().frame(getWebinarIframe());
        return new YoutubeFrame();
    }
}
