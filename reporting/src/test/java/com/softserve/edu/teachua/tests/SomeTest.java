package com.softserve.edu.teachua.tests;

import com.softserve.edu.teachua.data.Challengies;
import com.softserve.edu.teachua.data.Cities;
import com.softserve.edu.teachua.data.ClubContents;
import com.softserve.edu.teachua.data.CommentContents;
import com.softserve.edu.teachua.data.IUser;
import com.softserve.edu.teachua.data.UrlContents;
import com.softserve.edu.teachua.data.UserRepository;
import com.softserve.edu.teachua.pages.challenge.ChallengeTeachPage;
import com.softserve.edu.teachua.pages.challenge.YoutubeFrame;
import com.softserve.edu.teachua.pages.club.AdvancedClubPage;
import com.softserve.edu.teachua.pages.club.ClubComponent;
import com.softserve.edu.teachua.pages.club.ClubDetailsPage;
import com.softserve.edu.teachua.pages.menu.HomePage;
import com.softserve.edu.teachua.pages.top.TopPart;
import com.softserve.edu.teachua.pages.user.LoginModal;
import io.qameta.allure.Allure;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Link;
import io.qameta.allure.Owner;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;

import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

@Epic("User Interaction Tests")
@Execution(ExecutionMode.CONCURRENT)
public class SomeTest extends TestRunner {

    @Test
    @Owner("Lokankara")
    @Epic("Web interface")
    @Feature("Smoke Tests")
    @Severity(SeverityLevel.NORMAL)
    @Story("Verify basic navigation")
    @DisplayName("Verify basic functionality on the home page")
    @Description("Verify navigation through application pages and check teach label text")
    @Link(name = "Website", url = "http://speak-ukrainian.eastus2.cloudapp.azure.com/dev/")
    public void checkSmoke() {
        Allure.step("Load application and navigate through pages", () -> {
            HomePage homePage = loadApplication()
                    .gotoClubPage()
                    .gotoNewsPage()
                    .gotoAboutUsPage()
                    .gotoUkrainianServicePage()
                    .gotoHomePage();

            Allure.step("Expected result: Verify that the teach label text contains the expected message" + HomePage.BEGIN_TEACH_LABEL_MESSAGE, () -> {
                String teachLabelText = homePage.getTeachLabelText();
                Assertions.assertTrue(teachLabelText.contains(HomePage.BEGIN_TEACH_LABEL_MESSAGE),
                        "Expected teach label text to contain: " + HomePage.BEGIN_TEACH_LABEL_MESSAGE);
            });
        });
    }

    private static Stream<Arguments> validUserProvider() {
        return Stream.of(
                Arguments.of(UserRepository.get().customer())
        );
    }

    @Owner("Lokankara")
    @Story("User login")
    @Epic("Web interface")
    @Feature("Login User")
    @Severity(SeverityLevel.CRITICAL)
    @MethodSource("validUserProvider")
    @DisplayName("Check Successful Login")
    @ParameterizedTest(name = "{displayName} ({argumentsWithNames})")
    @Description("Verify successful login with valid user credentials")
    @Link(name = "Website", url = "http://speak-ukrainian.eastus2.cloudapp.azure.com/dev/")
    public void checkSuccessfulLogin(IUser user) {
        final String email = user.getEmail();
        final String password = user.getPassword();

        Allure.step("Load application, open login modal, and perform successful login", () -> {
            HomePage homePage = loadApplication()
                    .openLoginModal()
                    .successfulLogin(email, password);

            Allure.step("Expected result: Verify pop-up message after login - Expected: " + TopPart.POPUP_MESSAGE_SUCCESSFULLY, () -> {
                String popupMessage = homePage.getPopupMessageLabelText();
                Assertions.assertEquals(TopPart.POPUP_MESSAGE_SUCCESSFULLY, popupMessage,
                        "Expected pop-up message: " + TopPart.POPUP_MESSAGE_SUCCESSFULLY);
            });

            Allure.step("Expected result: Verify teach label text after login - Expected to contain: " + HomePage.BEGIN_TEACH_LABEL_MESSAGE, () -> {
                Assertions.assertTrue(homePage.getTeachLabelText().contains(HomePage.BEGIN_TEACH_LABEL_MESSAGE),
                        "Expected teach label text to contain: " + HomePage.BEGIN_TEACH_LABEL_MESSAGE);
            });

            HomePage signedOutHomePage = homePage.signOutUser();

            Allure.step("Expected result: Sign out user and verify user is logged out - Expected: User is logged out", () -> {
                Assertions.assertFalse(signedOutHomePage.isUserLogged(), "Expected user to be logged out");
            });
        });
    }

