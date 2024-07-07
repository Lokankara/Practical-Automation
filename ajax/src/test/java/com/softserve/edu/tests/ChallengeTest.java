package com.softserve.edu.tests;

import com.softserve.edu.page.speak.Header;
import com.softserve.edu.runner.BaseTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ChallengeTest extends BaseTest {

    private static final String TAB_MENU = "Навчайся";
    private static final String TEACHER_NAME = "Наталя Вуйтік";
    private static final String EXPECTED_URL = "https://www.youtube.com/watch?v=JMAF_pSOBws";
    private static final String BASE_URL = "http://speak-ukrainian.eastus2.cloudapp.azure.com/dev/";

    @BeforeEach
    public void beforeEach() {
        getDriver().get(BASE_URL);
    }

    @Test
    @DisplayName("Verify that the video on the challenge page plays correctly when selected")
    void checkChallengeYoutube() {

        new Header(getDriver())
                .openMenu()
                .chooseTab(TAB_MENU)
                .isCurrentPage(BASE_URL + "challenges/2")
//                .isContainingTitle(TAB_MENU) //bug: mistake in header, missed `ч`
                .scrollToVideo(TEACHER_NAME)
                .switchToIFrame(TEACHER_NAME)
                .assertVideoLink(EXPECTED_URL)
                .playVideo()
                .isVideoPlaying();
    }
}
