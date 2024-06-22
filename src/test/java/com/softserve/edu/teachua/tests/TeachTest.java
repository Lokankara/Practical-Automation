package com.softserve.edu.teachua.tests;

import com.softserve.edu.provider.CommentArgumentsProvider;
import com.softserve.edu.provider.UserInvalidArgumentsProvider;
import com.softserve.edu.provider.UserValidArgumentsProvider;
import com.softserve.edu.teachua.entity.Comment;
import com.softserve.edu.teachua.entity.User;
import com.softserve.edu.teachua.modal.LoginModal;
import com.softserve.edu.teachua.page.HomePage;
import com.softserve.edu.teachua.page.club.ClubNotFoundPage;
import com.softserve.edu.teachua.page.club.ClubPage;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.junit.jupiter.params.provider.CsvSource;

class TeachTest extends BaseTest {
    @AfterEach
    void afterEach(final TestInfo testInfo) {
        deleteCookies();
        clearStorage();
        logger.info(testInfo.getDisplayName());
    }

    @BeforeEach
    void beforeEach() {
        getDriver().get(BASE_URL);
    }

    protected HomePage loadApplication() {
        return new HomePage(getDriver());
    }

    @Test
    @DisplayName("Smoke test")
    void testSmoke() {
        HomePage homePage = loadApplication()
                .gotoClubPage()
                .gotoNewsPage()
                .gotoAboutUsPage()
                .gotoUkrainianServicePage()
                .gotoHomePage();

        Assertions.assertTrue(homePage.getTeachLabelText().contains(HomePage.BEGIN_TEACH_LABEL_MESSAGE));
    }

    @DisplayName("Test Successful Login")
    @ParameterizedTest(name = "{index} => {0}")
    @ArgumentsSource(UserValidArgumentsProvider.class)
    void testSuccessfulLogin(User validUser) {
        LoginModal loginModal = loadApplication()
                .openLoginModal()
                .successfulLogin(validUser)
                .assertSuccessfulLoginMessage()
                .openLoggedDropDown()
                .signOutUser()
                .openLoginModal();

        Assertions.assertEquals(LoginModal.LOGIN_HEADER, loginModal.getLoginHeader());
    }

    @DisplayName("Test Unsuccessful Login")
    @ParameterizedTest(name = "{index} => email={0}")
    @ArgumentsSource(UserInvalidArgumentsProvider.class)
    void testUnsuccessfulLogin(User invalidUser) {
        final String message = "Error message should be displayed with email:" + invalidUser.email();

        LoginModal loginModal = loadApplication()
                .openLoginModal()
                .unsuccessfulLoginPage(invalidUser);

        Assertions.assertEquals(HomePage.ERROR_MASSAGE, loginModal.getErrorLoginMessage(), message);
    }

    @DisplayName("Test logged dropdown add club modal")
    @ParameterizedTest(name = "{index} => {0}")
    @ArgumentsSource(UserValidArgumentsProvider.class)
    void testLoggedDropdownAddClubModal(User validUser) {
        loadApplication()
                .openLoginModal()
                .successfulLogin(validUser)
                .assertSuccessfulLoginMessage()
                .openLoggedDropDown()
                .openDropdownLoggedAddClubs()
                .checkAddClubModalHeader()
                .closeAddClubModal()
                .openLoggedDropDown()
                .signOutUser();
    }

    @DisplayName("Test logged dropdown add club modal")
    @ParameterizedTest(name = "{index} => {0}")
    @ArgumentsSource(UserValidArgumentsProvider.class)
    void testLoggedDropdownAddCenterModal(User validUser) {
        loadApplication()
                .openLoginModal()
                .successfulLogin(validUser)
                .assertSuccessfulLoginMessage()
                .openLoggedDropDown()
                .openDropdownLoggedAddCenterModal()
                .checkAddCenterModalHeader()
                .closeCenterModal()
                .openLoggedDropDown()
                .signOutUser();
    }

