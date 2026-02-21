package org.example.utils;

import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class TokenManager {

    private static String token;

    public static String getToken() {

        if (token == null) {

            String requestBody = """
                    {
                        "username": "admin",
                        "password": "password123"
                    }
                    """;

            Response response =
                    given()
                            .contentType("application/json")
                            .body(requestBody)
                            .when()
                            .post("/auth")
                            .then()
                            .statusCode(200)
                            .extract()
                            .response();

            token = response.jsonPath().getString("token");
        }

        return token;
    }
}