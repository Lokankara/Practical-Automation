package com.softserve.edu.teachua.tests;

import com.softserve.edu.teachua.data.Challenges;
import com.softserve.edu.teachua.data.TestData;
import com.softserve.edu.teachua.pages.main.HomePage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class HomeTest extends TestRunner {

    @Test
    @DisplayName("Smoke Test: Navigate through HomePage to Verify Teach Label Text")
    void testSmoke() {
        HomePage homePage = new HomePage(driverWrapper.getDriver())
                .visit()
                .gotoClubPage()
                .gotoChallengePage(Challenges.TO_LEARN_CHALLENGE)
                .gotoChallengePage(Challenges.THE_ONLY_CHALLENGE)
                .gotoNewsPage().assertHeader()
                .gotoAboutUsPage()
                .gotoUkrainianServicePage()
                .gotoHomePage();

        Assertions.assertTrue(homePage.getTeachLabelText().contains(TestData.BEGIN_TEACH_LABEL_MESSAGE),
                String.format("Expected teach text to contain '%s'", TestData.BEGIN_TEACH_LABEL_MESSAGE));
    }
}
