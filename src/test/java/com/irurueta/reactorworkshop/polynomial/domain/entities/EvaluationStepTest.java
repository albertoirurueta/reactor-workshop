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
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EvaluationStepTest {

    @Test
    void build_whenNoValues_returnsDefaultValues() {
        final var step = EvaluationStep.builder().build();

        assertNull(step.getOperation());
        assertNull(step.getOperand1());
        assertNull(step.getOperand2());
        assertNull(step.getLiteralPolynomial());
    }

    @Test
    void operation_returnsExpectedValue() {
        final var operation = Operation.LITERAL;
        final var step = EvaluationStep.builder().operation(operation).build();

        assertEquals(Operation.LITERAL, step.getOperation());
    }

    @Test
    void operand1_returnsExpectedValue() {
        final var operand1 = EvaluationStep.builder().build();
        final var step = EvaluationStep.builder().operand1(operand1).build();

        assertSame(operand1, step.getOperand1());
    }

    @Test
    void operand2_returnsExpectedValue() {
        final var operand2 = EvaluationStep.builder().build();
        final var step = EvaluationStep.builder().operand2(operand2).build();

        assertSame(operand2, step.getOperand2());
    }

    @Test
    void literalPolynomial_returnsExpectedValue() {
        final var literalPolynomial = new Polynomial(1.0, 1.0);
        final var step = EvaluationStep.builder().literalPolynomial(literalPolynomial).build();

        assertSame(literalPolynomial, step.getLiteralPolynomial());
    }
}