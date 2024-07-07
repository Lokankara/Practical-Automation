package com.softserve.edu.page.speak;

import com.softserve.edu.page.BasePage;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.time.Duration;

public class ChallengePage extends BasePage {
    protected ChallengePage(WebDriver driver) {
        super(driver);
    }

    @FindBy(tagName = "h1")
    private WebElement header;

    @FindBy(css = "button.ytp-large-play-button")
    private WebElement playButton;

    @FindBy(css = ".ytp-youtube-button")
    private WebElement videoLink;

    public ChallengePage isCurrentPage(String expectedUrl) {
        getDriver().manage().timeouts().implicitlyWait(Duration.ZERO);
        Boolean until = getWait().until(ExpectedConditions.urlToBe(expectedUrl));
        getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(IMPLICITLY_WAIT_SECONDS));
        Assertions.assertTrue(until, "Expected page " + expectedUrl + " in URL: " + getDriver().getCurrentUrl());

        return this;
    }

    public ChallengePage isContainingTitle(String expectedText) {
        String actualText = header.getText().trim();
        Assertions.assertTrue(actualText.contains(expectedText),
                String.format("Expected header containing: %s, but found: %s", expectedText, actualText));

        return this;
    }

    public ChallengePage scrollToVideo(String name) {
        scrollTo(getElementByXpath("//p[contains(text(), '" + name + "')]"));

        return this;
    }

    public ChallengePage switchToIFrame(String name) {
        String title = "//p[contains(text(), '" + name + "')]/following-sibling::iframe[@class='ql-video' and position()=1]";
        getDriver().switchTo().frame(getElementByXpath(title));

        return this;
    }

    public ChallengePage playVideo() {
        playButton.click();

        return this;
    }


    public ChallengePage assertVideoLink(String expectedUrl) {
        String iframeSrc = videoLink.getAttribute("href");
        Assertions.assertTrue(iframeSrc.contains(expectedUrl),
                String.format("Expected Link %s, but found: %s", expectedUrl, iframeSrc));

        return this;
    }

    public void isVideoPlaying() {
        Assertions.assertFalse(playButton.isDisplayed(), "Expected Play button not Displayed");
    }
}
