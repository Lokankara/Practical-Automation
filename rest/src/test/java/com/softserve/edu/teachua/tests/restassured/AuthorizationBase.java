package com.softserve.edu.teachua.tests.restassured;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeAll;

import java.util.Map;

import static io.restassured.RestAssured.given;

public class AuthorizationBase extends Base {

    protected static String accessToken;
    protected static Integer id;

    @BeforeAll
    public static void getToken() {
        Map<String, Object> user =
                Map.of("email", "cafoda4176@ikumaru.com", "password", "Cafoda4176@");

        Response response = given().header("Content-Type", "application/json")
                .when().body(user)
                .and().post("signin");

        accessToken = response.body().jsonPath().getString("accessToken");
        id = response.body().jsonPath().getInt("id");
    }

    protected RequestSpecification getAuthorization() {
        return given().header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + accessToken);
    }
}
