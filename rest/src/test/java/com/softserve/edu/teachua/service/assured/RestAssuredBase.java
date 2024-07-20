package com.softserve.edu.teachua.service.assured;

import com.softserve.edu.teachua.tools.ResourceUtils;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.util.Map;

import static io.restassured.RestAssured.given;

public abstract class RestAssuredBase {
    protected String accessToken;
    protected Integer id;

    public RestAssuredBase() {
        init();
    }

    private void init() {
        String basePath = System.getProperty("server.base");
        RestAssured.basePath = basePath == null ? "dev/api" : basePath;
        String baseHost = System.getProperty("server.host");
        RestAssured.baseURI = baseHost == null ? "http://speak-ukrainian.eastus2.cloudapp.azure.com" : baseHost;
        accessToken = getToken("nenix55377@hutov.com", "Elv3nWay!");
        ResourceUtils.get().saveTokenToFile(accessToken);
    }

    protected RequestSpecification getAuthorization(String accessToken) {
        return given().header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + accessToken);
    }

    protected RequestSpecification getAuthorization() {
        if (accessToken == null) {
            accessToken = ResourceUtils.get().readTokenFromFile();
        }
        return getAuthorization(accessToken);
    }

    public String getToken(String email, String password) {
        Map<String, Object> user = Map.of("email", email, "password", password);

        Response response = given()
                .header("Content-Type", "application/json")
                .when().body(user)
                .and().post("signin");
        id = response.body().jsonPath().getInt("id");
        return response.body().jsonPath().getString("accessToken");
    }
}
