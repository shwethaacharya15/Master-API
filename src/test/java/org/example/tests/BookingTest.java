package org.example.tests;
// Unit-like API tests for individual endpoints
import org.example.base.BaseTest;
import org.example.utils.TokenManager;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class BookingTest extends BaseTest {

    @Test
    void updateBookingWithValidToken() {

        String updateBody = """
                {
                    "firstname": "James",
                    "lastname": "Brown",
                    "totalprice": 999,
                    "depositpaid": true,
                    "bookingdates": {
                        "checkin": "2026-03-01",
                        "checkout": "2026-03-10"
                    },
                    "additionalneeds": "Breakfast"
                }
                """;

        given()
                .contentType("application/json")
                .cookie("token", TokenManager.getToken())
                .body(updateBody)
                .when()
                .put("/booking/1")
                .then()
                .statusCode(anyOf(is(200), is(201)));
    }

    @Test
    void updateBookingWithoutToken() {

        String updateBody = """
                {
                    "firstname": "James",
                    "lastname": "Brown"
                }
                """;

        given()
                .contentType("application/json")
                .body(updateBody)
                .when()
                .put("/booking/1")
                .then()
                .statusCode(403);
    }

    @Test
    void updateBookingWithInvalidToken() {

        String updateBody = """
                {
                    "firstname": "James",
                    "lastname": "Brown"
                }
                """;

        given()
                .contentType("application/json")
                .cookie("token", "invalid123")
                .body(updateBody)
                .when()
                .put("/booking/1")
                .then()
                .statusCode(403);
    }
}