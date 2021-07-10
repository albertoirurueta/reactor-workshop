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
import com.irurueta.reactorworkshop.polynomial.domain.entities.EvaluationStep;
import com.irurueta.reactorworkshop.polynomial.domain.entities.Operation;
import com.irurueta.reactorworkshop.polynomial.domain.exceptions.InvalidEvaluationStepException;
import com.irurueta.reactorworkshop.polynomial.domain.exceptions.PolynomialEvaluationException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PolynomialEvaluationServiceTest {

    private static final double ABSOLUTE_ERROR = 1e-12;

    private final PolynomialEvaluationService service = new PolynomialEvaluationService();

    @Test
    public void evaluate_whenLiteralStepWithFirstDegree_returnsExpected() {
        final var step = new EvaluationStep();
        step.setOperation(Operation.LITERAL);
        step.setLiteralPolynomial(new Polynomial(2.0, 1.0));

        final var result = service.evaluate(step);

        assertNotNull(result.getRoots());
        assertEquals(1, result.getRoots().length);
        assertEquals(-2.0, result.getRoots()[0].getReal(), 0.0);
        assertEquals(0.0, result.getRoots()[0].getImaginary(), 0.0);
        assertNull(result.getMinima());
        assertNull(result.getMaxima());
        assertArrayEquals(new double[]{2.0, 1.0}, result.getPolynomial().getPolyParams());
        assertArrayEquals(new double[]{1.0}, result.getFirstDerivative().getPolyParams(), 0.0);
        assertArrayEquals(new double[]{0.0}, result.getSecondDerivative().getPolyParams(), 0.0);
        assertArrayEquals(new double[]{0.0, 2.0, 0.5}, result.getIntegration().getPolyParams(), 0.0);
    }

    @Test
    public void evaluate_whenLiteralStepWithSecondDegreeWithMinima_returnsExpected() {
        final var step = new EvaluationStep();
        step.setOperation(Operation.LITERAL);
        step.setLiteralPolynomial(new Polynomial(-1.0, 0.0, 1.0));

        final var result = service.evaluate(step);

        assertNotNull(result.getRoots());
        assertEquals(2, result.getRoots().length);
        assertEquals(-1.0, result.getRoots()[0].getReal(), 0.0);
        assertEquals(0.0, result.getRoots()[0].getImaginary(), 0.0);
        assertEquals(1.0, result.getRoots()[1].getReal(), 0.0);
        assertEquals(0.0, result.getRoots()[1].getImaginary(), 0.0);
        assertEquals(1, result.getMinima().length);
        assertEquals(0.0, result.getMinima()[0], ABSOLUTE_ERROR);
        assertNull(result.getMaxima());
        assertArrayEquals(new double[]{-1.0, 0.0, 1.0}, result.getPolynomial().getPolyParams());
        assertArrayEquals(new double[]{0.0, 2.0}, result.getFirstDerivative().getPolyParams(), 0.0);
        assertArrayEquals(new double[]{2.0}, result.getSecondDerivative().getPolyParams(), 0.0);
        assertArrayEquals(new double[]{0.0, -1.0, 0.0, 1.0 / 3.0}, result.getIntegration().getPolyParams(), 0.0);
    }

    @Test
    public void evaluate_whenLiteralStepWithSecondDegreeWithMaxima_returnsExpected() {
        final var step = new EvaluationStep();
        step.setOperation(Operation.LITERAL);
        step.setLiteralPolynomial(new Polynomial(4.0, 0.0, -1.0));

        final var result = service.evaluate(step);

        assertNotNull(result.getRoots());
        assertEquals(2, result.getRoots().length);
        assertEquals(-2.0, result.getRoots()[0].getReal(), 0.0);
        assertEquals(0.0, result.getRoots()[0].getImaginary(), 0.0);
        assertEquals(2.0, result.getRoots()[1].getReal(), 0.0);
        assertEquals(0.0, result.getRoots()[1].getImaginary(), 0.0);
        assertNull(result.getMinima());
        assertEquals(1, result.getMaxima().length);
        assertEquals(0.0, result.getMaxima()[0], ABSOLUTE_ERROR);
        assertArrayEquals(new double[]{4.0, 0.0, -1.0}, result.getPolynomial().getPolyParams());
        assertArrayEquals(new double[]{0.0, -2.0}, result.getFirstDerivative().getPolyParams(), 0.0);
        assertArrayEquals(new double[]{-2.0}, result.getSecondDerivative().getPolyParams(), 0.0);
        assertArrayEquals(new double[]{0.0, 4.0, 0.0, -1.0 / 3.0}, result.getIntegration().getPolyParams(), 0.0);
    }

    @Test
    public void evaluate_whenSummationStep_returnsExpected() {
        final var stepOperand1 = new EvaluationStep();
        stepOperand1.setOperation(Operation.LITERAL);
        stepOperand1.setLiteralPolynomial(new Polynomial(2.0));

        final var stepOperand2 = new EvaluationStep();
        stepOperand2.setOperation(Operation.LITERAL);
        stepOperand2.setLiteralPolynomial(new Polynomial(0.0, 1.0));

        final var step = new EvaluationStep();
        step.setOperation(Operation.SUMMATION);
        step.setOperand1(stepOperand1);
        step.setOperand2(stepOperand2);

        final var result = service.evaluate(step);

        assertNotNull(result.getRoots());
        assertEquals(1, result.getRoots().length);
        assertEquals(-2.0, result.getRoots()[0].getReal(), 0.0);
        assertEquals(0.0, result.getRoots()[0].getImaginary(), 0.0);
        assertNull(result.getMinima());
        assertNull(result.getMaxima());
        assertArrayEquals(new double[]{2.0, 1.0}, result.getPolynomial().getPolyParams());
        assertArrayEquals(new double[]{1.0}, result.getFirstDerivative().getPolyParams(), 0.0);
        assertArrayEquals(new double[]{0.0}, result.getSecondDerivative().getPolyParams(), 0.0);
        assertArrayEquals(new double[]{0.0, 2.0, 0.5}, result.getIntegration().getPolyParams(), 0.0);
    }

    @Test
    public void evaluate_whenSubtractionStep_returnsExpected() {
        final var stepOperand1 = new EvaluationStep();
        stepOperand1.setOperation(Operation.LITERAL);
        stepOperand1.setLiteralPolynomial(new Polynomial(2.0));

        final var stepOperand2 = new EvaluationStep();
        stepOperand2.setOperation(Operation.LITERAL);
        stepOperand2.setLiteralPolynomial(new Polynomial(0.0, -1.0));

        final var step = new EvaluationStep();
        step.setOperation(Operation.SUBTRACTION);
        step.setOperand1(stepOperand1);
        step.setOperand2(stepOperand2);

        final var result = service.evaluate(step);

        assertNotNull(result.getRoots());
        assertEquals(1, result.getRoots().length);
        assertEquals(-2.0, result.getRoots()[0].getReal(), 0.0);
        assertEquals(0.0, result.getRoots()[0].getImaginary(), 0.0);
        assertNull(result.getMinima());
        assertNull(result.getMaxima());
        assertArrayEquals(new double[]{2.0, 1.0}, result.getPolynomial().getPolyParams());
        assertArrayEquals(new double[]{1.0}, result.getFirstDerivative().getPolyParams(), 0.0);
        assertArrayEquals(new double[]{0.0}, result.getSecondDerivative().getPolyParams(), 0.0);
        assertArrayEquals(new double[]{0.0, 2.0, 0.5}, result.getIntegration().getPolyParams(), 0.0);
    }

    @Test
    public void evaluate_whenMultiplicationStep_returnsExpected() {
        final var stepOperand1 = new EvaluationStep();
        stepOperand1.setOperation(Operation.LITERAL);
        stepOperand1.setLiteralPolynomial(new Polynomial(1.0, 1.0));

        final var stepOperand2 = new EvaluationStep();
        stepOperand2.setOperation(Operation.LITERAL);
        stepOperand2.setLiteralPolynomial(new Polynomial(-1.0, 1.0));

        final var step = new EvaluationStep();
        step.setOperation(Operation.MULTIPLICATION);
        step.setOperand1(stepOperand1);
        step.setOperand2(stepOperand2);

        final var result = service.evaluate(step);

        assertNotNull(result.getRoots());
        assertEquals(2, result.getRoots().length);
        assertEquals(-1.0, result.getRoots()[0].getReal(), 0.0);
        assertEquals(0.0, result.getRoots()[0].getImaginary(), 0.0);
        assertEquals(1.0, result.getRoots()[1].getReal(), 0.0);
        assertEquals(0.0, result.getRoots()[1].getImaginary(), 0.0);
        assertEquals(1, result.getMinima().length);
        assertEquals(0.0, result.getMinima()[0], ABSOLUTE_ERROR);
        assertNull(result.getMaxima());
        assertArrayEquals(new double[]{-1.0, 0.0, 1.0}, result.getPolynomial().getPolyParams());
        assertArrayEquals(new double[]{0.0, 2.0}, result.getFirstDerivative().getPolyParams(), 0.0);
        assertArrayEquals(new double[]{2.0}, result.getSecondDerivative().getPolyParams(), 0.0);
        assertArrayEquals(new double[]{0.0, -1.0, 0.0, 1.0 / 3.0}, result.getIntegration().getPolyParams(), 0.0);
    }

    @Test
    public void evaluate_whenMissingOperation_returnsInvalidEvaluationStepException() {
        final var step = new EvaluationStep();
        step.setLiteralPolynomial(new Polynomial(2.0, 1.0));

        assertThrows(InvalidEvaluationStepException.class, () -> service.evaluate(step));
    }

    @Test
    public void evaluate_whenMissingLiteral_returnsInvalidEvaluationStepException() {
        final var step = new EvaluationStep();
        step.setOperation(Operation.LITERAL);

        assertThrows(InvalidEvaluationStepException.class, () -> service.evaluate(step));
    }

    @Test
    public void evaluate_whenMissingOperands_returnsInvalidEvaluationStepException() {
        final var step = new EvaluationStep();

        step.setOperation(Operation.SUMMATION);
        assertThrows(InvalidEvaluationStepException.class, () -> service.evaluate(step));

        step.setOperation(Operation.SUBTRACTION);
        assertThrows(InvalidEvaluationStepException.class, () -> service.evaluate(step));

        step.setOperation(Operation.MULTIPLICATION);
        assertThrows(InvalidEvaluationStepException.class, () -> service.evaluate(step));
    }

    @Test
    public void evaluate_whenNaN_returnsPolynomialEvaluationException() {
        final var step = new EvaluationStep();
        step.setOperation(Operation.LITERAL);
        step.setLiteralPolynomial(new Polynomial(Double.NaN, Double.POSITIVE_INFINITY, Double.NEGATIVE_INFINITY));

        assertThrows(PolynomialEvaluationException.class, () -> service.evaluate(step));
    }
}