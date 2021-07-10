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
import com.irurueta.reactorworkshop.arithmeticsequence.domain.entities.SingleArithmeticSequenceResult;
import org.junit.jupiter.api.Test;
import org.springframework.stereotype.Component;

import static org.junit.jupiter.api.Assertions.*;

public class SingleArithmeticSequenceResultMapperTest {

    private static final Integer MIN_VALUE = 1;

    private static final Integer STEP = 2;

    private static final Integer COUNT = 10;

    private static final Integer MAX_VALUE = 19;

    private static final Integer SUM = 100;

    private final SingleArithmeticSequenceResultMapper mapper = new SingleArithmeticSequenceResultMapperImpl();

    @Test
    public void generatedClass_hasComponentAnnotation() {
        assertNotNull(TestUtils.getClassAnnotation(SingleArithmeticSequenceResultMapperImpl.class, Component.class));
    }

    @Test
    public void generatedClass_implementsSingleArithmeticSequenceResultMapper() {
        assertTrue(SingleArithmeticSequenceResultMapper.class.isAssignableFrom(SingleArithmeticSequenceResultMapperImpl.class));
    }

    @Test
    public void mapToDto_whenNull_returnsNull() {
        assertNull(mapper.mapToDto(null));
    }

    @Test
    public void mapToDto_whenNotNull_returnsExpectedValue() {
        final var singleResult = SingleArithmeticSequenceResult.builder()
                .minValue(MIN_VALUE)
                .step(STEP)
                .count(COUNT)
                .maxValue(MAX_VALUE)
                .sum(SUM)
                .build();

        final var result = mapper.mapToDto(singleResult);

        assertEquals(MIN_VALUE, result.getMinValue());
        assertEquals(STEP, result.getStep());
        assertEquals(COUNT, result.getCount());
        assertEquals(MAX_VALUE, result.getMaxValue());
        assertEquals(SUM, result.getSum());
    }
}