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
package com.irurueta.reactorworkshop.polynomial.domain.entities;

import com.irurueta.numerical.polynomials.Polynomial;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EvaluationStepTest {

    private EvaluationStep step;

    @BeforeEach
    void setUp() {
        step = new EvaluationStep();
    }

    @Test
    void operation_returnsExpectedValue() {
        // check default value
        assertNull(step.getOperation());

        // set new value
        step.setOperation(Operation.LITERAL);

        // check
        assertEquals(Operation.LITERAL, step.getOperation());
    }

    @Test
    void operand1_returnsExpectedValue() {
        // check default value
        assertNull(step.getOperand1());

        // set new value
        final var operand1 = new EvaluationStep();
        step.setOperand1(operand1);

        // check
        assertSame(operand1, step.getOperand1());
    }

    @Test
    void operand2_returnsExpectedValue() {
        // check default value
        assertNull(step.getOperand2());

        // set new value
        final var operand2 = new EvaluationStep();
        step.setOperand2(operand2);

        // check
        assertSame(operand2, step.getOperand2());
    }

    @Test
    void literalPolynomial_returnsExpectedValue() {
        // check default value
        assertNull(step.getLiteralPolynomial());

        // set new value
        final var literalPolynomial = new Polynomial(1.0, 1.0);
        step.setLiteralPolynomial(literalPolynomial);

        // check
        assertSame(literalPolynomial, step.getLiteralPolynomial());
    }
}