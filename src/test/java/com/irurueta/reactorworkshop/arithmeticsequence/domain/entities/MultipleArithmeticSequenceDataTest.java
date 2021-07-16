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
package com.irurueta.reactorworkshop.arithmeticsequence.domain.entities;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class MultipleArithmeticSequenceDataTest {

    private static final int MIN_VALUE = 123;

    private static final int STEP = 1;

    private static final int COUNT = 10;

    @Test
    void build_whenNoValues_returnsDefaultValues() {
        final var data = MultipleArithmeticSequenceData.builder().build();

        assertEquals(0, data.getMinValue());
        assertEquals(0, data.getStep());
        assertEquals(0, data.getCount());
        assertNull(data.getSequenceMethod());
    }

    @Test
    void minValue_returnsExpectedValue() {
        final var data = MultipleArithmeticSequenceData.builder()
                .minValue(MIN_VALUE)
                .build();

        assertEquals(MIN_VALUE, data.getMinValue());
    }

    @Test
    void step_returnsExpectedValue() {
        final var data = MultipleArithmeticSequenceData.builder()
                .step(STEP)
                .build();

        assertEquals(STEP, data.getStep());
    }

    @Test
    void count_returnsExpectedValue() {
        final var data = MultipleArithmeticSequenceData.builder()
                .count(COUNT)
                .build();

        assertEquals(COUNT, data.getCount());
    }

    @Test
    void sequenceMethod_returnsExpectedValue() {
        final var data = MultipleArithmeticSequenceData.builder()
                .sequenceMethod(ArithmeticSequenceMethod.FAST)
                .build();

        assertEquals(ArithmeticSequenceMethod.FAST, data.getSequenceMethod());
    }
}
