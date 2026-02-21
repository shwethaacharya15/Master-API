package org.example.utils;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

import java.time.Instant;

import static io.restassured.RestAssured.given;

public class AuthUtils {

    private static String token;
    private static Instant tokenExpiryTime;
    private static final long TOKEN_VALIDITY_SECONDS = 3600; // 1 hour

    public static String getToken() {
        if (token == null || isTokenExpired()) {
            generateNewToken();
        }
        return token;
    }

    private static boolean isTokenExpired() {
        return tokenExpiryTime == null || Instant.now().isAfter(tokenExpiryTime);
    }

    private static void generateNewToken() {
        String authPayload = """
            {
              "username": "admin",
              "password": "password123"
            }
            """;

        Response response = given()
                .contentType(ContentType.JSON)
                .body(authPayload)
                .when()
                .post("/auth")
                .then()
                .statusCode(200)
                .extract()
                .response();

        token = response.path("token");
        tokenExpiryTime = Instant.now().plusSeconds(TOKEN_VALIDITY_SECONDS);

        System.out.println("Generated new token: " + token);
        System.out.println("Token valid until: " + tokenExpiryTime);
    }

    public static void invalidateToken() {
        token = null;
        tokenExpiryTime = null;
    }
}