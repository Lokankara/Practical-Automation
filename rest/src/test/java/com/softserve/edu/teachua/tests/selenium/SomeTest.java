package com.softserve.edu.teachua.tests.selenium;

import com.softserve.edu.teachua.data.Challengies;
import com.softserve.edu.teachua.data.Cities;
import com.softserve.edu.teachua.data.club.ClubContents;
import com.softserve.edu.teachua.data.club.ClubRepository;
import com.softserve.edu.teachua.data.comment.CommentContents;
import com.softserve.edu.teachua.data.club.IClub;
import com.softserve.edu.teachua.data.user.IUser;
import com.softserve.edu.teachua.data.UrlContents;
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
        // Steps
        HomePage homePage = loadApplication()
                .gotoClubPage()
                .gotoNewsPage()
                .gotoAboutUsPage()
                .gotoUkrainianServicePage()
                .gotoHomePage();
        presentationSleep();
        // Check
        Assertions.assertTrue(homePage.getTeachLabelText().contains(HomePage.BEGIN_TEACH_LABEL_MESSAGE));
    }

    private static Stream<Arguments> validUserProvider() {
        return Stream.of(
                Arguments.of(UserRepository.get().customer())
        );
    }

    @ParameterizedTest(name = "{index} => email={0}, password={1}")
    @MethodSource("validUserProvider")
    void checkSuccessfulLogin(IUser user) {
        // Steps
        HomePage homePage = loadApplication()
                .openLoginModal()
                .successfulLogin(user.getEmail(), user.getPassword());
        String popupMessage = homePage.getPopupMessageLabelText();
        presentationSleep();
        //
        // Check pop-up message
        Assertions.assertEquals(TopPart.POPUP_MESSAGE_SUCCESSFULLY, popupMessage);
        //
        // Check
        Assertions.assertTrue(homePage.getTeachLabelText().contains(HomePage.BEGIN_TEACH_LABEL_MESSAGE));
        presentationSleep();
        //
        homePage = homePage.signoutUser();
        Assertions.assertFalse(homePage.isUserLogged());
        presentationSleep();
    }


    private static Stream<Arguments> invalidUserProvider() {
        return Stream.of(
                Arguments.of(UserRepository.get().invalidUser())
        );
    }

    @ParameterizedTest(name = "{index} => email={0}, password={1}")
    @MethodSource("invalidUserProvider")
    void checkUnsuccessfulLogin(IUser user) {
        // Steps
        LoginModal loginModal = loadApplication()
                .openLoginModal()
                .unsuccessfulLoginPage(user.getEmail(), user.getPassword());
        String popupMessage = loginModal.getPopupMessageLabelText();
        presentationSleep();

        // Check pop-up message
        Assertions.assertEquals(LoginModal.POPUP_MESSAGE_UNSUCCESSFULLY, popupMessage);
        presentationSleep();
    }

    private static Stream<Arguments> challengeTeachProvider() {
        return Stream.of(
                Arguments.of(Challengies.TO_LEARN_CHALLENGE, UrlContents.WEBINAR_IFRAME)
        );
    }

    @ParameterizedTest(name = "{index} => challengeName={0}")
    @MethodSource("challengeTeachProvider")
    void checkChallenge(Challengies challengeName, UrlContents urlContents) {
        YoutubeFrame youtubeFrame = loadApplication()
                .gotoChallengePage(challengeName, ChallengeTeachPage.class)
                .gotoYoutubeFrame()
                .playVideoContent();
        presentationSleep(4);
        System.out.println("\tyoutubeFrame.getYoutubeLinkText() = " + youtubeFrame.getYoutubeLinkText());
        //
        // Check Youtube Frame
        Assertions.assertTrue(youtubeFrame.getYoutubeLinkText().contains(urlContents.getSearchVideo()));
        presentationSleep();

        youtubeFrame.gotoChallengeTeachPage().gotoHomePage();
        presentationSleep(4);
    }

    private static Stream<Arguments> cityProvider() {
        return Stream.of(
                Arguments.of(Cities.KYIV_CITY),
                Arguments.of(Cities.HARKIV_CITY)
        );
    }

    @ParameterizedTest(name = "{index} => city={0}")
    @MethodSource("cityProvider")
    void checkCityClubs(Cities city) {
        ClubComponent ClubComponent = loadApplication()
                .gotoClubPage()
                .chooseCity(city)
                .getClubContainer()
                .getFirstClubComponent();
        //
        // Check first club address
        Assertions.assertTrue(ClubComponent.getAddressLabelText().contains(city.getCity()));
        presentationSleep();
    }


    private static Stream<Arguments> clubProvider0() {
        return Stream.of(
                Arguments.of(ClubContents.NEW_CADRE_CLUB),
                Arguments.of(ClubContents.VECTOR_CLUB)
        );
    }

    private static Stream<Arguments> clubProvider() {
        return Stream.of(
                Arguments.of(ClubRepository.get().vector())
        );
    }

    @ParameterizedTest(name = "{index} => clubContents={0}")
    @MethodSource("clubProvider")
    void checkClubExist(IClub club) {
        ClubComponent ClubComponent = loadApplication()
                .gotoClubPage()
                //.chooseCity(clubContents.getCity())
                .chooseCity(club.getLocation().getCity())
                .getClubContainer()
                .getClubComponentByPartialTitle(club.getName());
        //
        // Check club titles and descriptions
        Assertions.assertTrue(ClubComponent.getTitleLinkText().contains(club.getName()));
        presentationSleep();
    }

    @ParameterizedTest(name = "{index} => clubContents={0}")
    @MethodSource("clubProvider0")
    void checkAdvancedSearch(ClubContents clubContents) {
        AdvancedClubPage advancedClubPage = loadApplication()
                .gotoClubPage()
                .chooseCity(clubContents.getCity())
                .gotoAdvancedClubPage();
        //
        // Use pagination to search club
        Assertions.assertTrue(advancedClubPage.isExistClubByPartialTitle(clubContents.getTitle()));
        presentationSleep();
    }

    private static Stream<Arguments> commentProvider() {
        return Stream.of(
                Arguments.of(ClubContents.IT_EDUCATION_CLUB, CommentContents.FIRST_COMMENT)
        );
    }

    @ParameterizedTest(name = "{index} => clubContents={0}, commentContents={0}")
    @MethodSource("commentProvider")
    void checkCommentExist(ClubContents clubContents, CommentContents commentContents) {
        ClubDetailsPage clubDetailsPage = loadApplication()
                .gotoClubPage()
                .chooseCity(clubContents.getCity())
                .getClubContainer()
                .getClubComponentByPartialTitle(clubContents.getTitle())
                .openClubDetailsPage();
        //
        // Check comment exist
        Assertions.assertTrue(clubDetailsPage.getCommentsContainer()
                .isExistClubComponentByPartialAuthor(commentContents.getAuthor()));
        presentationSleep();
    }
}
