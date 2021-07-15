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

import com.irurueta.algebra.Complex;
import com.irurueta.numerical.polynomials.Polynomial;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;

class PolynomialEvaluationResultTest {

    @Test
    void build_whenNoValues_returnsDefaultValues() {
        final var result = PolynomialEvaluationResult.builder().build();

        assertNull(result.getRoots());
        assertNull(result.getMinima());
        assertNull(result.getMaxima());
        assertNull(result.getPolynomial());
        assertNull(result.getFirstDerivative());
        assertNull(result.getSecondDerivative());
        assertNull(result.getIntegration());
    }

    @Test
    void roots_hasExpectedValue() {
        final var roots = new Complex[0];
        final var result = PolynomialEvaluationResult.builder().roots(roots).build();

        assertSame(roots, result.getRoots());
    }

    @Test
    void minima_hasExpectedValue() {
        final var minima = new double[0];
        final var result = PolynomialEvaluationResult.builder().minima(minima).build();

        assertSame(minima, result.getMinima());
    }

    @Test
    void maxima_hasExpectedValue() {
        final var maxima = new double[0];
        final var result = PolynomialEvaluationResult.builder().maxima(maxima).build();

        assertSame(maxima, result.getMaxima());
    }

    @Test
    void polynomial_hasExpectedValue() {
        final var polynomial = new Polynomial(1.0, 1.0);
        final var result = PolynomialEvaluationResult.builder().polynomial(polynomial).build();

        assertSame(polynomial, result.getPolynomial());
    }

    @Test
    void firstDerivative_hasExpectedValue() {
        final var firstDerivative = new Polynomial(1.0);
        final var result = PolynomialEvaluationResult.builder()
                .firstDerivative(firstDerivative).build();

        assertSame(firstDerivative, result.getFirstDerivative());
    }

    @Test
    void secondDerivative_hasExpectedValue() {
        final var secondDerivative = new Polynomial(0.0);
        final var result = PolynomialEvaluationResult.builder()
                .secondDerivative(secondDerivative).build();

        assertSame(secondDerivative, result.getSecondDerivative());
    }

    @Test
    void integration_hasExpectedValue() {
        final var integration = new Polynomial(0.0, 1.0, 0.5);
        final var result = PolynomialEvaluationResult.builder().integration(integration).build();

        assertSame(integration, result.getIntegration());
    }
}