    private static Stream<Arguments> invalidUserProvider() {
        return Stream.of(
                Arguments.of(UserRepository.get().invalidUser())
        );
    }

    @Owner("Lokankara")
    @Story("User login")
    @Epic("Web interface")
    @Feature("Unsuccessful Login")
    @Severity(SeverityLevel.NORMAL)
    @MethodSource("invalidUserProvider")
    @DisplayName("Check Unsuccessful Login")
    @ParameterizedTest(name = "{displayName} ({argumentsWithNames})")
    @Description("Verify unsuccessful login with invalid user credentials")
    @Link(name = "Website", url = "http://speak-ukrainian.eastus2.cloudapp.azure.com/dev/")
    public void checkUnsuccessfulLogin(IUser user) {
        Allure.step("Load application, open login modal, and perform unsuccessful login", () -> {
            LoginModal loginModal = loadApplication()
                    .openLoginModal()
                    .unsuccessfulLoginPage(user.getEmail(), user.getPassword());
            String popupMessage = loginModal.getPopupMessageLabelText();

            Allure.step("Expected result: Check pop-up message: " + LoginModal.POPUP_MESSAGE_UNSUCCESSFULLY, () ->
                    Assertions.assertEquals(LoginModal.POPUP_MESSAGE_UNSUCCESSFULLY, popupMessage,
                            "Expected pop-up message: " + LoginModal.POPUP_MESSAGE_UNSUCCESSFULLY));
        });
    }

    private static Stream<Arguments> challengeTeachProvider() {
        return Stream.of(
                Arguments.of(Challengies.TO_LEARN_CHALLENGE, UrlContents.WEBINAR_IFRAME)
        );
    }

    @Owner("Lokankara")
    @Epic("Web interface")
    @Feature("Challenges")
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("Check Challenge")
    @MethodSource("challengeTeachProvider")
    @Story("Verify challenge playback and navigation")
    @ParameterizedTest(name = "{index} => challengeName={0}")
    @Description("Verify challenge details and YouTube frame content")
    @Link(name = "Website", url = "http://speak-ukrainian.eastus2.cloudapp.azure.com/dev/")
    public void checkChallenge(Challengies challengeName, UrlContents urlContents) {
        Allure.step("Load application, navigate to challenge page and play video content", () -> {
            YoutubeFrame youtubeFrame = loadApplication()
                    .gotoChallengePage(challengeName, ChallengeTeachPage.class)
                    .gotoYoutubeFrame()
                    .playVideoContent();

            Allure.step("Expected result: Check YouTube frame content - Expected to contain: " + urlContents.getSearchVideo(), () ->
                    Assertions.assertTrue(youtubeFrame.getYoutubeLinkText().contains(urlContents.getSearchVideo()),
                            "Expected YouTube link text to contain: " + urlContents.getSearchVideo()));

            Allure.step("Navigate back to challenge teach page and then to home page", () -> {
                HomePage homePage = youtubeFrame
                        .gotoChallengeTeachPage()
                        .gotoHomePage();
            });
        });
    }

    private static Stream<Arguments> cityProvider() {
        return Stream.of(
                Arguments.of(Cities.KYIV_CITY),
                Arguments.of(Cities.HARKIV_CITY)
        );
    }

    @Owner("Lokankara")
    @Epic("Web interface")
    @Feature("City Clubs")
    @MethodSource("cityProvider")
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("Check City Clubs")
    @Story("Verify club address information")
    @ParameterizedTest(name = "{index} => city={0}")
    @Description("Verify city-specific club details")
    @Link(name = "Website", url = "http://speak-ukrainian.eastus2.cloudapp.azure.com/dev/")
    public void checkCityClubs(Cities city) {
        Allure.step("Load application and navigate to club page", () -> {
            ClubComponent clubComponent = loadApplication()
                    .gotoClubPage()
                    .chooseCity(city)
                    .getClubContainer()
                    .getFirstClubComponent();

            Allure.step("Expected result: Verify club address contains city name - Expected: " + city.getCity(), () -> {
                Assertions.assertTrue(clubComponent.getAddressLabelText().contains(city.getCity()),
                        "Actual club address: " + clubComponent.getAddressLabelText());
            });
        });
    }

