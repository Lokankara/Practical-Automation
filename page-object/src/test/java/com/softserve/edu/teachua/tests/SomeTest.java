package com.softserve.edu.teachua.tests;

import com.softserve.edu.teachua.data.Challenges;
import com.softserve.edu.teachua.data.ClubContents;
import com.softserve.edu.teachua.data.CommentContents;
import com.softserve.edu.teachua.data.WebinarChallenges;
import com.softserve.edu.teachua.pages.challenge.ChallengeOnlyPage;
import com.softserve.edu.teachua.pages.challenge.ChallengePage;
import com.softserve.edu.teachua.pages.challenge.ChallengeTeachPage;
import com.softserve.edu.teachua.pages.club.ClubPage;
import com.softserve.edu.teachua.tests.provider.ClubContentsProvider;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

class SomeTest extends TestRunner {
    private static final int SECOND_PAGE = 2;

    static Stream<? extends Arguments> provideTeacherNames() {
        return Stream.of(WebinarChallenges.values()).map(Arguments::of);
    }

    @ParameterizedTest(name = "{index} => webinar: {0}")
    @MethodSource("provideTeacherNames")
    void checkTeachChallengeWithTeacherNamesAndVideoHeader(WebinarChallenges webinar) {
        String headerText = loadApplication()
                .gotoTeachChallengePage()
                .gotoYoutubeFrame(webinar.getHeader())
                .playVideo()
                .isVideoPlaying()
                .assertHeader(webinar.getTitle())
                .assertVideoLink(webinar.getUrl())
                .gotoChallengeTeachPage()
                .getHeaderText();

        Assertions.assertTrue(headerText.contains(ChallengeTeachPage.CHALLENGE_HEADER),
                "Header text does not contain expected challenge header: "
                        + ChallengeTeachPage.CHALLENGE_HEADER);
    }

    private static Stream<Arguments> challengeLearnProvider() {
        return Stream.of(
                Arguments.of(Challenges.TO_LEARN_CHALLENGE)
        );
    }

    @ParameterizedTest(name = "{index} => challengeName: {0}")
    @MethodSource("challengeLearnProvider")
    void testChallengeVideoPlayback(Challenges challenge) {
        final WebinarChallenges webinar = WebinarChallenges.VUYTIK;

        String bannerLabelText = loadApplication()
                .gotoChallengePage(challenge, ChallengeTeachPage.class)
                .scrollToVideo(webinar.getHeader())
                .gotoYoutubeFrame(webinar.getHeader())
                .assertVideoLink(webinar.getUrl())
                .assertHeader(webinar.getHeader())
                .playVideo()
                .isVideoPlaying()
                .gotoChallengeTeachPage()
                .getBannerLabelText();

        Assertions.assertEquals(ChallengeTeachPage.LEARN_CHALLENGE_BANNER, bannerLabelText,
                "Banner label text does not match expected value");
    }

    static Stream<Arguments> provideChallengePage() {
        return Stream.of(
                Arguments.of(Challenges.DEFAULT_CHALLENGE, ChallengeTeachPage.class, ChallengeTeachPage.LEARN_CHALLENGE_BANNER),
                Arguments.of(Challenges.TO_LEARN_CHALLENGE, ChallengeTeachPage.class, ChallengeTeachPage.LEARN_CHALLENGE_BANNER),
                Arguments.of(Challenges.THE_ONLY_CHALLENGE, ChallengeOnlyPage.class, ChallengeOnlyPage.ONLY_CHALLENGE_BANNER)
        );
    }

    @ParameterizedTest(name = "{index} => challengePage: {0}")
    @MethodSource("provideChallengePage")
    void testProvideChallengePages(Challenges challenge, Class<? extends ChallengePage> expectedPageClass, String expectedBanner) {
        String labelText = loadApplication()
                .gotoChallengePage(challenge, expectedPageClass)
                .assertButtonIsDisabled()
                .getBannerLabelText();

        Assertions.assertEquals(expectedBanner, labelText,
                "Label text does not match expected banner text");
    }


