/*
 * Copyright (C) 2021 Alberto Irurueta Carro (alberto@irurueta.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.irurueta.reactorworkshop.arithmeticsequence.api.controllers;

import static io.restassured.RestAssured.given;

import com.irurueta.reactorworkshop.ReactorWorkshopApplication;
import com.irurueta.reactorworkshop.arithmeticsequence.domain.entities.ArithmeticSequenceMethod;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Random;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = {ReactorWorkshopApplication.class})
public class ArithmeticSequenceControllerIT {

    private static final int MAX = 100;

    @LocalServerPort
    private int port;

    @Test
    public void computeDetailNonReactive_returnsOk() {
        final var random = new Random();
        final var minValue = random.nextInt(MAX);
        final var step = random.nextInt(MAX);
        final var count = 1 + random.nextInt(MAX);
        final var sequenceMethod = ArithmeticSequenceMethod.FAST.getValue();

        given().port(port).contentType(ContentType.JSON).when()
                .log().all()
                .queryParam("minValue", minValue)
                .queryParam("step", step)
                .queryParam("count", count)
                .queryParam("sequenceMethod", sequenceMethod)
                .get("/arithmetic-sequence/non-reactive/detail")
                .then()
                .log().all()
                .contentType(ContentType.JSON)
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    public void computeSummaryNonReactive_returnsOk() {
        final var random = new Random();
        final var minValue = random.nextInt(MAX);
        final var step = random.nextInt(MAX);
        final var count = 1 + random.nextInt(MAX);
        final var sequenceMethod = ArithmeticSequenceMethod.FAST.getValue();

        given().port(port).contentType(ContentType.JSON).when()
                .log().all()
                .queryParam("minValue", minValue)
                .queryParam("step", step)
                .queryParam("count", count)
                .queryParam("sequenceMethod", sequenceMethod)
                .get("/arithmetic-sequence/non-reactive/summary")
                .then()
                .log().all()
                .contentType(ContentType.JSON)
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    public void computeDetailReactive_returnsOk() {
        final var random = new Random();
        final var minValue = random.nextInt(MAX);
        final var step = random.nextInt(MAX);
        final var count = 1 + random.nextInt(MAX);
        final var sequenceMethod = ArithmeticSequenceMethod.FAST.getValue();

        given().port(port).contentType(ContentType.JSON).when()
                .log().all()
                .queryParam("minValue", minValue)
                .queryParam("step", step)
                .queryParam("count", count)
                .queryParam("sequenceMethod", sequenceMethod)
                .get("/arithmetic-sequence/reactive/detail")
                .then()
                .log().all()
                .contentType(ContentType.JSON)
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    public void computeSummaryReactive_returnsOk() {
        final var random = new Random();
        final var minValue = random.nextInt(MAX);
        final var step = random.nextInt(MAX);
        final var count = 1 + random.nextInt(MAX);
        final var sequenceMethod = ArithmeticSequenceMethod.FAST.getValue();

        given().port(port).contentType(ContentType.JSON).when()
                .log().all()
                .queryParam("minValue", minValue)
                .queryParam("step", step)
                .queryParam("count", count)
                .queryParam("sequenceMethod", sequenceMethod)
                .get("/arithmetic-sequence/reactive/summary")
                .then()
                .log().all()
                .contentType(ContentType.JSON)
                .statusCode(HttpStatus.OK.value());
    }
}
