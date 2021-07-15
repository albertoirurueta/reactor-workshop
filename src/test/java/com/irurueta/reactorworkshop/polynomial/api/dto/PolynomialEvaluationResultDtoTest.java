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

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;

class PolynomialEvaluationResultDtoTest {

    private PolynomialEvaluationResultDto dto;

    @BeforeEach
    void setUp() {
        dto = new PolynomialEvaluationResultDto();
    }

    @Test
    void roots_returnsExpectedValue() {
        // check default value
        assertNull(dto.getRoots());

        // set new value
        final var roots = new ComplexDto[0];
        dto.setRoots(roots);

        // check
        assertSame(roots, dto.getRoots());
    }

    @Test
    void minima_returnsExpectedValue() {
        // check default value
        assertNull(dto.getMinima());

        // set new value
        final var minima = new double[0];
        dto.setMinima(minima);

        // check
        assertSame(minima, dto.getMinima());
    }

    @Test
    void maxima_returnsExpectedValue() {
        // check default value
        assertNull(dto.getMaxima());

        // set new value
        final var maxima = new double[0];
        dto.setMaxima(maxima);

        // check
        assertSame(maxima, dto.getMaxima());
    }

    @Test
    void polynomialParameters_returnsExpectedValue() {
        // check default value
        assertNull(dto.getPolynomialParameters());

        // set new value
        final var polynomialParameters = new double[0];
        dto.setPolynomialParameters(polynomialParameters);

        // check
        assertSame(polynomialParameters, dto.getPolynomialParameters());
    }

    @Test
    void firstDerivativePolynomialParameters_returnsExpectedValue() {
        // check default value
        assertNull(dto.getFirstDerivativePolynomialParameters());

        // set new value
        final var firstDerivativePolynomialParameters = new double[0];
        dto.setFirstDerivativePolynomialParameters(firstDerivativePolynomialParameters);

        // check
        assertSame(firstDerivativePolynomialParameters, dto.getFirstDerivativePolynomialParameters());
    }

    @Test
    void secondDerivativePolynomialParameters_returnsExpectedValue() {
        // check default value
        assertNull(dto.getSecondDerivativePolynomialParameters());

        // set new value
        final var secondDerivativePolynomialParameters = new double[0];
        dto.setSecondDerivativePolynomialParameters(secondDerivativePolynomialParameters);

        // check
        assertSame(secondDerivativePolynomialParameters, dto.getSecondDerivativePolynomialParameters());
    }

    @Test
    void integrationPolynomialParameters_returnsExpectedValue() {
        // check default value
        assertNull(dto.getIntegrationPolynomialParameters());

        // set new value
        final var integrationPolynomialParameters = new double[0];
        dto.setIntegrationPolynomialParameters(integrationPolynomialParameters);

        // check
        assertSame(integrationPolynomialParameters, dto.getIntegrationPolynomialParameters());
    }
}
