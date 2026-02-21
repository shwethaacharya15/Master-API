package org.example;

import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.*;

public class HealthCheckAuthTest {

    @Test
    void generateToken() {

        baseURI = "https://restful-booker.herokuapp.com";

        String requestBody = """
                {
                    "username": "admin",
                    "password": "password123"
                }
                """;

        given()
                .log().all()
                .contentType("application/json")
                .body(requestBody)
                .when()
                .post("/auth")
                .then()
                .log().all()
                .statusCode(200);
    }
}