    private static Stream<Arguments> clubProvider() {
        return Stream.of(
                Arguments.of(ClubContents.NEW_CADRE_CLUB),
                Arguments.of(ClubContents.VECTOR_CLUB)
        );
    }

    @Owner("Lokankara")
    @Feature("Club Exist")
    @Epic("Web interface")
    @MethodSource("clubProvider")
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("Check Club Existence")
    @Story("Verify club title and description")
    @ParameterizedTest(name = "{index} => clubContents={0}")
    @Description("Verify club existence based on provided club details")
    @Link(name = "Website", url = "http://speak-ukrainian.eastus2.cloudapp.azure.com/dev/")
    public void checkClubExist(ClubContents clubContents) {
        final ClubContents finalClubContents = clubContents;
        Allure.step("Load application, navigate to club page, choose city, and get club component by title", () -> {
            ClubComponent clubComponent = loadApplication()
                    .gotoClubPage()
                    .chooseCity(finalClubContents.getCity())
                    .getClubContainer()
                    .getClubComponentByPartialTitle(finalClubContents.getTitle());

            Allure.step("Expected result: Check club title - Expected to contain: " + finalClubContents.getTitle(), () ->
                    Assertions.assertTrue(clubComponent.getTitleLinkText().contains(finalClubContents.getTitle()),
                            "Actual club title: " + clubComponent.getTitleLinkText()));
        });
    }

    @Owner("Lokankara")
    @Epic("Web interface")
    @Feature("Advanced Search")
    @MethodSource("clubProvider")
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("Check Advanced Club Search")
    @Story("Verify advanced search with pagination")
    @ParameterizedTest(name = "{index} => clubContents={0}")
    @Description("Verify advanced club search functionality")
    @Link(name = "Website", url = "http://speak-ukrainian.eastus2.cloudapp.azure.com/dev/")
    public void checkAdvancedSearch(ClubContents clubContents) {
        final ClubContents finalClubContents = clubContents;
        Allure.step("Load application, navigate to club page, choose city, and perform advanced search", () -> {
            AdvancedClubPage advancedClubPage = loadApplication()
                    .gotoClubPage()
                    .chooseCity(finalClubContents.getCity())
                    .gotoAdvancedClubPage();

            Allure.step("Expected result: Check club existence in advanced search with title: " + finalClubContents.getTitle(), () ->
                    Assertions.assertTrue(advancedClubPage.isExistClubByPartialTitle(finalClubContents.getTitle()),
                            "Expected club to exist in advanced search with title: " + finalClubContents.getTitle()));
        });
    }

    private static Stream<Arguments> commentProvider() {
        return Stream.of(
                Arguments.of(ClubContents.IT_EDUCATION_CLUB, CommentContents.FIRST_COMMENT)
        );
    }

    @Owner("Lokankara")
    @Epic("Web interface")
    @Feature("Comment Exist")
    @Severity(SeverityLevel.MINOR)
    @MethodSource("commentProvider")
    @DisplayName("Check Comment Existence")
    @Story("Verify comment display and author details")
    @Description("Verify comment existence on club details page")
    @ParameterizedTest(name = "{index} => clubContents={0}, commentContents={1}")
    @Link(name = "Website", url = "http://speak-ukrainian.eastus2.cloudapp.azure.com/dev/")
    public void checkCommentExist(ClubContents clubContents, CommentContents commentContents) {
        final ClubContents finalClubContents = clubContents;
        final CommentContents finalCommentContents = commentContents;
        Allure.step("Load application, navigate to club page, choose city, and open club details page", () -> {
            ClubDetailsPage clubDetailsPage = loadApplication()
                    .gotoClubPage()
                    .chooseCity(finalClubContents.getCity())
                    .getClubContainer()
                    .getClubComponentByPartialTitle(finalClubContents.getTitle())
                    .openClubDetailsPage();

            Allure.step("Expected result: Check comment existence by author: " + finalCommentContents.getAuthor(), () ->
                    Assertions.assertTrue(clubDetailsPage.getCommentsContainer()
                                    .isExistClubComponentByPartialAuthor(finalCommentContents.getAuthor()),
                            "Expected comment to exist by author: " + finalCommentContents.getAuthor()));
        });
    }
}
