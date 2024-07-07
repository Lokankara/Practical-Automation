package com.softserve.edu.teachua.pages.challenge;

import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.WebDriver;

import static com.softserve.edu.teachua.data.TestData.TEACH_CHALLENGE_URL;

public class ChallengeTeachPage extends ChallengePage {
    public ChallengeTeachPage(WebDriver driver) {
        super(driver);
    }

    @Override
    public ChallengeTeachPage assertCurrentURL() {
        Assertions.assertEquals(TEACH_CHALLENGE_URL, getDriver().getCurrentUrl(),
                "Current URL should match expected TEACH_CHALLENGE_URL");
        return this;
    }

    @Override
    public ChallengePage visit() {
        getDriver().get(TEACH_CHALLENGE_URL);
        return this;
    }
}