    private static Stream<Arguments> clubProvider() {
        return Stream.of(
                Arguments.of(ClubContents.NEW_CADRE_CLUB),
                Arguments.of(ClubContents.CENTER_NO3_CLUB),
                Arguments.of(ClubContents.PLASTUN_KHARAKTER_CLUB)
        );
    }

    @ParameterizedTest(name = "{index} => city={0}")
    @MethodSource("clubProvider")
    void checkFirstCityClubAddress(ClubContents content) {
        loadApplication()
                .gotoClubPage()
                .chooseCity(content.getCity())
                .assertCityHeader(content.getCity())
                .getFirstClubComponent()
                .assertClubAddress(content.getCity());
    }

    @ParameterizedTest(name = "{index} => city={0}")
    @MethodSource("clubProvider")
    void checkFirstCityClubDetailPage(ClubContents content) {
        loadApplication()
                .gotoClubPage()
                .chooseCity(content.getCity())
                .assertCityHeader(content.getCity())
                .getFirstClubComponent()
                .assertClubContent(content)
                .openClubDetailsPage()
                .assertDetailsHeader(content);
    }

    @ParameterizedTest(name = "{index} => city={0}")
    @MethodSource("clubProvider")
    void checkFirstCityClubDetailsInfoModal(ClubContents content) {
        loadApplication()
                .gotoClubPage()
                .chooseCity(content.getCity())
                .assertCityHeader(content.getCity())
                .getFirstClubComponent()
                .assertClubContent(content)
                .openClubInfoModal()
                .assertHeaderText()
                .gotoClubDetailsPage()
                .assertInfoModalHeader(content.getClub())
                .openClubCommentModal()
                .assertErrorLoginMessage();
    }

    @ParameterizedTest(name = "{index} => clubContents={0}")
    @MethodSource("clubProvider")
    void checkClubPagePreviousAndNextPaginationLinkOnTheNextPage(ClubContents content) {
        ClubPage clubPage = loadApplication()
                .gotoClubPage()
                .chooseCity(content.getCity())
                .assertCityHeader(content.getCity())
                .gotoNextClubPage();

        Assertions.assertTrue(clubPage.isEnableNextPageLink(), "Next page link is not enabled");
        Assertions.assertTrue(clubPage.isEnablePreviousPageLink(), "Previous page link is not enabled");

    }

    @ParameterizedTest(name = "{index} => clubContents={0}")
    @MethodSource("clubProvider")
    void checkClubPagePreviousAndNextPaginationLink(ClubContents content) {
        ClubPage clubPage = loadApplication()
                .gotoClubPage()
                .chooseCity(content.getCity())
                .assertCityHeader(content.getCity())
                .gotoNextClubPage();

        Assertions.assertTrue(clubPage.isEnablePreviousPageLink(), "Previous page link is not enabled");
        Assertions.assertTrue(clubPage.isEnableNextPageLink(), "Expected next page link to be disabled, but it is enabled with next page");
    }

    @ParameterizedTest(name = "{index} => clubContents={0}")
    @MethodSource("clubProvider")
    void checkClubPagePreviousAndNextPaginationLinkOnTheSecondPage(ClubContents content) {
        ClubPage clubPage = loadApplication()
                .gotoClubPage()
                .chooseCity(content.getCity())
                .assertCityHeader(content.getCity())
                .gotoPage(SECOND_PAGE).assertCityHeader(content.getCity());

        Assertions.assertTrue(clubPage.isEnableNextPageLink(), "Next page link is not enabled");
        Assertions.assertTrue(clubPage.isEnablePreviousPageLink(), "Previous page link is not enabled");
    }

    @ParameterizedTest(name = "{index} => clubContents={0}")
    @MethodSource("clubProvider")
    void checkClubExist(ClubContents clubContents) {
        loadApplication()
                .gotoClubPage()
                .chooseCity(clubContents.getCity())
                .getClubComponentByPartialTitle(clubContents.getClub())
                .assertClubContent(clubContents);
    }

