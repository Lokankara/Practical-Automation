package com.softserve.edu.teachua.pages.challenge;

import com.softserve.edu.teachua.pages.top.TopSearchPart;
import com.softserve.edu.teachua.tools.ReportUtils;
import org.openqa.selenium.WebElement;

public abstract class ChallengePage extends TopSearchPart {

    private WebElement bannerLabel;

    public ChallengePage() {
        initElements();
    }

    private void initElements() {
        bannerLabel = search.cssSelector("div.banner span.title");
        ReportUtils.logInfo("Initialized bannerLabel with CSS selector");
    }

    public WebElement getBannerLabel() {
        ReportUtils.logInfo("Getting bannerLabel element");
        return bannerLabel;
    }

    public String getBannerLabelText() {
        String text = getBannerLabel().getText();
        ReportUtils.logInfo("Retrieved text from bannerLabel: " + text);
        return text;
    }
}
