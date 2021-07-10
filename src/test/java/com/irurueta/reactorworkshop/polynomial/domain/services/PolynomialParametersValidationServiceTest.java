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

public class PolynomialParametersValidationServiceTest {

    private static final int MAX_DEGREE = 3;

    private static final int MAX_POLYNOMIAL_TREE_DEPTH = 2;

    private static final int MAX_POLYNOMIAL_COUNT = 4;

    private  PolynomialParametersValidationService service;

    @BeforeEach
    public void setUp() {
        final var configuration = mock(ReactorWorkshopConfiguration.class);
        when(configuration.getMaxPolynomialDegree()).thenReturn(MAX_DEGREE);
        when(configuration.getMaxPolynomialTreeDepth()).thenReturn(MAX_POLYNOMIAL_TREE_DEPTH);
        when(configuration.getMaxPolynomialCount()).thenReturn(MAX_POLYNOMIAL_COUNT);

        service = new PolynomialParametersValidationService(configuration);
    }

    @Test
    public void constructor_setsMaxDegree() {
        assertEquals(MAX_DEGREE, service.getMaxDegree());
    }

    @Test
    public void constructor_setsMaxPolynomialTreeDepth() {
        assertEquals(MAX_POLYNOMIAL_TREE_DEPTH, service.getMaxPolynomialTreeDepth());
    }

    @Test
    public void constructor_setsMaxPolynomialCount() {
        assertEquals(MAX_POLYNOMIAL_COUNT, service.getMaxPolynomialCount());
    }

    @Test
    public void validate_whenNull_makesNoAction() {
        assertDoesNotThrow(() -> service.validate(null));
    }

    @Test
    public void validate_whenNotLiteral_makesNoAction() {
        final var step = new EvaluationStep();
        step.setOperation(Operation.SUMMATION);

        final var steps = Collections.singletonList(step);
        assertDoesNotThrow(() -> service.validate(steps));
    }

    @Test
    public void validate_whenValidLiteral_validatesPolynomialDegree() {
        final var polynomial = new Polynomial(1.0, 1.0, 1.0, 1.0);
        final var step = new EvaluationStep();
        step.setLiteralPolynomial(polynomial);
        step.setOperation(Operation.LITERAL);

        final var steps = Collections.singletonList(step);
        assertDoesNotThrow(() -> service.validate(steps));
    }

    @Test
    public void validate_whenMaxDegreeExceeded_throwsInvalidEvaluationStepException() {
        final var polynomial = new Polynomial(1.0, 1.0, 1.0, 1.0, 1.0);
        final var step = new EvaluationStep();
        step.setLiteralPolynomial(polynomial);
        step.setOperation(Operation.LITERAL);

        final var steps = Collections.singletonList(step);
        assertThrows(InvalidEvaluationStepException.class, () -> service.validate(steps));

        assertTrue(polynomial.getDegree() > service.getMaxDegree());
    }

    @Test
    public void validate_whenMaxPolynomialTreeDepthExceeded_throwsInvalidEvaluationStepException() {
        final var step = new EvaluationStep();
        step.setOperation(Operation.SUMMATION);

        final var step1 = new EvaluationStep();
        step1.setOperation(Operation.SUMMATION);
        step.setOperand1(step1);

        final var step3 = new EvaluationStep();
        step3.setOperation(Operation.SUMMATION);
        step1.setOperand1(step3);

        final var polynomial5 = new Polynomial(1.0, 1.0);
        final var step5 = new EvaluationStep();
        step5.setLiteralPolynomial(polynomial5);
        step5.setOperation(Operation.LITERAL);
        step3.setOperand1(step5);

        final var polynomial6 = new Polynomial(1.0, 1.0);
        final var step6 = new EvaluationStep();
        step6.setLiteralPolynomial(polynomial6);
        step6.setOperation(Operation.LITERAL);
        step3.setOperand2(step6);

        final var polynomial4 = new Polynomial(1.0, 1.0);
        final var step4 = new EvaluationStep();
        step4.setLiteralPolynomial(polynomial4);
        step4.setOperation(Operation.LITERAL);
        step1.setOperand2(step4);

        final var polynomial2 = new Polynomial(1.0, 1.0);
        final var step2 = new EvaluationStep();
        step2.setLiteralPolynomial(polynomial2);
        step2.setOperation(Operation.LITERAL);
        step.setOperand2(step2);

        final var steps = Collections.singletonList(step);
        assertThrows(InvalidEvaluationStepException.class, () -> service.validate(steps));
    }

    @Test
    public void validate_whenMaxPolynomialCountExceeded_throwsInvalidEvaluationStepException() {
        final var polynomial = new Polynomial(1.0, 1.0);
        final var step = new EvaluationStep();
        step.setLiteralPolynomial(polynomial);
        step.setOperation(Operation.LITERAL);

        final var steps = Arrays.asList(step, step, step, step, step);
        assertThrows(InvalidEvaluationStepException.class, () -> service.validate(steps));
    }

}