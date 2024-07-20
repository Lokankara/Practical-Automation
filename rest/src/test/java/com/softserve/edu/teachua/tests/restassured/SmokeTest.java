package com.softserve.edu.teachua.tests.restassured;

import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SmokeTest extends AuthorizationBase {

    @Test
    void userTest() {
        Response response = getAuthorization()
                .when().get("user/" + id);

        assertEquals(200, response.statusCode());
    }
}
