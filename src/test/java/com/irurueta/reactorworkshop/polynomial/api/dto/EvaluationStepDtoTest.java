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
package com.irurueta.reactorworkshop.polynomial.api.dto;

import com.irurueta.reactorworkshop.polynomial.domain.entities.Operation;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class EvaluationStepDtoTest {

    @Test
    public void build_whenNoValues_returnsDefaultValues() {
        final var dto = EvaluationStepDto.builder().build();

        assertNull(dto.getOperation());
        assertNull(dto.getOperand1());
        assertNull(dto.getOperand2());
        assertNull(dto.getLiteralPolynomialParameters());
    }

    @Test
    public void operation_hasExpectedValue() {
        final var operation = Operation.LITERAL.getValue();
        final var dto = EvaluationStepDto.builder().operation(operation).build();

        assertEquals(operation, dto.getOperation());
    }

    @Test
    public void operand1_hasExpectedValue() {
        final var operand1 = EvaluationStepDto.builder().build();
        final var dto = EvaluationStepDto.builder().operand1(operand1).build();

        assertSame(operand1, dto.getOperand1());
    }

    @Test
    public void operand2_hasExpectedValue() {
        final var operand2 = EvaluationStepDto.builder().build();
        final var dto = EvaluationStepDto.builder().operand2(operand2).build();

        assertSame(operand2, dto.getOperand2());
    }

    @Test
    public void literalPolynomialParameters_hasExpectedValue() {
        final var params = new double[]{1.0, 1.0};
        final var dto = EvaluationStepDto.builder().literalPolynomialParameters(params).build();

        assertSame(params, dto.getLiteralPolynomialParameters());
    }
}