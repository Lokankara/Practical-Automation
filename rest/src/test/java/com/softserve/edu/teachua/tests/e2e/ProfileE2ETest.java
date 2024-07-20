package com.softserve.edu.teachua.tests.e2e;

import com.softserve.edu.teachua.data.user.IUser;
import com.softserve.edu.teachua.data.user.UserRepository;
import com.softserve.edu.teachua.pages.menu.HomePage;
import com.softserve.edu.teachua.pages.top.TopPart;
import com.softserve.edu.teachua.pages.user.LoginModal;
import com.softserve.edu.teachua.service.facade.UserFacade;
import com.softserve.edu.teachua.dao.user.model.LoggedUserDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;

import static com.softserve.edu.teachua.tools.ResourceUtils.NEW_USER_JSON;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Execution(ExecutionMode.SAME_THREAD)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ProfileE2ETest {
    private final UserFacade userService = new UserFacade();

    @Test
    @Order(0)
    void checkAuthorizationUserWithUnapprovedUser() {
        LoggedUserDto randomUser = LoggedUserDto.createUser();
        LoggedUserDto loggedUserDto = userService.signUpUser(randomUser);
        IUser user = UserRepository.get().userFromDBById(loggedUserDto.id());
        String message = userService.unsuccessfulLogin(user);

        Assertions.assertNotNull(loggedUserDto);
        Assertions.assertEquals(randomUser.email(), loggedUserDto.email());
        Assertions.assertEquals(LoginModal.POPUP_MESSAGE_UNSUCCESSFULLY, message);
    }

    @Test
    @Order(1)
    void checkAuthorizationUserWithApprovedUser() {
        IUser user = UserRepository.get().newUserFromJson(NEW_USER_JSON);
        final int id = user.getId();

        assertTrue(userService.activateUserById(id), String.format("ID: %d", id));
        HomePage homePage = userService.successfulLogin(user);

        Assertions.assertEquals(TopPart.POPUP_MESSAGE_SUCCESSFULLY, homePage.getPopupMessageLabelText());
        Assertions.assertTrue(homePage.getTeachLabelText().contains(HomePage.BEGIN_TEACH_LABEL_MESSAGE));
        Assertions.assertFalse(homePage.signoutUser().isUserLogged());
        Assertions.assertTrue(userService.deleteUserById(id));
    }

    @Test
    @Order(2)
    void checkAuthorizationUser() {
        LoggedUserDto randomUser = LoggedUserDto.createUser();
        LoggedUserDto loggedUserDto = userService.activateSignUpUser(randomUser);
        IUser user = UserRepository.get().newUserFromJson(NEW_USER_JSON);
        final int id = loggedUserDto.id();

        String message = userService.unsuccessfulLogin(user);
        Assertions.assertEquals(LoginModal.POPUP_MESSAGE_UNSUCCESSFULLY, message);

        assertTrue(userService.activateUserById(id), String.format("ID: %d", id));
        HomePage homePage = userService.successfulLogin(user);

        Assertions.assertEquals(TopPart.POPUP_MESSAGE_SUCCESSFULLY, homePage.getPopupMessageLabelText());
        Assertions.assertTrue(homePage.getTeachLabelText().contains(HomePage.BEGIN_TEACH_LABEL_MESSAGE));
        Assertions.assertFalse(homePage.signoutUser().isUserLogged());
        Assertions.assertTrue(userService.deleteUserById(user.getId()));
    }
}
