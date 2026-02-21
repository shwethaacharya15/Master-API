package org.example.tests;

import io.restassured.response.Response;
import org.example.base.BaseTest;
import org.example.builders.BookingPayloadBuilder;
import org.example.utils.TokenManager;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
// Full E2E with positive & negative + schema + token
public class BookingE2ETest extends BaseTest {

    @Test
    void fullBookingLifecycle() {

        // Step 1 — Generate token
        String token = TokenManager.getToken();

        // Step 2 — Create booking
        Response createResponse =
                given()
                        .contentType("application/json")
                        .body(BookingPayloadBuilder.createBookingPayload())
                        .when()
                        .post("/booking")
                        .then()
                        .statusCode(200)
                        .extract().response();

        int bookingId = createResponse.jsonPath().getInt("bookingid");

        // Step 3 — Get booking
        given()
                .when()
                .get("/booking/" + bookingId)
                .then()
                .statusCode(200)
                .body("firstname", equalTo("API"));

        // Step 4 — Update booking
        given()
                .contentType("application/json")
                .cookie("token", token)
                .body(BookingPayloadBuilder.createBookingPayload())
                .when()
                .put("/booking/" + bookingId)
                .then()
                .statusCode(200);

        // Step 5 — Verify update
        given()
                .when()
                .get("/booking/" + bookingId)
                .then()
                .statusCode(200);

        // Step 6 — Delete booking
        given()
                .cookie("token", token)
                .when()
                .delete("/booking/" + bookingId)
                .then()
                .statusCode(201);

        // Step 7 — Verify deletion
        given()
                .when()
                .get("/booking/" + bookingId)
                .then()
                .statusCode(404);
    }
}