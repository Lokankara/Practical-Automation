package com.softserve.edu.teachua.pages.challenge;

import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.WebDriver;

import static com.softserve.edu.teachua.data.TestData.BASE_ONLY_CHALLENGES_URL;

public class ChallengeOnlyPage extends ChallengePage {
    public static final String ONLY_CHALLENGE_BANNER = "\"Єдині\" - це 28 днів підтримки у переході на українську";

    public ChallengeOnlyPage(WebDriver driver) {
        super(driver);
    }

    @Override
    protected void assertCurrentURL() {
        Assertions.assertEquals(BASE_ONLY_CHALLENGES_URL, driver.getCurrentUrl());
    }
}
