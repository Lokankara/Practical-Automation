package com.softserve.edu.teachua.tests.restassured;

import io.restassured.module.jsv.JsonSchemaValidator;
import org.junit.jupiter.api.Test;

import java.io.InputStream;

import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsStringIgnoringCase;
import static org.hamcrest.Matchers.hasItems;

class FunctionalTest extends Base {
    private static final String CITIES = "cities";


    @Test
    void test1() {
        given().when().get(CITIES).then().log()
                .all();
        given().when().get(CITIES)
                .then().statusCode(200);
    }

    @Test
    void test2() {
        given().when().get(CITIES).then()
                .body(containsStringIgnoringCase("test5"));
    }

    @Test
    void test3() {
        get(CITIES).then().
                body("name", hasItems("Дніпро", "Київ", "Test5", "Test4"));
    }

    @Test
    void test4() {
        InputStream citiesSchema = getClass().getClassLoader().getResourceAsStream("cities.json");
        get(CITIES).then().assertThat().body(JsonSchemaValidator.matchesJsonSchema(citiesSchema));
    }

}
