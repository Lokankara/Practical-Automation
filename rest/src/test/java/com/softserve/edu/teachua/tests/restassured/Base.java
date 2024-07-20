package com.softserve.edu.teachua.tests.restassured;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;

public class Base {

    @BeforeAll
    public static void setup() {
        String basePath = System.getProperty("server.base");
        if (basePath == null) {
            basePath = "dev/api";
        }
        RestAssured.basePath = basePath;

        String baseHost = System.getProperty("server.host");
        if (baseHost == null) {
            baseHost = "http://speak-ukrainian.eastus2.cloudapp.azure.com";
        }
        RestAssured.baseURI = baseHost;
    }
}
