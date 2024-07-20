package com.softserve.edu.teachua.tests.restassured;

import com.softserve.edu.teachua.dao.user.model.LoggedUserDto;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ProfileAuthorizationTest extends AuthorizationBase {
    protected Map<String, String> userInfo;

    @BeforeEach
    public void getUserInfo() {
        userInfo = getAuthorization().when().get("user/" + id).as(Map.class);
        userInfo.remove("password");
    }

    @AfterEach
    public void restoreUserInfo() {
        getAuthorization()
                .when().body(userInfo)
                .and().put("user/" + id);
    }

    @Test
    void getUserProfileData() {
        getAuthorization()
                .when().get("user/" + id)
                .then().statusCode(200)
                .and().body("firstName", equalTo("MyNewName"));
    }

    @Test
    void changeFirstName() {
        Map<String, Object> newUserInfo = new HashMap<>(userInfo);
        newUserInfo.put("firstName", "Other");

        getAuthorization()
                .when().body(newUserInfo)
                .and().put("user/" + id)
                .then().statusCode(200)
                .and().body("firstName", equalTo("Other"));
    }

    @Test
    void getUserProfileData2() {
        Response response = getAuthorization().when().get("user/" + id);

        assertThat(response.statusCode(), equalTo(200));

        LoggedUserDto user = response.body().as(LoggedUserDto.class);
        assertEquals("MyNewName", user.firstName());
    }

    @Test
    void testUserSchema() {
        InputStream userSchema = getClass().getClassLoader().getResourceAsStream("user_schema.json");
        Response response = getAuthorization()
                .when().get("user/" + id)
                .then()
                .assertThat().statusCode(200)
                .extract()
                .response();

        Assertions.assertNotNull(response.body().asString());
        response.then().assertThat().body(JsonSchemaValidator.matchesJsonSchema(Objects.requireNonNull(userSchema)));
    }
}
