package org.example.base;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;

public class BaseTest {

    @BeforeEach
    public void setup() {
        // Set common RestAssured config for all tests
        RestAssured.baseURI = "https://restful-booker.herokuapp.com";
        RestAssured.port = 443; // HTTPS default port
        RestAssured.useRelaxedHTTPSValidation(); // optional for SSL issues
    }
}