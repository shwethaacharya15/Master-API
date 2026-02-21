package org.example;

import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;

public class HealthCheckTest {

    @Test
    void checkServerHealth() {

        baseURI = "https://restful-booker.herokuapp.com";

        given()
                .log().all()               // log request
                .when()
                .get("/ping")
                .then()
                .log().all()               // log response
                .statusCode(201);
    }
}