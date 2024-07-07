package com.softserve.edu.teachua.pages.challenge;

import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.WebDriver;

import static com.softserve.edu.teachua.data.TestData.ONLY_CHALLENGE_URL;

public class ChallengeUnitedPage extends ChallengePage {
    public ChallengeUnitedPage(WebDriver driver) {
        super(driver);
    }

    @Override
    public ChallengeUnitedPage assertCurrentURL() {
        Assertions.assertEquals(ONLY_CHALLENGE_URL, getDriver().getCurrentUrl(),
                "Current URL should match expected ONLY_CHALLENGE_URL");
        return this;
    }

    @Override
    public ChallengePage visit() {
        getDriver().get(ONLY_CHALLENGE_URL);
        return this;
    }
}
