package org.example.tests;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.*;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class BookingE2EWithLogsTest {

    private static String token;
    private static int bookingId;

    @BeforeAll
    public static void setup() {
        RestAssured.baseURI = "https://restful-booker.herokuapp.com";

        // Step 1: Authenticate and get token
        Map<String, String> auth = new HashMap<>();
        auth.put("username", "admin");
        auth.put("password", "password123");

        Response authResponse = given()
                .contentType(ContentType.JSON)
                .body(auth)
                .log().all()
                .post("/auth");

        authResponse.then().log().all().statusCode(200);
        token = authResponse.jsonPath().getString("token");
    }

    @Test
    @Order(1)
    public void testCreateBooking_Positive() {
        Map<String, Object> booking = new HashMap<>();
        booking.put("firstname", "Shwetha");
        booking.put("lastname", "QA");
        booking.put("totalprice", 500);
        booking.put("depositpaid", true);
        Map<String, String> dates = new HashMap<>();
        dates.put("checkin", "2026-02-21");
        dates.put("checkout", "2026-02-25");
        booking.put("bookingdates", dates);
        booking.put("additionalneeds", "Breakfast");

        Response response = given()
                .contentType(ContentType.JSON)
                .body(booking)
                .log().all()
                .post("/booking");

        response.then().log().all()
                .statusCode(200)
                .body("booking.firstname", equalTo("Shwetha"))
                .body("booking.lastname", equalTo("QA"));

        bookingId = response.jsonPath().getInt("bookingid");
        System.out.println("Booking created with ID: " + bookingId);
    }

    @Test
    @Order(2)
    public void testGetBookingById_Positive() {
        given()
                .log().all()
                .get("/booking/" + bookingId)
                .then().log().all()
                .statusCode(200)
                .body("firstname", equalTo("Shwetha"))
                .body("lastname", equalTo("QA"));
    }

    @Test
    @Order(3)
    public void testGetBookingIds() {
        given()
                .log().all()
                .get("/booking")
                .then().log().all()
                .statusCode(200)
                .body("bookingid", hasItem(bookingId));
    }

    @Test
    @Order(4)
    public void testUpdateBooking_Full() {
        Map<String, Object> updatedBooking = new HashMap<>();
        updatedBooking.put("firstname", "ShwethaUpdated");
        updatedBooking.put("lastname", "QAUpdated");
        updatedBooking.put("totalprice", 600);
        updatedBooking.put("depositpaid", false);
        Map<String, String> dates = new HashMap<>();
        dates.put("checkin", "2026-02-22");
        dates.put("checkout", "2026-02-26");
        updatedBooking.put("bookingdates", dates);
        updatedBooking.put("additionalneeds", "Lunch");

        given()
                .contentType(ContentType.JSON)
                .cookie("token", token)
                .body(updatedBooking)
                .log().all()
                .put("/booking/" + bookingId)
                .then().log().all()
                .statusCode(200)
                .body("firstname", equalTo("ShwethaUpdated"))
                .body("lastname", equalTo("QAUpdated"));
    }

    @Test
    @Order(5)
    public void testDeleteBooking_Positive() {
        given()
                .cookie("token", token)
                .log().all()
                .delete("/booking/" + bookingId)
                .then().log().all()
                .statusCode(201); // Restful Booker returns 201 on delete
    }

    @Test
    @Order(6)
    public void testGetBookingById_Negative() {
        // Should fail because booking is deleted
        given()
                .log().all()
                .get("/booking/" + bookingId)
                .then().log().all()
                .statusCode(404);
    }

    @Test
    @Order(7)
    public void testDeleteBooking_Negative() {
        // Deleting again should fail
        given()
                .cookie("token", token)
                .log().all()
                .delete("/booking/" + bookingId)
                .then().log().all()
                .statusCode(405);
    }

    @Test
    @Order(8)
    public void testPing_Positive() {
        given()
                .log().all()
                .get("/ping")
                .then().log().all()
                .statusCode(201); // ping returns 201
    }
}