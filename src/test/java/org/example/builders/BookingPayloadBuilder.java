package org.example.builders;
// Build positive/negative payloads
import java.util.Random;

public class BookingPayloadBuilder {

    public static String createBookingPayload() {

        int price = new Random().nextInt(1000);

        return """
                {
                  "firstname": "API",
                  "lastname": "Tester",
                  "totalprice": %d,
                  "depositpaid": true,
                  "bookingdates": {
                    "checkin": "2024-01-01",
                    "checkout": "2024-01-10"
                  },
                  "additionalneeds": "Breakfast"
                }
                """.formatted(price);
    }
}