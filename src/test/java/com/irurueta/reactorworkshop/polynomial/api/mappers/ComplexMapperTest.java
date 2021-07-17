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
import com.irurueta.reactorworkshop.TestUtils;
import org.junit.jupiter.api.Test;
import org.springframework.stereotype.Component;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class ComplexMapperTest {

    private final ComplexMapper mapper = new ComplexMapperImpl();

    @Test
    void generatedClass_hasComponentAnnotation() {
        assertNotNull(TestUtils.getClassAnnotation(ComplexMapperImpl.class, Component.class));
    }

    @Test
    void generatedClass_implementsComplexMapper() {
        assertTrue(ComplexMapper.class.isAssignableFrom(ComplexMapperImpl.class));
    }

    @Test
    void mapToDto_whenNull_returnsNull() {
        assertNull(mapper.mapToDto(null));
    }

    @Test
    void mapToDto_whenNotNull_returnsExpectedValue() {
        final var random = new Random();
        final var real = random.nextDouble();
        final var imaginary = random.nextDouble();
        final var complex = new Complex(real, imaginary);

        final var result = mapper.mapToDto(complex);

        assertEquals(real, result.getReal(), 0.0);
        assertEquals(imaginary, result.getImaginary(), 0.0);
    }
}