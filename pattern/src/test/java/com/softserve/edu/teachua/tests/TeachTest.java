package com.softserve.edu.teachua.tests;

import com.softserve.edu.teachua.data.club.Club;
import com.softserve.edu.teachua.data.club.ClubRepository;
import com.softserve.edu.teachua.data.user.IUser;
import com.softserve.edu.teachua.data.user.UserRepository;
import com.softserve.edu.teachua.pages.club.ClubComponent;
import com.softserve.edu.teachua.pages.club.ClubDetailsPage;
import com.softserve.edu.teachua.pages.club.ClubNotFoundPage;
import com.softserve.edu.teachua.pages.menu.HomePage;
import com.softserve.edu.teachua.pages.top.TopPart;
import com.softserve.edu.teachua.pages.user.LoginModal;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

@Execution(ExecutionMode.CONCURRENT)
class TeachTest extends TestRunner {

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

    @ParameterizedTest(name = "{index} => User={0}")
    @MethodSource("validUserProvider")
    void checkSuccessfulLogin(IUser validUser) {

        HomePage homePage = loadApplication()
                .openLoginModal()
                .successfulLogin(validUser);
        String popupMessage = homePage.getPopupMessageLabelText();

        Assertions.assertEquals(TopPart.POPUP_MESSAGE_SUCCESSFULLY, popupMessage);

        Assertions.assertTrue(homePage.getTeachLabelText().contains(HomePage.BEGIN_TEACH_LABEL_MESSAGE));
        homePage = homePage.signOutUser();
        Assertions.assertFalse(homePage.isUserLogged());
    }

    private static Stream<Arguments> invalidUserProvider() {
        return Stream.of(Arguments.of(UserRepository.get().invalidUser()));
    }

    @ParameterizedTest(name = "{index} => email={0}, password={1}")
    @MethodSource("invalidUserProvider")
    void checkUnsuccessfulLogin(IUser invalidUser) {
        LoginModal loginModal = loadApplication()
                .openLoginModal()
                .unsuccessfulLoginPage(invalidUser);
        String popupMessage = loginModal.getPopupMessageLabelText();

        Assertions.assertEquals(LoginModal.POPUP_MESSAGE_UNSUCCESSFULLY, popupMessage);
    }

    private static Stream<Arguments> clubProvider() {
        return Stream.of(
                Arguments.of(ClubRepository.get().spasClub())
        );
    }

    @ParameterizedTest(name = "{index} => searchText={0}")
    @MethodSource("clubProvider")
    void checkClubExist(Club club) {
        ClubComponent clubComponent = loadApplication()
                .successfulSearchClub(club.getCityName())
                .getClubContainer()
                .getClubComponentByPartialTitle(club.getCityName());

        Assertions.assertEquals(club.getTitle(), clubComponent.getTitleLinkText());
    }

    @ParameterizedTest(name = "{index} => searchText={0}")
    @CsvSource({"proba"})
    void checkClubNotExist(String searchText) {
        ClubNotFoundPage clubNotFoundPage = loadApplication()
                .unsuccessfulSearchClub(searchText);

        Assertions.assertEquals(ClubNotFoundPage.NOT_FOUND_MASSAGE, clubNotFoundPage.getNotFoundLabelText());
    }

    @ParameterizedTest(name = "{index} => user={0}")
    @MethodSource("validUserProvider")
    void checkAddComment(IUser validUser) {
        ClubDetailsPage clubDetailsPage = loadApplication()
                .openLoginModal()
                .successfulLogin(validUser)
                .successfulSearchClub(validUser.getComments().get(0).getClub())
                .getClubContainer()
                .getClubComponentByPartialTitle(validUser.getComments().get(0).getClub())
                .openClubDetailsPage()
                .openClubCommentModal()
                .submitComment(validUser.getComments().get(0).getText());

        Assertions.assertEquals(validUser.getComments().get(0).getText(),
                clubDetailsPage.getCommentsContainer().getFirstCommentComponent().getCommentLabelText());

        HomePage homePage = clubDetailsPage.signOutUser();
        Assertions.assertFalse(homePage.isUserLogged());
    }

    @ParameterizedTest(name = "{index} => searchText={0}, commentText={1}")
    @CsvSource({"IT освіта: курси, Проба Коментар44"})
    void checkExistComment(String searchText, String commentText) {
        ClubDetailsPage clubDetailsPage = loadApplication()
                .successfulSearchClub(searchText)
                .getClubContainer()
                .getClubComponentByPartialTitle(searchText)
                .openClubInfoModal()
                .gotoClubDetailsPage();

        System.out.println("\tclubDetailsPage.getCommentContentLabelText() = "
                + clubDetailsPage.getCommentsContainer().getFirstCommentComponent().getCommentLabelText());
        Assertions.assertEquals(commentText, clubDetailsPage
                .getCommentsContainer().getFirstCommentComponent().getCommentLabelText());

        HomePage homePage = clubDetailsPage.gotoHomePage();
        Assertions.assertFalse(homePage.isUserLogged());
    }
}
