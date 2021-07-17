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
import com.irurueta.reactorworkshop.ReactorWorkshopConfiguration;
import com.irurueta.reactorworkshop.polynomial.domain.entities.EvaluationStep;
import com.irurueta.reactorworkshop.polynomial.domain.entities.Operation;
import com.irurueta.reactorworkshop.polynomial.domain.exceptions.InvalidEvaluationStepException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class PolynomialParametersValidationServiceTest {

    private static final int MAX_DEGREE = 3;

    private static final int MAX_POLYNOMIAL_TREE_DEPTH = 2;

    private static final int MAX_POLYNOMIAL_COUNT = 4;

    private  PolynomialParametersValidationService service;

    @BeforeEach
    void setUp() {
        final var configuration = mock(ReactorWorkshopConfiguration.class);
        when(configuration.getMaxPolynomialDegree()).thenReturn(MAX_DEGREE);
        when(configuration.getMaxPolynomialTreeDepth()).thenReturn(MAX_POLYNOMIAL_TREE_DEPTH);
        when(configuration.getMaxPolynomialCount()).thenReturn(MAX_POLYNOMIAL_COUNT);

        service = new PolynomialParametersValidationService(configuration);
    }

    @Test
    void constructor_setsMaxDegree() {
        assertEquals(MAX_DEGREE, service.getMaxDegree());
    }

    @Test
    void constructor_setsMaxPolynomialTreeDepth() {
        assertEquals(MAX_POLYNOMIAL_TREE_DEPTH, service.getMaxPolynomialTreeDepth());
    }

    @Test
    void constructor_setsMaxPolynomialCount() {
        assertEquals(MAX_POLYNOMIAL_COUNT, service.getMaxPolynomialCount());
    }

    @Test
    void validate_whenNull_makesNoAction() {
        assertDoesNotThrow(() -> service.validate(null));
    }

    @Test
    void validate_whenNotLiteral_makesNoAction() {
        final var step = EvaluationStep.builder()
                .operation(Operation.SUMMATION)
                .build();

        final var steps = Collections.singletonList(step);
        assertDoesNotThrow(() -> service.validate(steps));
    }

    @Test
    void validate_whenValidLiteral_validatesPolynomialDegree() {
        final var polynomial = new Polynomial(1.0, 1.0, 1.0, 1.0);
        final var step = EvaluationStep.builder()
                .literalPolynomial(polynomial)
                .operation(Operation.LITERAL)
                .build();

        final var steps = Collections.singletonList(step);
        assertDoesNotThrow(() -> service.validate(steps));
    }

    @Test
    void validate_whenMaxDegreeExceeded_throwsInvalidEvaluationStepException() {
        final var polynomial = new Polynomial(1.0, 1.0, 1.0, 1.0, 1.0);
        final var step = EvaluationStep.builder()
                .literalPolynomial(polynomial)
                .operation(Operation.LITERAL)
                .build();

        final var steps = Collections.singletonList(step);
        assertThrows(InvalidEvaluationStepException.class, () -> service.validate(steps));

        assertTrue(polynomial.getDegree() > service.getMaxDegree());
    }

    @Test
    void validate_whenMaxPolynomialTreeDepthExceeded_throwsInvalidEvaluationStepException() {

        final var step = EvaluationStep.builder()
                .operation(Operation.SUMMATION)
                .operand1(EvaluationStep.builder()
                        .operation(Operation.SUMMATION)
                        .operand1(EvaluationStep.builder()
                                .operation(Operation.SUMMATION)
                                .operand1(EvaluationStep.builder()
                                        .literalPolynomial(new Polynomial(1.0, 1.0))
                                        .operation(Operation.LITERAL)
                                        .build())
                                .operand2(EvaluationStep.builder()
                                        .literalPolynomial(new Polynomial(1.0, 1.0))
                                        .operation(Operation.LITERAL)
                                        .build())
                                .build())
                        .operand2(EvaluationStep.builder()
                                .literalPolynomial(new Polynomial(1.0, 1.0))
                                .operation(Operation.LITERAL)
                                .build())
                        .build())
                .operand2(EvaluationStep.builder()
                        .literalPolynomial(new Polynomial(1.0, 1.0))
                        .operation(Operation.LITERAL)
                        .build())
                .build();

        final var steps = Collections.singletonList(step);
        assertThrows(InvalidEvaluationStepException.class, () -> service.validate(steps));
    }

    @Test
    void validate_whenMaxPolynomialCountExceeded_throwsInvalidEvaluationStepException() {
        final var polynomial = new Polynomial(1.0, 1.0);
        final var step = EvaluationStep.builder()
                .literalPolynomial(polynomial)
                .operation(Operation.LITERAL)
                .build();

        final var steps = Arrays.asList(step, step, step, step, step);
        assertThrows(InvalidEvaluationStepException.class, () -> service.validate(steps));
    }

}