    @ParameterizedTest(name = "{index} => clubContents={0}")
    @MethodSource("clubProvider")
    void checkAdvancedSearchGotoNextClubPage(ClubContents clubContents) {
        loadApplication()
                .gotoClubPage()
                .chooseCity(clubContents.getCity())
                .gotoNextClubPage()
//                .gotoAdvancedClubPage()
                .assertCityHeader(clubContents.getCity());
    }

    @ParameterizedTest(name = "{index} => clubContents={0}")
    @ArgumentsSource(ClubContentsProvider.class)
    void checkIsExistClubComponentByPartialTitle(ClubContents clubContents) {
        boolean isExist = loadApplication()
                .gotoClubPage()
                .chooseCity(clubContents.getCity())
                .gotoNextClubPage()
                .isExistClubComponentByPartialTitle(clubContents.getClub());
        Assertions.assertTrue(isExist, "Next page link is not enabled");
    }
    @ParameterizedTest(name = "{index} => clubContents={0}")
    @ArgumentsSource(ClubContentsProvider.class)
    void checkPreviousClubPage(ClubContents clubContents) {
        loadApplication()
                .gotoClubPage()
                .chooseCity(clubContents.getCity())
                .gotoNextClubPage()
                .gotoPreviousClubPage()
                .untilClubComponentIsFoundByPartialTitle(clubContents.getClub());
    }

    private static Stream<Arguments> commentProvider() {
        return Stream.of(
                Arguments.of(ClubContents.IT_EDUCATION_CLUB, CommentContents.FIRST_COMMENT)
        );
    }

    @ParameterizedTest(name = "{index} => clubContents={0}, commentContents={0}")
    @MethodSource("commentProvider")
    void checkPaginationNextUntilCommentIsExist(ClubContents clubContents, CommentContents commentContents) {
        loadApplication()
                .gotoClubPage()
                .chooseCity(clubContents.getCity())
                .getClubComponentByPartialTitle(clubContents.getClub())
                .openClubDetailsPage()
                .assertDetailsHeader(clubContents)
                .assertCommentComponent(commentContents);
    }

    @ParameterizedTest(name = "{index} => searchText={0}")
    @DisplayName("Test Select Club by City and Check Info Modal when selecting city clubs, searching club details, opening info modal, checking info modal header, navigating to club details page, and verifying club details header")
//    @ArgumentsSource(ClubContentsProvider.class)
    @MethodSource("commentProvider")
    void testSelectClubByCityAndCheckInfoModal(ClubContents contents) {
        loadApplication()
                .selectCityClubs(contents.getCity())
                .successfulSearchClub(contents.getClub())
                .openClubInfoModal(contents.getClub())
                .assertClubContent(contents)
                .gotoClubDetailsPage()
                .assertDetailsHeader(contents)
                .assertInfoModalHeader(contents.getClub());
    }

    @ParameterizedTest(name = "{index} => {0}")
    @DisplayName("Test Existing Comment in Last Ten: Given comment, when viewing club details and loading more comments, then verify comment exists in last ten comments")
    @MethodSource("commentProvider")
    void testExistCommentInNextTen(ClubContents contents, CommentContents commentContents) {
        loadApplication()
                .selectCityClubs(contents.getCity())
                .successfulSearchClub(contents.getClub())
                .openClubInfoModal(contents.getClub())
                .assertClubContent(contents)
                .gotoClubDetailsPage()
                .assertDetailsHeader(contents)
                .showMoreComments(10)
                .assertCommentComponent(commentContents);
    }

    @DisplayName("Test Existing Comment When User Is Logged: Given user, when successfully logging in, searching club details, checking comments, and signing out, then verify comment exists")
    @ParameterizedTest(name = "{index} => {0}")
    @MethodSource("commentProvider")
    void testExistLastComment(ClubContents contents, CommentContents commentContents) {
        loadApplication()
                .selectCityClubs(contents.getCity())
                .successfulSearchClub(contents.getClub())
                .openClubInfoModal(contents.getClub())
                .assertClubContent(contents)
                .gotoClubDetailsPage()
                .assertDetailsHeader(contents)
                .checkLastCommentText(commentContents)
                .assertInfoModalHeader(contents.getClub());
    }
}
