package com.softserve.edu.teachua.tests.e2e;

import com.softserve.edu.teachua.dao.club.model.ClubDto;
import com.softserve.edu.teachua.dao.comment.model.CreatedFeedbackRequestDto;
import com.softserve.edu.teachua.dao.comment.model.CreatedFeedbackResponseDto;
import com.softserve.edu.teachua.dao.user.model.LoggedUserDto;
import com.softserve.edu.teachua.data.Cities;
import com.softserve.edu.teachua.data.user.IUser;
import com.softserve.edu.teachua.data.user.UserRepository;
import com.softserve.edu.teachua.pages.club.CommentsContainer;
import com.softserve.edu.teachua.pages.menu.HomePage;
import com.softserve.edu.teachua.pages.top.TopPart;
import com.softserve.edu.teachua.service.facade.CommentFacade;
import com.softserve.edu.teachua.service.facade.UserFacade;
import com.softserve.edu.teachua.tools.PositiveFeedbackGenerator;
import com.softserve.edu.teachua.wraps.browser.DriverUtils;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;

import java.util.List;
import java.util.Random;

import static com.softserve.edu.teachua.tools.ResourceUtils.NEW_USER_JSON;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Execution(ExecutionMode.SAME_THREAD)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CommentE2ETest {
    private final CommentFacade commentService = new CommentFacade();
    private final UserFacade userService = new UserFacade();
    private static final long CLUB_ID = 27L;

    @AfterAll
    public static void afterAll() {
        DriverUtils.quit();
    }

    @Test
    @Order(0)
    void checkExistUserCreateCommentByAssuredAndCheckIfExistBySelenium() {
        CreatedFeedbackRequestDto dto = new CreatedFeedbackRequestDto(
                11L, 5, PositiveFeedbackGenerator.generateFeedback(), 198L, CLUB_ID);

        ClubDto club = commentService.getClubById(dto.clubId());
        CreatedFeedbackResponseDto comment = commentService.createFeedback(dto).as(CreatedFeedbackResponseDto.class);
        String actual = commentService.getFirstComment(club.name());

        Assertions.assertEquals(actual, comment.text());
        Assertions.assertTrue(commentService.deleteCommentByDao(comment.id()));
    }

    @Test
    void checkGetAllClubsByCityAndLeaveCommentInRandomClub() {
        final Cities club = Cities.KYIV_CITY;
        List<ClubDto> clubs = commentService.getClubsByCity(club.getCity());
        Assertions.assertFalse(clubs.isEmpty(), "Clubs list is empty");

        ClubDto randomClub = clubs.get(new Random().nextInt(clubs.size()));
        ClubDto grandClub = commentService.getClubById(randomClub.id());
        String[] nameParts = grandClub.name().split("\\s+");
        String namePart = nameParts[0] + " " + nameParts[1];

        CreatedFeedbackRequestDto dto = new CreatedFeedbackRequestDto(
                0, 5, PositiveFeedbackGenerator.generateFeedback(), 198L, grandClub.id());

        CreatedFeedbackResponseDto comment = commentService.createFeedback(dto).as(CreatedFeedbackResponseDto.class);
        String actual = commentService.getFirstComment(namePart);

        Assertions.assertEquals(actual, comment.text());
        Assertions.assertTrue(commentService.deleteCommentByDao(comment.id()));
    }

    @Test
    @Order(5)
    void checkCreateNewUserAndLogged() {
        userService.activateSignUpUser(LoggedUserDto.createUser());
        IUser user = UserRepository.get().newUserFromJson(NEW_USER_JSON);

        assertTrue(userService.activateUserById(user.getId()));
        HomePage homePage = userService.successfulLogin(user);

        Assertions.assertEquals(TopPart.POPUP_MESSAGE_SUCCESSFULLY, homePage.getPopupMessageLabelText());
        Assertions.assertTrue(homePage.getTeachLabelText().contains(HomePage.BEGIN_TEACH_LABEL_MESSAGE));
    }

    @Test
    @Order(6)
    void checkCreateLeaveCommentInClubWhenUserLoggedAndCheckIfCommentExist() {
        IUser userFromJson = UserRepository.get().newUserFromJson(NEW_USER_JSON);
        CreatedFeedbackRequestDto requestDto = new CreatedFeedbackRequestDto(
                0, 5, PositiveFeedbackGenerator.generateFeedback(), userFromJson.getId(), CLUB_ID);

        ClubDto club = commentService.getClubById(CLUB_ID);
        CreatedFeedbackResponseDto dto = commentService.createFeedback(requestDto).as(CreatedFeedbackResponseDto.class);
        CommentsContainer comments = commentService.findComments(club);

        Assertions.assertTrue(comments.isExistClubComponentByPartialAuthor(String.format("%s %s", dto.user().firstName(), dto.user().lastName())));
        Assertions.assertEquals(dto.text(), comments.getFirstCommentComponent().getCommentLabelText());

        Assertions.assertTrue(commentService.deleteCommentByDao(dto.id()));
        Assertions.assertTrue(userService.deleteUserById(userFromJson.getId()));
    }
}
