package com.irurueta.reactorworkshop.polynomial.api.controllers;

import com.irurueta.reactorworkshop.ReactorWorkshopApplication;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static io.restassured.RestAssured.given;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = {ReactorWorkshopApplication.class})
class PolynomialControllerIT {

    private static final long DELAY_MILLIS = 100;

    @LocalServerPort
    private int port;

    @Test
    void evaluateNonReactive_returnsOk() {
        given().port(port).contentType(ContentType.JSON)
                .body("{\"steps\": [{\"operation\":\"literal\",\"literalPolynomialParameters\":[1.0,1.0]}]}")
                .when().log().all().post("/polynomial/non-reactive")
                .then().log().all()
                .contentType(ContentType.JSON)
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    void evaluateNonReactive_whenDelayProvided_returnsOk() {
        given().port(port).contentType(ContentType.JSON)
                .body("{\"steps\": [{\"operation\":\"literal\",\"literalPolynomialParameters\":[1.0,1.0]}]}")
                .queryParam("delay", DELAY_MILLIS)
                .when().log().all().post("/polynomial/non-reactive")
                .then().log().all()
                .contentType(ContentType.JSON)
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    void evaluateReactive_returnsOk() {
        given().port(port).contentType(ContentType.JSON)
                .body("{\"steps\": [{\"operation\":\"literal\",\"literalPolynomialParameters\":[1.0,1.0]}]}")
                .when().log().all().post("/polynomial/reactive")
                .then().log().all()
                .contentType(ContentType.JSON)
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    void evaluateReactive_whenDelayProvided_returnsOk() {
        given().port(port).contentType(ContentType.JSON)
                .body("{\"steps\": [{\"operation\":\"literal\",\"literalPolynomialParameters\":[1.0,1.0]}]}")
                .queryParam("delay", DELAY_MILLIS)
                .when().log().all().post("/polynomial/reactive")
                .then().log().all()
                .contentType(ContentType.JSON)
                .statusCode(HttpStatus.OK.value());
    }

}
