package com.softserve.edu.teachua.service.facade;

import com.softserve.edu.teachua.data.user.IUser;
import com.softserve.edu.teachua.pages.menu.HomePage;
import com.softserve.edu.teachua.service.assured.user.UserAssuredService;
import com.softserve.edu.teachua.service.assured.user.UserRestAssuredService;
import com.softserve.edu.teachua.service.dao.user.UserDaoService;
import com.softserve.edu.teachua.service.dao.user.UserJdbcDaoService;
import com.softserve.edu.teachua.service.selenium.user.UserLoginSeleniumService;
import com.softserve.edu.teachua.dao.user.model.SignInResponse;
import com.softserve.edu.teachua.dao.user.model.LoggedUserDto;
import io.restassured.path.json.JsonPath;
import org.jetbrains.annotations.NotNull;

public class UserFacade {

    private final UserDaoService daoService;
    private final UserAssuredService assuredService;
    private final UserLoginSeleniumService seleniumService;

    public UserFacade() {
        daoService = new UserJdbcDaoService();
        seleniumService = new UserLoginSeleniumService();
        assuredService = new UserRestAssuredService();
    }

    public LoggedUserDto getUserById(int id) {
        return daoService.findById(id);
    }

    public boolean disableUserById(int id) {
        return daoService.disableById(id);
    }

    public boolean activateUserById(int id) {
        return daoService.activateById(id);
    }

    public boolean deleteUserById(int id) {
        boolean isDeleted = daoService.deleteById(id);
        if (isDeleted) {
            daoService.saveToFile(LoggedUserDto.empty());
        }
        return isDeleted;
    }

    public LoggedUserDto activateSignUpUser(LoggedUserDto user) {
        SignInResponse response = assuredService.signUpUser(user);
        activateUserById(response.id());
        LoggedUserDto loggedUserDto = updateProfileUser(user);
        return saveUserToFile(user, loggedUserDto.id());
    }

    private LoggedUserDto updateProfileUser(LoggedUserDto user) {
        JsonPath jsonPath = assuredService.signInUser(user);
        return assuredService.updateProfile(jsonPath, user);
    }

    public String unsuccessfulLogin(IUser user) {
        return seleniumService.unsuccessfulLogin(user);
    }

    public HomePage successfulLogin(IUser user) {
        return seleniumService.successfulLogin(user);
    }

    public LoggedUserDto signUpUser(LoggedUserDto newUser) {
        SignInResponse response = assuredService.signUpUser(newUser);
        return saveUserToFile(newUser, response.id());
    }

    @NotNull
    private LoggedUserDto saveUserToFile(LoggedUserDto user, Integer id) {
        LoggedUserDto savedUser = LoggedUserDto.createUser(user, id);
        daoService.saveToFile(savedUser);
        return savedUser;
    }

    public boolean deleteUserByAssured(Integer id) {
        return assuredService.deleteById(id);
    }
}
