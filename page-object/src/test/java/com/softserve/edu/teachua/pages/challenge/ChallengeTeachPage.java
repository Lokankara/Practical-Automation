package com.softserve.edu.teachua.pages.challenge;

import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import static com.softserve.edu.teachua.data.TestData.BASE_CHALLENGES_URL;
import static com.softserve.edu.teachua.data.TestData.BASE_URL;

public class ChallengeTeachPage extends ChallengePage {
    private WebElement headerLabel;
    private static final By HEADER = By.cssSelector("h1");
    public static final String LEARN_CHALLENGE_BANNER = "Навачайся";
    public static final String CHALLENGE_HEADER = "Програма челенджу";
    private static final String TITLE = "//p[contains(text(), '%s')]";
    private static final String TITLE_FRAME = "//p[contains(text(), '%s')]/following-sibling::iframe[@class='ql-video' and position()=1]";

    public ChallengeTeachPage(WebDriver driver) {
        super(driver);
        initElements();
    }

    @Override
    protected void assertCurrentURL() {
        Assertions.assertEquals(BASE_CHALLENGES_URL, driver.getCurrentUrl());
    }

    public ChallengeTeachPage scrollToVideo(String name) {
        scrollToElement(findElement(By.xpath(String.format(TITLE, name))));
        return this;
    }

    private void initElements() {
        headerLabel = findElement(HEADER);
    }

    private WebElement getHeaderLabel() {
        return headerLabel;
    }

    private WebElement getFrameTitle(String name) {
        return findElement(By.xpath(String.format(TITLE_FRAME, name)));
    }

    private void switchTo(WebElement frame) {
        driver.switchTo().frame(frame);
    }

    public String getHeaderText() {
        return getHeaderLabel().getText();
    }

    public YoutubeFrame gotoYoutubeFrame(String name) {
        switchTo(getFrameTitle(name));
        return new YoutubeFrame(driver);
    }
}
