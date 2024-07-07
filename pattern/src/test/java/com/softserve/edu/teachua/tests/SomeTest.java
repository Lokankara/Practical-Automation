package com.softserve.edu.teachua.tests;

import com.softserve.edu.teachua.data.Challenges;
import com.softserve.edu.teachua.data.city.City;
import com.softserve.edu.teachua.data.city.CityRepository;
import com.softserve.edu.teachua.data.club.Club;
import com.softserve.edu.teachua.data.comment.Comment;
import com.softserve.edu.teachua.data.UrlContents;
import com.softserve.edu.teachua.data.club.ClubRepository;
import com.softserve.edu.teachua.data.comment.CommentRepository;
import com.softserve.edu.teachua.data.user.IUser;
import com.softserve.edu.teachua.data.user.UserRepository;
import com.softserve.edu.teachua.pages.challenge.ChallengeTeachPage;
import com.softserve.edu.teachua.pages.challenge.YoutubeFrame;
import com.softserve.edu.teachua.pages.club.AdvancedClubPage;
import com.softserve.edu.teachua.pages.club.ClubComponent;
import com.softserve.edu.teachua.pages.club.ClubDetailsPage;
import com.softserve.edu.teachua.pages.menu.HomePage;
import com.softserve.edu.teachua.pages.top.TopPart;
import com.softserve.edu.teachua.pages.user.LoginModal;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

@Execution(ExecutionMode.CONCURRENT)
class SomeTest extends TestRunner {

    @Test
    void checkSmoke() {
        HomePage homePage = loadApplication()
                .gotoClubPage()
                .gotoNewsPage()
                .gotoAboutUsPage()
                .gotoUkrainianServicePage()
                .gotoHomePage();

        Assertions.assertTrue(homePage.getTeachLabelText().contains(HomePage.BEGIN_TEACH_LABEL_MESSAGE));
    }

    private static Stream<Arguments> validUserProvider() {
        return Stream.of(Arguments.of(UserRepository.get().customer()));
    }

    @ParameterizedTest(name = "{index} => user={0}")
    @MethodSource("validUserProvider")
    void checkSuccessfulLogin(IUser user) {

        HomePage homePage = loadApplication()
                .openLoginModal()
                .successfulLogin(user);

//        String popupMessage = homePage.getPopupMessageLabelText();

//        Assertions.assertEquals(TopPart.POPUP_MESSAGE_SUCCESSFULLY, popupMessage);
        Assertions.assertTrue(homePage.getTeachLabelText().contains(HomePage.BEGIN_TEACH_LABEL_MESSAGE));
        Assertions.assertFalse(homePage.signOutUser().isUserLogged());
    }

    private static Stream<Arguments> invalidUserProvider() {
        return Stream.of(Arguments.of(UserRepository.get().invalidUser()));
    }

    @ParameterizedTest(name = "{index} => user={0}")
    @MethodSource("invalidUserProvider")
    void checkUnsuccessfulLogin(IUser user) {
        LoginModal loginModal = loadApplication()
                .openLoginModal()
                .unsuccessfulLoginPage(user);
//        String popupMessage = loginModal.getPopupMessageLabelText();

//        Assertions.assertEquals(LoginModal.POPUP_MESSAGE_UNSUCCESSFULLY, popupMessage);
    }

    private static Stream<Arguments> challengeTeachProvider() {
        return Stream.of(
                Arguments.of(Challenges.TO_LEARN_CHALLENGE, UrlContents.WEBINAR_IFRAME)
        );
    }

    @ParameterizedTest(name = "{index} => challengeName={0}")
    @MethodSource("challengeTeachProvider")
    void checkChallenge(Challenges challengeName, UrlContents urlContents) {
        YoutubeFrame youtubeFrame = loadApplication()
                .gotoChallengePage(challengeName, ChallengeTeachPage.class)
                .gotoYoutubeFrame()
                .playVideoContent();
        System.out.println("\tyoutubeFrame.getYoutubeLinkText() = " + youtubeFrame.getYoutubeLinkText());

        Assertions.assertTrue(youtubeFrame.getYoutubeLinkText().contains(urlContents.getSearchVideo()));
//        HomePage homePage = youtubeFrame.stopVideoContent()
//                .gotoChallengeTeachPage()
//                .gotoHomePage();
    }

    private static Stream<Arguments> cityProvider() {
        return Stream.of(
                Arguments.of(CityRepository.get().kyivCity()),
                Arguments.of(CityRepository.get().defaultCity())
        );
    }

    @ParameterizedTest(name = "{index} => city={0}")
    @MethodSource("cityProvider")
    void checkCityClubs(City city) {
        ClubComponent ClubComponent = loadApplication()
                .gotoClubPage()
                .chooseCity(city.getCityName())
                .getClubContainer()
                .getFirstClubComponent();

        Assertions.assertTrue(ClubComponent.getAddressLabelText().contains(city.getCityName()), city.getCityName() +": "+ ClubComponent.getAddressLabelText());
    }

    private static Stream<Arguments> clubProvider() {
        return Stream.of(
                Arguments.of(ClubRepository.get().itEducationClub())
        );
    }

    @ParameterizedTest(name = "{index} => club={0}")
    @MethodSource("clubProvider")
    void checkClubExist(Club club) {
        ClubComponent ClubComponent = loadApplication()
                .gotoClubPage()
                .chooseCity(club.getCityName())
                .getClubContainer()
                .getClubComponentByPartialTitle(club.getTitle());

        Assertions.assertTrue(ClubComponent.getTitleLinkText().contains(club.getTitle()));
    }

    @ParameterizedTest(name = "{index} => clubContents={0}")
    @MethodSource("clubProvider")
    void checkAdvancedSearch(Club club) {
        AdvancedClubPage advancedClubPage = loadApplication()
                .gotoClubPage()
                .chooseCity(club.getCity().getCityName())
                .gotoAdvancedClubPage();

        Assertions.assertTrue(advancedClubPage.isExistClubByPartialTitle(club.getTitle()));
    }

    private static Stream<Arguments> commentProvider() {
        return Stream.of(
                Arguments.of(ClubRepository.get().itEducationClub(), CommentRepository.get().firstComment())
        );
    }

    @ParameterizedTest(name = "{index} => clubContents={0}, commentContents={1}")
    @MethodSource("commentProvider")
    void checkCommentExist(Club club, Comment comment) {
        ClubDetailsPage clubDetailsPage = loadApplication()
                .gotoClubPage()
                .chooseCity(club.getCityName())
                .getClubContainer()
                .getClubComponentByPartialTitle(club.getTitle())
                .openClubDetailsPage();

        Assertions.assertTrue(clubDetailsPage.getCommentsContainer()
                .isExistClubComponentByPartialAuthor(comment.getAuthor()));
    }
}
