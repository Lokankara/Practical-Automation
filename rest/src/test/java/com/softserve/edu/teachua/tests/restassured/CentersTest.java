package com.softserve.edu.teachua.tests.restassured;

import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

import java.io.InputStream;

import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;


class CentersTest extends Base {

    private static final String CENTERS = "centers";

    @Test
    void validationSchemaAllCentersTest() {
        InputStream centersSchema = getClass().getClassLoader().getResourceAsStream("centers_schema.json");
        get(CENTERS).then()
                .assertThat().body(JsonSchemaValidator.matchesJsonSchema(centersSchema));
    }

    @Test
    void validationSchemaCenterTest() {
        InputStream centerSchema = getClass().getClassLoader().getResourceAsStream("center_schema.json");

        Response res = given().when().get("center/3");
        assertThat("Center with id = `3` should be present in database", res.statusCode(), Matchers.is(200));

        assertThat(res.getBody().asString(), JsonSchemaValidator.matchesJsonSchema(centerSchema));
    }

}
