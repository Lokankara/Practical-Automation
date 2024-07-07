package com.softserve.edu.teachua.tests;

import com.softserve.edu.teachua.entity.Criteria;
import com.softserve.edu.teachua.tests.provider.CommentArgumentsProvider;
import com.softserve.edu.teachua.tests.provider.CriteriaArgumentsProvider;
import com.softserve.edu.teachua.tests.provider.UserInvalidArgumentsProvider;
import com.softserve.edu.teachua.tests.provider.UserValidArgumentsProvider;
import com.softserve.edu.teachua.entity.Comment;
import com.softserve.edu.teachua.entity.User;
import com.softserve.edu.teachua.pages.modal.LoginModal;
import com.softserve.edu.teachua.pages.page.HomePage;
import com.softserve.edu.teachua.pages.page.club.ClubNotFoundPage;
import com.softserve.edu.teachua.pages.page.club.ClubPage;
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

    @DisplayName("Test Successful Login with a valid user data, when successfully logging in and signing out, then verify login modal header")
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

    @DisplayName("Test Unsuccessful Login with invalid user credentials, when attempting to login, then verify error message")
    @ParameterizedTest(name = "{index} => email={0}")
    @ArgumentsSource(UserInvalidArgumentsProvider.class)
    void testUnsuccessfulLogin(User invalidUser) {
        final String message = "Error message should be displayed with email:" + invalidUser.email();

        LoginModal loginModal = loadApplication()
                .openLoginModal()
                .unsuccessfulLoginPage(invalidUser);

        Assertions.assertEquals(HomePage.ERROR_MASSAGE, loginModal.getErrorLoginMessage(), message);
    }

    @DisplayName("Test with valid user data, when logging in and accessing 'Add Club' modal from logged dropdown, then verify modal header and logout")
    @ParameterizedTest(name = "{index} => {0}")
    @ArgumentsSource(UserValidArgumentsProvider.class)
    void testLoggedDropdownAddClubModal(User validUser) {
        loadApplication()
                .openLoginModal()
                .successfulLogin(validUser)
                .assertSuccessfulLoginMessage()
                .openLoggedDropDown()
                .openDropdownLoggedAddClubs()
                .assertAddClubModalHeader()
                .closeAddClubModal()
                .openLoggedDropDown()
                .signOutUser();
    }

    @DisplayName("Given a logged-in user, when opening logged dropdown and accessing 'Add Center' modal, then verify modal header, and logout")
    @ParameterizedTest(name = "{index} => {0}")
    @ArgumentsSource(UserValidArgumentsProvider.class)
    void testLoggedDropdownAddCenterModal(User validUser) {
        loadApplication()
                .openLoginModal()
                .successfulLogin(validUser)
                .assertSuccessfulLoginMessage()
                .openLoggedDropDown()
                .openDropdownLoggedAddCenterModal()
                .assertAddCenterModalHeader()
                .closeCenterModal()
                .openLoggedDropDown()
                .signOutUser();
    }

    @DisplayName("Test Club Existence: Given search text 'Dream Team', when club page is loaded, then verify club title")
    @ParameterizedTest(name = "{index} => searchText={0}, clubTitle={1}")
    @CsvSource({"Dream Team, Школа танців Dream Team"})
    void testClubExist(String searchText, String clubTitle) {
        ClubPage clubPage = loadApplication()
                .successfulSearchClub(searchText);

        Assertions.assertEquals(clubTitle, clubPage.getClubTitleLinkText());
    }

    @DisplayName("Test Club Not Found: Given search text 'proba', when club is not found, then verify 'Not Found' message")
    @ParameterizedTest(name = "{index} => searchText={0}")
    @CsvSource({"proba"})
    void testClubNotExist(String searchText) {
        ClubNotFoundPage clubNotFoundPage = loadApplication()
                .unsuccessfulSearchClub(searchText);

        Assertions.assertEquals(ClubNotFoundPage.NOT_FOUND_MASSAGE, clubNotFoundPage.getNotFoundLabelText());
    }

    @DisplayName("Test Adding Comment When User Is Logged In: Given valid user, when successfully logging in, searching text, adding a comment, then verify the last comment and sign out")
    @ParameterizedTest(name = "{index} => {0}")
    @ArgumentsSource(UserValidArgumentsProvider.class)
    void testAddCommentWhenUserIsLogged(User user) {
        loadApplication()
                .openLoginModal()
                .successfulLogin(user)
                .successfulSearchClub(user.comment().searchText())
                .gotoClubDetailsPage()
                .openCommentModal()
                .submitComment(user.comment().commentText())
                .checkLastCommentText(user.comment().commentText())
                .openLoggedDropDown()
                .signOutUser();
    }

    @DisplayName("Test Adding Comment When User Is Unauthorized: Given comment content, when trying to add comment without login, then assert error message")
    @ParameterizedTest(name = "{index} => {0}")
    @ArgumentsSource(CommentArgumentsProvider.class)
    void testAddCommentWhenUserIsUnauthorized(Comment comment) {
        loadApplication()
                .successfulSearchClub(comment.searchText())
                .gotoClubDetailsPage()
                .openClubDetailsPage()
                .assertErrorLoginMessage();
    }

    @DisplayName("Test Existing Comment in Last Five: Given comment, when viewing club details, then verify comment exists in last five comments")
    @ParameterizedTest(name = "{index} => {0}")
    @ArgumentsSource(CommentArgumentsProvider.class)
    void testExistCommentInLastFiveWhenUserIsUnauthorized(Comment comment) {
        loadApplication()
                .successfulSearchClub(comment.searchText())
                .openClubInfoModal()
                .gotoClubDetailsPage()
                .assertClubDetailsHeader(comment.searchText())
                .assertLastComments(comment.commentText());
    }

    @DisplayName("Test Existing Comment in Last Ten: Given comment, when viewing club details and loading more comments, then verify comment exists in last ten comments")
    @ParameterizedTest(name = "{index} => {0}")
    @ArgumentsSource(CommentArgumentsProvider.class)
    void testExistCommentInLastTenWhenUserIsUnauthorized(Comment comment) {
        loadApplication()
                .successfulSearchClub(comment.searchText())
                .openClubInfoModal()
                .gotoClubDetailsPage()
                .assertClubDetailsHeader(comment.searchText())
                .showNextComments(5)
                .assertLastComments(comment.commentText());
    }

    @Test
    @DisplayName("Test Existing Comment in Last Ten: Given comment, when viewing club details and loading more comments, then verify comment exists in last ten comments")
    void testExistCommentInLastTenWhenUserIsUnauthorized() {
        Comment comment = new Comment("LESKIV-SCHOOL", "Look Good For Me");
        loadApplication()
                .successfulSearchClub(comment.searchText())
                .openClubInfoModal()
                .gotoClubDetailsPage()
                .assertClubDetailsHeader(comment.searchText())
                .showNextComments(10)
                .assertLastComments(comment.commentText());
    }

    @DisplayName("Test Existing Comment When User Is Logged: Given user, when successfully logging in, searching club details, checking comments, and signing out, then verify comment exists")
    @ParameterizedTest(name = "{index} => {0}")
    @ArgumentsSource(UserValidArgumentsProvider.class)
    void testExistCommentWhenUserIsLogged(User user) {
        loadApplication()
                .openLoginModal()
                .successfulLogin(user)
                .successfulSearchClub(user.comment().searchText())
                .gotoClubDetailsPage()
                .assertClubDetailsHeader(user.comment().searchText())
                .assertLastComments(user.comment().commentText())
                .openLoggedDropDown()
                .signOutUser();
    }

    @DisplayName("Test Club Info Modal: Given comment, when searching club details, opening info modal, checking info modal header, navigating to club details page, checking club details header, and verifying last comments")
    @ParameterizedTest(name = "{index} => searchText={0}")
    @ArgumentsSource(CommentArgumentsProvider.class)
    void testClubInfoModal(Comment comment) {
        loadApplication()
                .successfulSearchClub(comment.searchText())
                .openClubInfoModal()
                .checkInfoModalHeader(comment.searchText())
                .gotoClubDetailsPage()
                .assertClubDetailsHeader(comment.searchText())
                .assertLastComments(comment.commentText());
    }

    @ParameterizedTest(name = "{index} => searchText={0}")
    @DisplayName("Test Select Club by City and Check Info Modal when selecting city clubs, searching club details, opening info modal, checking info modal header, navigating to club details page, and verifying club details header")
    @ArgumentsSource(CriteriaArgumentsProvider.class)
    void testSelectClubByCityAndCheckInfoModal(Criteria criteria) {
        loadApplication()
                .selectCityClubs(criteria.city())
                .successfulSearchClub(criteria.club())
                .openClubInfoModal()
                .checkInfoModalHeader(criteria.club())
                .gotoClubDetailsPage()
                .assertClubDetailsHeader(criteria.club());
    }
}