    @DisplayName("Check Club Exist")
    @ParameterizedTest(name = "{index} => searchText={0}, clubTitle={1}")
    @CsvSource({"Dream Team, Школа танців Dream Team"})
    void testClubExist(String searchText, String clubTitle) {
        ClubPage clubPage = loadApplication()
                .successfulSearchClub(searchText);

        Assertions.assertEquals(clubTitle, clubPage.getClubTitleLinkText());
    }

    @DisplayName("Check club NotFound LabelText")
    @ParameterizedTest(name = "{index} => searchText={0}")
    @CsvSource({"proba"})
    void testClubNotExist(String searchText) {
        ClubNotFoundPage clubNotFoundPage = loadApplication()
                .unsuccessfulSearchClub(searchText);

        Assertions.assertEquals(ClubNotFoundPage.NOT_FOUND_MASSAGE, clubNotFoundPage.getNotFoundLabelText());
    }

    @DisplayName("Check Add Comment")
    @ParameterizedTest(name = "{index} => {0}")
    @ArgumentsSource(UserValidArgumentsProvider.class)
    void testAddCommentWhenUserIsLogged(User user) {
        loadApplication()
                .openLoginModal()
                .successfulLogin(user)
                .successfulSearchClub("IT освіта: курси")
                .gotoClubDetailsPage()
                .openCommentModal()
                .submitComment(user.comment().commentText())
                .checkLastCommentText(user.comment().commentText())
                .openLoggedDropDown()
                .signOutUser();
    }

    @DisplayName("Check Add Comment")
    @ParameterizedTest(name = "{index} => {0}")
    @ArgumentsSource(CommentArgumentsProvider.class)
    void testAddCommentWhenUserIsUnauthorized(Comment comment) {
        loadApplication()
                .successfulSearchClub(comment.searchText())
                .gotoClubDetailsPage()
                .tryOpenCommentModal()
                .assertErrorLoginMessage();
    }

    @DisplayName("Check Exist Comment")
    @ParameterizedTest(name = "{index} => {0}")
    @ArgumentsSource(CommentArgumentsProvider.class)
    void testExistCommentInLastFiveWhenUserIsUnauthorized(Comment comment) {
        loadApplication()
                .successfulSearchClub(comment.searchText())
                .openClubInfoModal()
                .gotoClubDetailsPage()
                .checkClubDetailsHeader(comment.searchText())
                .checkLastComments(comment.commentText());
    }

    @DisplayName("Check Exist Comment")
    @ParameterizedTest(name = "{index} => {0}")
    @ArgumentsSource(CommentArgumentsProvider.class)
    void testExistCommentInLastTenWhenUserIsUnauthorized(Comment comment) {
        loadApplication()
                .successfulSearchClub(comment.searchText())
                .openClubInfoModal()
                .gotoClubDetailsPage()
                .checkClubDetailsHeader(comment.searchText())
                .showMore(5)
                .checkLastComments(comment.commentText());
    }

    @DisplayName("Check Add Comment")
    @ParameterizedTest(name = "{index} => {0}")
    @ArgumentsSource(UserValidArgumentsProvider.class)
    void testExistCommentWhenUserIsLogged(User user) {
        loadApplication()
                .openLoginModal()
                .successfulLogin(user)
                .successfulSearchClub(user.comment().searchText())
                .gotoClubDetailsPage()
                .checkClubDetailsHeader(user.comment().searchText())
                .checkLastComments(user.comment().commentText())
                .openLoggedDropDown()
                .signOutUser();
    }

    @DisplayName("Check Exist Comment")
    @ParameterizedTest(name = "{index} => searchText={0}")
    @ArgumentsSource(CommentArgumentsProvider.class)
    void testClubInfoModal(Comment comment) {
        loadApplication()
                .successfulSearchClub(comment.searchText())
                .openClubInfoModal()
                .checkInfoModalHeader(comment.searchText())
                .gotoClubDetailsPage()
                .checkClubDetailsHeader(comment.searchText())
                .checkLastComments(comment.commentText());
    }
}
