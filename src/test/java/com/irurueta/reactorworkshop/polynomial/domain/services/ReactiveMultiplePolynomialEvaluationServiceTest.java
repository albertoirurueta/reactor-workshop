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
package com.irurueta.reactorworkshop.polynomial.domain.services;

import com.irurueta.numerical.polynomials.Polynomial;
import com.irurueta.reactorworkshop.TestUtils;
import com.irurueta.reactorworkshop.polynomial.domain.entities.EvaluationStep;
import com.irurueta.reactorworkshop.polynomial.domain.entities.Operation;
import com.irurueta.reactorworkshop.polynomial.domain.entities.PolynomialEvaluationResult;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.stereotype.Service;
import org.springframework.test.util.ReflectionTestUtils;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class ReactiveMultiplePolynomialEvaluationServiceTest {

    private static final double ABSOLUTE_ERROR = 1e-12;

    private static final long DELAY_MILLIS = 500L;

    @Spy
    private PolynomialEvaluationService singlePolynomialService;

    @InjectMocks
    private ReactiveMultiplePolynomialEvaluationService service;

    private AutoCloseable autoCloseable;

    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }

    @Test
    void class_hasServiceAnnotation() {
        assertNotNull(TestUtils.getClassAnnotation(ReactiveMultiplePolynomialEvaluationService.class, Service.class));
    }

    @Test
    void constructor_injectsExpectedInstance() {
        assertSame(singlePolynomialService, ReflectionTestUtils.getField(service, "singlePolynomialService"));
    }

    @Disabled
    @Test
    void evaluate_whenNoDelay_returnsExpectedResult() {
        final var step1 = EvaluationStep.builder()
                .operation(Operation.LITERAL)
                .literalPolynomial(new Polynomial(2.0, 1.0))
                .build();

        final var step2 = EvaluationStep.builder()
                .operation(Operation.LITERAL)
                .literalPolynomial(new Polynomial(-1.0, 0.0, 1.0))
                .build();

        final var step3 = EvaluationStep.builder()
                .operation(Operation.LITERAL)
                .literalPolynomial(new Polynomial(4.0, 0.0, -1.0))
                .build();

        final var steps = Flux.just(step1, step2, step3);
        final var flux = service.evaluate(steps, null);

        // check
        final int[] c = new int[1];
        StepVerifier.create(flux).thenConsumeWhile(result -> {
            c[0] += 1;

            if (Arrays.equals(POLY_PARAMS_0, result.getPolynomial().getPolyParams())) {
                assertResult0(result);
                return true;
            } else if (Arrays.equals(POLY_PARAMS_1, result.getPolynomial().getPolyParams())) {
                assertResult1(result);
                return true;
            } else if (Arrays.equals(POLY_PARAMS_2, result.getPolynomial().getPolyParams())) {
                assertResult2(result);
                return true;
            } else {
                // Unexpected result
                return false;
            }
        }).verifyComplete();

        assertEquals(3, c[0]);
    }

    @Disabled
    @Test
    void evaluate_whenNegativeDelay_returnsExpectedResult() {
        final var step1 = EvaluationStep.builder()
                .operation(Operation.LITERAL)
                .literalPolynomial(new Polynomial(2.0, 1.0))
                .build();

        final var step2 = EvaluationStep.builder()
                .operation(Operation.LITERAL)
                .literalPolynomial(new Polynomial(-1.0, 0.0, 1.0))
                .build();

        final var step3 = EvaluationStep.builder()
                .operation(Operation.LITERAL)
                .literalPolynomial(new Polynomial(4.0, 0.0, -1.0))
                .build();

        final var steps = Flux.just(step1, step2, step3);
        final var flux = service.evaluate(steps, -500L);

        // check
        final int[] c = new int[1];
        StepVerifier.create(flux).thenConsumeWhile(result -> {
            c[0] += 1;

            if (Arrays.equals(POLY_PARAMS_0, result.getPolynomial().getPolyParams())) {
                assertResult0(result);
                return true;
            } else if (Arrays.equals(POLY_PARAMS_1, result.getPolynomial().getPolyParams())) {
                assertResult1(result);
                return true;
            } else if (Arrays.equals(POLY_PARAMS_2, result.getPolynomial().getPolyParams())) {
                assertResult2(result);
                return true;
            } else {
                // Unexpected result
                return false;
            }
        }).verifyComplete();

        assertEquals(3, c[0]);
    }

    @Disabled
    @Test
    void evaluate_whenPositiveDelay_returnsExpectedResult() {
        final var step1 = EvaluationStep.builder()
                .operation(Operation.LITERAL)
                .literalPolynomial(new Polynomial(2.0, 1.0))
                .build();

        final var step2 = EvaluationStep.builder()
                .operation(Operation.LITERAL)
                .literalPolynomial(new Polynomial(-1.0, 0.0, 1.0))
                .build();

        final var step3 = EvaluationStep.builder()
                .operation(Operation.LITERAL)
                .literalPolynomial(new Polynomial(4.0, 0.0, -1.0))
                .build();

        final var steps = Flux.just(step1, step2, step3);

        final var flux = service.evaluate(steps, DELAY_MILLIS);

        // check
        final int[] c = new int[1];
        final var duration = StepVerifier.create(flux).thenConsumeWhile(result -> {
            c[0] += 1;

            if (Arrays.equals(POLY_PARAMS_0, result.getPolynomial().getPolyParams())) {
                assertResult0(result);
                return true;
            } else if (Arrays.equals(POLY_PARAMS_1, result.getPolynomial().getPolyParams())) {
                assertResult1(result);
                return true;
            } else if (Arrays.equals(POLY_PARAMS_2, result.getPolynomial().getPolyParams())) {
                assertResult2(result);
                return true;
            } else {
                // Unexpected result
                return false;
            }
        }).verifyComplete();

        assertEquals(3, c[0]);
        assertTrue(duration.toMillis() >= c[0] * DELAY_MILLIS);
    }

    private static final double[] POLY_PARAMS_0 = new double[]{2.0, 1.0};

    private static final double[] POLY_PARAMS_1 = new double[]{-1.0, 0.0, 1.0};

    private static final double[] POLY_PARAMS_2 = new double[]{4.0, 0.0, -1.0};

    private static void assertResult0(final PolynomialEvaluationResult result0) {
        assertNotNull(result0.getRoots());
        assertEquals(1, result0.getRoots().length);
        assertEquals(-2.0, result0.getRoots()[0].getReal(), 0.0);
        assertEquals(0.0, result0.getRoots()[0].getImaginary(), 0.0);
        assertNull(result0.getMinima());
        assertNull(result0.getMaxima());
        assertArrayEquals(POLY_PARAMS_0, result0.getPolynomial().getPolyParams());
        assertArrayEquals(new double[]{1.0}, result0.getFirstDerivative().getPolyParams(), 0.0);
        assertArrayEquals(new double[]{0.0}, result0.getSecondDerivative().getPolyParams(), 0.0);
        assertArrayEquals(new double[]{0.0, 2.0, 0.5}, result0.getIntegration().getPolyParams(), 0.0);
    }

    private static void assertResult1(final PolynomialEvaluationResult result1) {
        assertNotNull(result1.getRoots());
        assertEquals(2, result1.getRoots().length);
        assertEquals(-1.0, result1.getRoots()[0].getReal(), 0.0);
        assertEquals(0.0, result1.getRoots()[0].getImaginary(), 0.0);
        assertEquals(1.0, result1.getRoots()[1].getReal(), 0.0);
        assertEquals(0.0, result1.getRoots()[1].getImaginary(), 0.0);
        assertEquals(1, result1.getMinima().length);
        assertEquals(0.0, result1.getMinima()[0], ABSOLUTE_ERROR);
        assertNull(result1.getMaxima());
        assertArrayEquals(new double[]{-1.0, 0.0, 1.0}, result1.getPolynomial().getPolyParams());
        assertArrayEquals(new double[]{0.0, 2.0}, result1.getFirstDerivative().getPolyParams(), 0.0);
        assertArrayEquals(new double[]{2.0}, result1.getSecondDerivative().getPolyParams(), 0.0);
        assertArrayEquals(new double[]{0.0, -1.0, 0.0, 1.0 / 3.0}, result1.getIntegration().getPolyParams(), 0.0);
    }

    private void assertResult2(final PolynomialEvaluationResult result2) {
        assertNotNull(result2.getRoots());
        assertEquals(2, result2.getRoots().length);
        assertEquals(-2.0, result2.getRoots()[0].getReal(), 0.0);
        assertEquals(0.0, result2.getRoots()[0].getImaginary(), 0.0);
        assertEquals(2.0, result2.getRoots()[1].getReal(), 0.0);
        assertEquals(0.0, result2.getRoots()[1].getImaginary(), 0.0);
        assertNull(result2.getMinima());
        assertEquals(1, result2.getMaxima().length);
        assertEquals(0.0, result2.getMaxima()[0], ABSOLUTE_ERROR);
        assertArrayEquals(new double[]{4.0, 0.0, -1.0}, result2.getPolynomial().getPolyParams());
        assertArrayEquals(new double[]{0.0, -2.0}, result2.getFirstDerivative().getPolyParams(), 0.0);
        assertArrayEquals(new double[]{-2.0}, result2.getSecondDerivative().getPolyParams(), 0.0);
        assertArrayEquals(new double[]{0.0, 4.0, 0.0, -1.0 / 3.0}, result2.getIntegration().getPolyParams(), 0.0);
    }
}