package com.softserve.edu.page.speak;

import com.softserve.edu.page.BasePage;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class ChallengePage extends BasePage {
    protected ChallengePage(WebDriver driver) {
        super(driver);
    }

    @FindBy(tagName = "h1")
    private WebElement header;

    @FindBy(css = "button.ytp-large-play-button")
    private WebElement playButton;

    @FindBy(css = ".ytp-impression-link")
    private WebElement videoLink;

    public ChallengePage isCurrentPage(String expectedUrl) {
        Boolean until = wait.until(ExpectedConditions.urlToBe(expectedUrl));
        Assertions.assertTrue(until, "Expected page " + expectedUrl + " in URL: " + driver.getCurrentUrl());

        return this;
    }

    public ChallengePage isContainingTitle(String expectedText) {
        String actualText = header.getText().trim();
        Assertions.assertTrue(actualText.contains(expectedText),
                String.format("Expected header containing: %s, but found: %s", expectedText, actualText));

        return this;
    }

    public ChallengePage scrollToVideo(String name) {
        scrollTo(driver.findElement(By.xpath("//p[contains(text(), '" + name + "')]")));

        return this;
    }

    public ChallengePage switchToIFrame(String name) {
        String expression = "//p[contains(text(), '" + name + "')]/following-sibling::iframe[@class='ql-video' and position()=1]";
        WebElement iframeVideo = driver.findElement(By.xpath(expression));
        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(iframeVideo));

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
