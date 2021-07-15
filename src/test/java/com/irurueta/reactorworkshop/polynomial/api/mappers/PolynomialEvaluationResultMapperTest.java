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
package com.irurueta.reactorworkshop.polynomial.api.mappers;

import com.irurueta.algebra.Complex;
import com.irurueta.numerical.polynomials.Polynomial;
import com.irurueta.reactorworkshop.TestUtils;
import com.irurueta.reactorworkshop.polynomial.domain.entities.PolynomialEvaluationResult;
import org.assertj.core.util.Arrays;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.stereotype.Component;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PolynomialEvaluationResultMapperTest {

    private static final double[] POLY_PARAMS = new double[]{2.0, 1.0};

    private static final double[] FIRST_DERIVATIVE_POLY_PARAMS = new double[]{1.0};

    private static final double[] SECOND_DERIVATIVE_POLY_PARAMS = new double[]{0.0};

    private static final double[] INTEGRATION_POLY_PARAMS = new double[]{0.0, 2.0, 0.5};

    @Spy
    private ComplexMapperImpl complexMapper;

    @Spy
    private PolynomialMapperImpl polynomialMapper;

    @InjectMocks
    private PolynomialEvaluationResultMapperImpl mapper;

    private AutoCloseable autoCloseable;

    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }

    @Test
    void generatedClass_hasComponentAnnotation() {
        assertNotNull(TestUtils.getClassAnnotation(PolynomialEvaluationResultMapperImpl.class, Component.class));
    }

    @Test
    void generatedClass_implementsComplexMapper() {
        assertTrue(PolynomialEvaluationResultMapper.class.isAssignableFrom(PolynomialEvaluationResultMapperImpl.class));
    }

    @Test
    void mapToDto_whenNull_returnsNull() {
        assertNull(mapper.mapToDto(null));

        verifyNoInteractions(polynomialMapper);
        verifyNoInteractions(complexMapper);
    }

    @Test
    void mapToDto_whenNotNull_returnsExpectedValue() {
        final var random = new Random();

        final var real = random.nextDouble();
        final var imaginary = random.nextDouble();
        final var root = new Complex(real, imaginary);
        final var roots = Arrays.array(root);

        final var minimum = random.nextDouble();
        final var minima = new double[]{minimum};

        final var maximum = random.nextDouble();
        final var maxima = new double[]{maximum};

        final var polynomial = new Polynomial(POLY_PARAMS);
        final var firstDerivative = new Polynomial(FIRST_DERIVATIVE_POLY_PARAMS);
        final var secondDerivative = new Polynomial(SECOND_DERIVATIVE_POLY_PARAMS);
        final var integration = new Polynomial(INTEGRATION_POLY_PARAMS);

        final var result = PolynomialEvaluationResult.builder()
                .roots(roots)
                .minima(minima)
                .maxima(maxima)
                .polynomial(polynomial)
                .firstDerivative(firstDerivative)
                .secondDerivative(secondDerivative)
                .integration(integration)
                .build();

        final var dto = mapper.mapToDto(result);

        // check
        assertEquals(1, dto.getRoots().length);
        assertEquals(real, dto.getRoots()[0].getReal(), 0.0);
        assertEquals(imaginary, dto.getRoots()[0].getImaginary(), 0.0);
        assertArrayEquals(minima, dto.getMinima(), 0.0);
        assertArrayEquals(maxima, dto.getMaxima(), 0.0);
        assertArrayEquals(POLY_PARAMS, dto.getPolynomialParameters(), 0.0);
        assertArrayEquals(FIRST_DERIVATIVE_POLY_PARAMS, dto.getFirstDerivativePolynomialParameters(), 0.0);
        assertArrayEquals(SECOND_DERIVATIVE_POLY_PARAMS, dto.getSecondDerivativePolynomialParameters(), 0.0);
        assertArrayEquals(INTEGRATION_POLY_PARAMS, dto.getIntegrationPolynomialParameters(), 0.0);

        verify(complexMapper, only()).mapToDto(root);
        verify(polynomialMapper, times(1)).mapToDto(polynomial);
        verify(polynomialMapper, times(1)).mapToDto(firstDerivative);
        verify(polynomialMapper, times(1)).mapToDto(secondDerivative);
        verify(polynomialMapper, times(1)).mapToDto(integration);
        verifyNoMoreInteractions(polynomialMapper);
    }

    @Test
    void mapToDto_whenNoRoots_returnsExpectedValue() {
        final var random = new Random();

        final var minimum = random.nextDouble();
        final var minima = new double[]{minimum};

        final var maximum = random.nextDouble();
        final var maxima = new double[]{maximum};

        final var polynomial = new Polynomial(POLY_PARAMS);
        final var firstDerivative = new Polynomial(FIRST_DERIVATIVE_POLY_PARAMS);
        final var secondDerivative = new Polynomial(SECOND_DERIVATIVE_POLY_PARAMS);
        final var integration = new Polynomial(INTEGRATION_POLY_PARAMS);

        final var result = PolynomialEvaluationResult.builder()
                .minima(minima)
                .maxima(maxima)
                .polynomial(polynomial)
                .firstDerivative(firstDerivative)
                .secondDerivative(secondDerivative)
                .integration(integration)
                .build();

        final var dto = mapper.mapToDto(result);

        // check
        assertNull(dto.getRoots());
        assertArrayEquals(minima, dto.getMinima(), 0.0);
        assertArrayEquals(maxima, dto.getMaxima(), 0.0);
        assertArrayEquals(POLY_PARAMS, dto.getPolynomialParameters(), 0.0);
        assertArrayEquals(FIRST_DERIVATIVE_POLY_PARAMS, dto.getFirstDerivativePolynomialParameters(), 0.0);
        assertArrayEquals(SECOND_DERIVATIVE_POLY_PARAMS, dto.getSecondDerivativePolynomialParameters(), 0.0);
        assertArrayEquals(INTEGRATION_POLY_PARAMS, dto.getIntegrationPolynomialParameters(), 0.0);

        verifyNoInteractions(complexMapper);
        verify(polynomialMapper, times(1)).mapToDto(polynomial);
        verify(polynomialMapper, times(1)).mapToDto(firstDerivative);
        verify(polynomialMapper, times(1)).mapToDto(secondDerivative);
        verify(polynomialMapper, times(1)).mapToDto(integration);
        verifyNoMoreInteractions(polynomialMapper);

    }

    @Test
    void mapToDto_whenNoMinima_returnsExpectedValue() {
        final var random = new Random();

        final var real = random.nextDouble();
        final var imaginary = random.nextDouble();
        final var root = new Complex(real, imaginary);
        final var roots = Arrays.array(root);

        final var maximum = random.nextDouble();
        final var maxima = new double[]{maximum};

        final var polynomial = new Polynomial(POLY_PARAMS);
        final var firstDerivative = new Polynomial(FIRST_DERIVATIVE_POLY_PARAMS);
        final var secondDerivative = new Polynomial(SECOND_DERIVATIVE_POLY_PARAMS);
        final var integration = new Polynomial(INTEGRATION_POLY_PARAMS);

        final var result = PolynomialEvaluationResult.builder()
                .roots(roots)
                .maxima(maxima)
                .polynomial(polynomial)
                .firstDerivative(firstDerivative)
                .secondDerivative(secondDerivative)
                .integration(integration)
                .build();

        final var dto = mapper.mapToDto(result);

        // check
        assertEquals(1, dto.getRoots().length);
        assertEquals(real, dto.getRoots()[0].getReal(), 0.0);
        assertEquals(imaginary, dto.getRoots()[0].getImaginary(), 0.0);
        assertNull(dto.getMinima());
        assertArrayEquals(maxima, dto.getMaxima(), 0.0);
        assertArrayEquals(POLY_PARAMS, dto.getPolynomialParameters(), 0.0);
        assertArrayEquals(FIRST_DERIVATIVE_POLY_PARAMS, dto.getFirstDerivativePolynomialParameters(), 0.0);
        assertArrayEquals(SECOND_DERIVATIVE_POLY_PARAMS, dto.getSecondDerivativePolynomialParameters(), 0.0);
        assertArrayEquals(INTEGRATION_POLY_PARAMS, dto.getIntegrationPolynomialParameters(), 0.0);

        verify(complexMapper, only()).mapToDto(root);
        verify(polynomialMapper, times(1)).mapToDto(polynomial);
        verify(polynomialMapper, times(1)).mapToDto(firstDerivative);
        verify(polynomialMapper, times(1)).mapToDto(secondDerivative);
        verify(polynomialMapper, times(1)).mapToDto(integration);
        verifyNoMoreInteractions(polynomialMapper);
    }

    @Test
    void mapToDto_whenNoMaxima_returnsExpectedValue() {
        final var random = new Random();

        final var real = random.nextDouble();
        final var imaginary = random.nextDouble();
        final var root = new Complex(real, imaginary);
        final var roots = Arrays.array(root);

        final var minimum = random.nextDouble();
        final var minima = new double[]{minimum};

        final var polynomial = new Polynomial(POLY_PARAMS);
        final var firstDerivative = new Polynomial(FIRST_DERIVATIVE_POLY_PARAMS);
        final var secondDerivative = new Polynomial(SECOND_DERIVATIVE_POLY_PARAMS);
        final var integration = new Polynomial(INTEGRATION_POLY_PARAMS);

        final var result = PolynomialEvaluationResult.builder()
                .roots(roots)
                .minima(minima)
                .polynomial(polynomial)
                .firstDerivative(firstDerivative)
                .secondDerivative(secondDerivative)
                .integration(integration)
                .build();

        final var dto = mapper.mapToDto(result);

        // check
        assertEquals(1, dto.getRoots().length);
        assertEquals(real, dto.getRoots()[0].getReal(), 0.0);
        assertEquals(imaginary, dto.getRoots()[0].getImaginary(), 0.0);
        assertArrayEquals(minima, dto.getMinima(), 0.0);
        assertNull(dto.getMaxima());
        assertArrayEquals(POLY_PARAMS, dto.getPolynomialParameters(), 0.0);
        assertArrayEquals(FIRST_DERIVATIVE_POLY_PARAMS, dto.getFirstDerivativePolynomialParameters(), 0.0);
        assertArrayEquals(SECOND_DERIVATIVE_POLY_PARAMS, dto.getSecondDerivativePolynomialParameters(), 0.0);
        assertArrayEquals(INTEGRATION_POLY_PARAMS, dto.getIntegrationPolynomialParameters(), 0.0);

        verify(complexMapper, only()).mapToDto(root);
        verify(polynomialMapper, times(1)).mapToDto(polynomial);
        verify(polynomialMapper, times(1)).mapToDto(firstDerivative);
        verify(polynomialMapper, times(1)).mapToDto(secondDerivative);
        verify(polynomialMapper, times(1)).mapToDto(integration);
        verifyNoMoreInteractions(polynomialMapper);
    }
}