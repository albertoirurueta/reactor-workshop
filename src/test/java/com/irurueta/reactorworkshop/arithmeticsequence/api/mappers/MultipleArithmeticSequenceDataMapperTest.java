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
package com.irurueta.reactorworkshop.arithmeticsequence.api.mappers;

import com.irurueta.reactorworkshop.TestUtils;
import com.irurueta.reactorworkshop.arithmeticsequence.domain.entities.ArithmeticSequenceMethod;
import org.junit.jupiter.api.Test;
import org.springframework.stereotype.Component;

import static org.junit.jupiter.api.Assertions.*;

class MultipleArithmeticSequenceDataMapperTest {

    private static final Integer MIN_VALUE = 1;

    private static final Integer STEP = 2;

    private static final Integer COUNT = 10;

    private static final String SEQUENCE_METHOD = "fast";

    private final MultipleArithmeticSequenceDataMapper mapper = new MultipleArithmeticSequenceDataMapperImpl();

    @Test
    void generatedClass_hasComponentAnnotation() {
        assertNotNull(TestUtils.getClassAnnotation(MultipleArithmeticSequenceDataMapperImpl.class, Component.class));
    }

    @Test
    void generatedClass_implementsMultipleArithmeticSequenceDataMapper() {
        assertTrue(MultipleArithmeticSequenceDataMapper.class.isAssignableFrom(MultipleArithmeticSequenceDataMapperImpl.class));
    }

    @Test
    void mapFromDto_whenAllValuesAreNull_returnsNull() {
        assertNull(mapper.mapFromDto(null, null, null, null));
    }

    @Test
    void mapFromDto_whenNullMinValue_returnsExpectedValue() {
        final var result = mapper.mapFromDto(null, STEP, COUNT, SEQUENCE_METHOD);

        assertEquals(0, result.getMinValue());
        assertEquals(STEP, result.getStep());
        assertEquals(COUNT, result.getCount());
        assertEquals(ArithmeticSequenceMethod.FAST, result.getSequenceMethod());
    }

    @Test
    void mapFromDto_whenNullStep_returnsExpectedValue() {
        final var result = mapper.mapFromDto(MIN_VALUE, null, COUNT, SEQUENCE_METHOD);

        assertEquals(MIN_VALUE, result.getMinValue());
        assertEquals(0, result.getStep());
        assertEquals(COUNT, result.getCount());
        assertEquals(ArithmeticSequenceMethod.FAST, result.getSequenceMethod());
    }

    @Test
    void mapFromDto_whenNullCount_returnsExpectedValue() {
        final var result = mapper.mapFromDto(MIN_VALUE, STEP, null, SEQUENCE_METHOD);

        assertEquals(MIN_VALUE, result.getMinValue());
        assertEquals(STEP, result.getStep());
        assertEquals(0, result.getCount());
        assertEquals(ArithmeticSequenceMethod.FAST, result.getSequenceMethod());
    }

    @Test
    void mapFromDto_whenNullSequenceMethod_returnsExpectedValue() {
        final var result = mapper.mapFromDto(MIN_VALUE, STEP, COUNT, null);

        assertEquals(MIN_VALUE, result.getMinValue());
        assertEquals(STEP, result.getStep());
        assertEquals(COUNT, result.getCount());
        assertNull(result.getSequenceMethod());
    }

    @Test
    void mapFromDto_whenNoNullValues_returnsExpectedValue() {
        final var result = mapper.mapFromDto(MIN_VALUE, STEP, COUNT, SEQUENCE_METHOD);

        assertEquals(MIN_VALUE, result.getMinValue());
        assertEquals(STEP, result.getStep());
        assertEquals(COUNT, result.getCount());
        assertEquals(ArithmeticSequenceMethod.FAST, result.getSequenceMethod());
    }

    @Test
    void mapFromString_whenValueIsNull_returnsNull() {
        assertNull(mapper.mapFromString(null));
    }

    @Test
    void mapFromString_whenValueIsNotNull_returnsExpectedValue() {
        assertEquals(ArithmeticSequenceMethod.FAST, mapper.mapFromString(SEQUENCE_METHOD));
    }
}