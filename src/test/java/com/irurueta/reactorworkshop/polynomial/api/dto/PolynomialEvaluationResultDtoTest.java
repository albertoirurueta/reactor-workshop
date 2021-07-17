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

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;

class PolynomialEvaluationResultDtoTest {

    @Test
    void build_whenNoValues_returnsDefaultValues() {
        final var dto = PolynomialEvaluationResultDto.builder().build();

        assertNull(dto.getRoots());
        assertNull(dto.getMinima());
        assertNull(dto.getMaxima());
        assertNull(dto.getPolynomialParameters());
        assertNull(dto.getFirstDerivativePolynomialParameters());
        assertNull(dto.getSecondDerivativePolynomialParameters());
        assertNull(dto.getIntegrationPolynomialParameters());
    }

    @Test
    void roots_returnsExpectedValue() {
        final var roots = new ComplexDto[0];
        final var dto = PolynomialEvaluationResultDto.builder()
                .roots(roots)
                .build();

        assertSame(roots, dto.getRoots());
    }

    @Test
    void minima_returnsExpectedValue() {
        final var minima = new double[0];
        final var dto = PolynomialEvaluationResultDto.builder()
                .minima(minima)
                .build();

        assertSame(minima, dto.getMinima());
    }

    @Test
    void maxima_returnsExpectedValue() {
        final var maxima = new double[0];
        final var dto = PolynomialEvaluationResultDto.builder()
                .maxima(maxima)
                .build();

        assertSame(maxima, dto.getMaxima());
    }

    @Test
    void polynomialParameters_returnsExpectedValue() {
        final var polynomialParameters = new double[0];
        final var dto = PolynomialEvaluationResultDto.builder()
                .polynomialParameters(polynomialParameters)
                .build();

        assertSame(polynomialParameters, dto.getPolynomialParameters());
    }

    @Test
    void firstDerivativePolynomialParameters_returnsExpectedValue() {
        final var firstDerivativePolynomialParameters = new double[0];
        final var dto = PolynomialEvaluationResultDto.builder()
                .firstDerivativePolynomialParameters(firstDerivativePolynomialParameters)
                .build();

        assertSame(firstDerivativePolynomialParameters, dto.getFirstDerivativePolynomialParameters());
    }

    @Test
    void secondDerivativePolynomialParameters_returnsExpectedValue() {
        final var secondDerivativePolynomialParameters = new double[0];
        final var dto = PolynomialEvaluationResultDto.builder()
                .secondDerivativePolynomialParameters(secondDerivativePolynomialParameters)
                .build();

        assertSame(secondDerivativePolynomialParameters, dto.getSecondDerivativePolynomialParameters());
    }

    @Test
    void integrationPolynomialParameters_returnsExpectedValue() {
        final var integrationPolynomialParameters = new double[0];
        final var dto = PolynomialEvaluationResultDto.builder()
                .integrationPolynomialParameters(integrationPolynomialParameters)
                .build();

        assertSame(integrationPolynomialParameters, dto.getIntegrationPolynomialParameters());
    }
}
