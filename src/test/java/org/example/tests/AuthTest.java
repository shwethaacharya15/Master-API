package org.example.tests;

import org.example.base.BaseTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class AuthTest extends BaseTest {

    @Test
    void generateTokenSuccessfully() {

        String requestBody = """
                {
                    "username": "admin",
                    "password": "password123"
                }
                """;

        given()
                .contentType("application/json")
                .body(requestBody)
                .when()
                .post("/auth")
                .then()
                .statusCode(200)
                .body("token", notNullValue());
    }

    @Test
    void generateTokenWithInvalidPassword() {

        String requestBody = """
                {
                    "username": "admin",
                    "password": "wrongpass"
                }
                """;

        given()
                .contentType("application/json")
                .body(requestBody)
                .when()
                .post("/auth")
                .then()
                .statusCode(200)   // Booker returns 200 with error message
                .body("reason", equalTo("Bad credentials"));
    }
}