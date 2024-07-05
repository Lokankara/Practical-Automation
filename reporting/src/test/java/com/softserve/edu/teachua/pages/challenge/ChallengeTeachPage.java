package com.softserve.edu.teachua.pages.challenge;

import com.softserve.edu.teachua.tools.ReportUtils;
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
        ReportUtils.logInfo("Initialized commentLabel which contains name 'Вуйтік'");
        webinarIframe = search.xpath("//iframe[contains(@src, '/JMAF_pSOBws')]");
        ReportUtils.logInfo("Initialized webinarIframe which contains link '/JMAF_pSOBws'");
    }

    public WebElement getCommentLabel() {
        ReportUtils.logInfo("Getting commentLabel element");
        return commentLabel;
    }

    public String getCommentLabelText() {
        String text = getCommentLabel().getText();
        ReportUtils.logInfo("Retrieved text from commentLabel: " + text);
        return text;
    }

    public WebElement getWebinarIframe() {
        ReportUtils.logInfo("Getting webinarIframe element");
        return webinarIframe;
    }

    public void clickWebinarIframe() {
        ReportUtils.logAction("Clicking webinarIframe element");
        getWebinarIframe().click();
    }

    public YoutubeFrame gotoYoutubeFrame() {
        ReportUtils.logAction("Switching to webinarIframe");
        DriverUtils.switchToFrame(getWebinarIframe());
        ReportUtils.logInfo("Navigating to YoutubeFrame");
        return new YoutubeFrame();
    }
}
