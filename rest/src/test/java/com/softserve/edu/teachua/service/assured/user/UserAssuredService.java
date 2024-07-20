package com.softserve.edu.teachua.service.assured.user;

import com.softserve.edu.teachua.dao.user.model.SignInResponse;
import com.softserve.edu.teachua.dao.user.model.LoggedUserDto;
import io.restassured.path.json.JsonPath;

public interface UserAssuredService {

    SignInResponse signUpUser(LoggedUserDto newUser);

    JsonPath signInUser(LoggedUserDto newUser);

    LoggedUserDto updateProfile(JsonPath jsonPath, LoggedUserDto newUser);

    boolean deleteById(Integer id);
}
