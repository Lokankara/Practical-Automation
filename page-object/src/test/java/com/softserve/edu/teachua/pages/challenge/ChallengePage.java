package com.softserve.edu.teachua.pages.challenge;

import com.softserve.edu.teachua.pages.top.TopSearchPart;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public abstract class ChallengePage extends TopSearchPart {

    private WebElement bannerLabel;
    private WebElement applyChallengeButton;
    public static final String TAG_ATTRIBUTE_DISABLED = "disabled";
    private static final By APPLY_BUTTON = By.cssSelector("button.ant-btn.flooded-button.apply-button");
    private static final By BANNER_TITLE = By.cssSelector("div.banner span.title");

    public ChallengePage(WebDriver driver) {
        super(driver);
        initElements();
    }

    private void initElements() {
        bannerLabel = findElement(BANNER_TITLE);
        applyChallengeButton = findElement(APPLY_BUTTON);
    }

    private WebElement getBannerLabel() {
        return bannerLabel;
    }

    public String getBannerLabelText() {
        return getBannerLabel().getText();
    }

    private WebElement getApplyChallengeButton() {
        return applyChallengeButton;
    }

    public ChallengePage assertButtonIsDisabled() {
        Assertions.assertTrue(getApplyChallengeButton().isDisplayed(), "Button is not displayed");
        String disabled = getApplyChallengeButton().getAttribute(TAG_ATTRIBUTE_DISABLED);
        Assertions.assertNotNull(disabled, "Expected button to be disabled, but it is not");
        Assertions.assertTrue(Boolean.parseBoolean(disabled));
        return this;
    }
}
