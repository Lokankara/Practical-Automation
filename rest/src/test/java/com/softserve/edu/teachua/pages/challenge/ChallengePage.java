package com.softserve.edu.teachua.pages.challenge;

import com.softserve.edu.teachua.pages.top.TopSearchPart;
import org.openqa.selenium.WebElement;

public abstract class ChallengePage extends TopSearchPart {

    private WebElement bannerLabel;

    public ChallengePage() {
        initElements();
    }

    private void initElements() {
        bannerLabel = search.cssSelector("div.banner span.title");
    }

    // Page Object

    // bannerLabel
    public WebElement getBannerLabel() {
        return bannerLabel;
    }

    public String getBannerLabelText() {
        return getBannerLabel().getText();
    }

    // Functional

    // Business Logic

}
