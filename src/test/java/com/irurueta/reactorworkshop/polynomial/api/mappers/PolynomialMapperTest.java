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

import com.irurueta.numerical.polynomials.Polynomial;
import com.irurueta.reactorworkshop.TestUtils;
import org.junit.jupiter.api.Test;
import org.springframework.stereotype.Component;

import static org.junit.jupiter.api.Assertions.*;

class PolynomialMapperTest {

    private static final double[] POLY_PARAMS = new double[]{1.0, 1.0};

    private final PolynomialMapper mapper = new PolynomialMapperImpl();

    @Test
    void generatedClass_hasComponentAnnotation() {
        assertNotNull(TestUtils.getClassAnnotation(PolynomialMapperImpl.class, Component.class));
    }

    @Test
    void generatedClass_implementsComplexMapper() {
        assertTrue(PolynomialMapper.class.isAssignableFrom(PolynomialMapperImpl.class));
    }

    @Test
    void mapToDto_whenNull_returnsEmpty() {
        assertEquals(0, mapper.mapToDto(null).length);
    }

    @Test
    void mapToDto_whenNotNull_returnsExpectedValue() {
        final var polynomial = new Polynomial(POLY_PARAMS);
        assertArrayEquals(POLY_PARAMS, mapper.mapToDto(polynomial), 0.0);
    }

    @Test
    void mapFromDto_whenNull_returnsNull() {
        assertNull(mapper.mapFromDto(null));
    }

    @Test
    void mapFromDto_whenNotNull_returnsExpectedValue() {
        final var polynomial = mapper.mapFromDto(POLY_PARAMS);
        assertArrayEquals(POLY_PARAMS, polynomial.getPolyParams(), 0.0);
    }
}
