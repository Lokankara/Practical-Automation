package com.softserve.edu.teachua.service.assured.user;

import com.softserve.edu.teachua.service.assured.RestAssuredBase;
import com.softserve.edu.teachua.dao.user.model.SignInResponse;
import com.softserve.edu.teachua.dao.user.model.LoggedUserDto;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.path.json.JsonPath;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static com.softserve.edu.teachua.tools.ResourceUtils.SIGNIN_USER_SCHEMA_JSON;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class UserRestAssuredService extends RestAssuredBase implements UserAssuredService {

    private final InputStream userSchema;
    private static final String SIGNUP = "signup";
    private static final String SIGNIN = "signin";
    private static final String DELETE_USER = "users/{id}";
    private static final String UPDATE_USER_BY_ID = "user/%d";

    public UserRestAssuredService() {
        this.userSchema = getClass().getClassLoader().getResourceAsStream(SIGNIN_USER_SCHEMA_JSON);
    }

    @Override
    public JsonPath signInUser(LoggedUserDto dto) {
        return given()
                .header("Content-Type", "application/json")
                .when().body(Map.of("email", dto.email(), "password", dto.password()))
                .and().post(SIGNIN)
                .then()
                .statusCode(200)
                .extract().body()
                .jsonPath();
    }

    @Override
    public LoggedUserDto updateProfile(JsonPath jsonPath, LoggedUserDto newUser) {
        return getAuthorization(jsonPath.getString("accessToken"))
                .when().body(userDtoToMap(newUser))
                .and().put(String.format(UPDATE_USER_BY_ID, jsonPath.getInt("id")))
                .then()
                .statusCode(200)
                .and().body("firstName", equalTo(newUser.firstName()))
                .extract().as(LoggedUserDto.class);
    }

    @Override
    public boolean deleteById(Integer id) {
        return getAuthorization()
                .when()
                .delete(DELETE_USER, id)
                .getStatusCode() == 200;
    }

    @Override
    public SignInResponse signUpUser(LoggedUserDto newUser) {
        return RestAssured.given()
                .contentType(ContentType.JSON)
                .body(newUser)
                .post(SIGNUP).then()
                .statusCode(200)
                .body(JsonSchemaValidator.matchesJsonSchema(Objects.requireNonNull(userSchema)))
                .assertThat()
                .body(notNullValue())
                .extract()
                .as(SignInResponse.class);
    }

    private Map<String, Object> userDtoToMap(LoggedUserDto user) {
        Map<String, Object> userMap = new HashMap<>();
        userMap.put("id", user.id());
        userMap.put("email", user.email());
        userMap.put("firstName", user.firstName());
        userMap.put("lastName", user.lastName());
        userMap.put("phone", user.phone());
        userMap.put("urlLogo", user.urlLogo());
        userMap.put("status", user.status());
        userMap.put("roleName", user.roleName());
        return userMap;
    }
}
