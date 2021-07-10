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
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.stereotype.Service;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class MultiplePolynomialEvaluationServiceTest {

    private static final double ABSOLUTE_ERROR = 1e-12;

    private static final long DELAY_MILLIS = 500L;

    @Spy
    private PolynomialEvaluationService singlePolynomialService;

    @InjectMocks
    private MultiplePolynomialEvaluationService service;

    private AutoCloseable autoCloseable;

    @BeforeEach
    public void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    public void tearDown() throws Exception {
        autoCloseable.close();
    }

    @Test
    public void class_hasServiceAnnotation() {
        assertNotNull(TestUtils.getClassAnnotation(MultiplePolynomialEvaluationService.class, Service.class));
    }

    @Test
    public void constructor_injectsExpectedInstance() {
        assertSame(singlePolynomialService, ReflectionTestUtils.getField(service, "singlePolynomialService"));
    }

    @Test
    public void evaluate_whenNoDelay_returnsExpectedResult() {
        final var step1 = new EvaluationStep();
        step1.setOperation(Operation.LITERAL);
        step1.setLiteralPolynomial(new Polynomial(2.0, 1.0));

        final var step2 = new EvaluationStep();
        step2.setOperation(Operation.LITERAL);
        step2.setLiteralPolynomial(new Polynomial(-1.0, 0.0, 1.0));

        final var step3 = new EvaluationStep();
        step3.setOperation(Operation.LITERAL);
        step3.setLiteralPolynomial(new Polynomial(4.0, 0.0, -1.0));

        final var steps = Arrays.asList(step1, step2, step3);
        final var result = service.evaluate(steps, null);

        // check
        assertEquals(3, result.size());
        assertResult0(result.get(0));
        assertResult1(result.get(1));
        assertResult2(result.get(2));
    }

    @Test
    public void evaluate_whenNegativeDelay_returnsExpectedResult() {
        final var step1 = new EvaluationStep();
        step1.setOperation(Operation.LITERAL);
        step1.setLiteralPolynomial(new Polynomial(2.0, 1.0));

        final var step2 = new EvaluationStep();
        step2.setOperation(Operation.LITERAL);
        step2.setLiteralPolynomial(new Polynomial(-1.0, 0.0, 1.0));

        final var step3 = new EvaluationStep();
        step3.setOperation(Operation.LITERAL);
        step3.setLiteralPolynomial(new Polynomial(4.0, 0.0, -1.0));

        final var steps = Arrays.asList(step1, step2, step3);
        final var result = service.evaluate(steps, -500L);

        // check
        assertEquals(3, result.size());
        assertResult0(result.get(0));
        assertResult1(result.get(1));
        assertResult2(result.get(2));
    }

    @Test
    public void evaluate_whenPositiveDelay_returnsExpectedResult() {
        final var step1 = new EvaluationStep();
        step1.setOperation(Operation.LITERAL);
        step1.setLiteralPolynomial(new Polynomial(2.0, 1.0));

        final var step2 = new EvaluationStep();
        step2.setOperation(Operation.LITERAL);
        step2.setLiteralPolynomial(new Polynomial(-1.0, 0.0, 1.0));

        final var step3 = new EvaluationStep();
        step3.setOperation(Operation.LITERAL);
        step3.setLiteralPolynomial(new Polynomial(4.0, 0.0, -1.0));

        final var steps = Arrays.asList(step1, step2, step3);

        final var startInstant = Instant.now();
        final var result = service.evaluate(steps, DELAY_MILLIS);
        final var duration = Duration.between(startInstant, Instant.now());

        // check
        assertEquals(3, result.size());
        assertTrue(duration.toMillis() >= result.size() * DELAY_MILLIS);
        assertResult0(result.get(0));
        assertResult1(result.get(1));
        assertResult2(result.get(2));
    }

    private static void assertResult0(final PolynomialEvaluationResult result0) {
        assertNotNull(result0.getRoots());
        assertEquals(1, result0.getRoots().length);
        assertEquals(-2.0, result0.getRoots()[0].getReal(), 0.0);
        assertEquals(0.0, result0.getRoots()[0].getImaginary(), 0.0);
        assertNull(result0.getMinima());
        assertNull(result0.getMaxima());
        assertArrayEquals(new double[]{2.0, 1.0}, result0.getPolynomial().getPolyParams());
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

    private static void assertResult2(final PolynomialEvaluationResult result2) {
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