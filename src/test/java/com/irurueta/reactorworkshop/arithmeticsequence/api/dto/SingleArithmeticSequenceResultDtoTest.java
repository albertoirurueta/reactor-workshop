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
package com.irurueta.reactorworkshop.arithmeticsequence.api.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SingleArithmeticSequenceResultDtoTest {

    private static final int MIN_VALUE = 123;

    private static final int STEP = 1;

    private static final int COUNT = 10;

    private static final int MAX_VALUE = 456;

    private static final int SUM = 789;

    @Test
    void build_whenNoValues_returnsDefaultValues() {
        final var result = SingleArithmeticSequenceResultDto.builder().build();

        assertEquals(0, result.getMinValue());
        assertEquals(0, result.getStep());
        assertEquals(0, result.getCount());
        assertEquals(0, result.getMaxValue());
        assertEquals(0, result.getSum());
    }

    @Test
    void minValue_returnsExpectedValue() {
        final var result = SingleArithmeticSequenceResultDto.builder()
                .minValue(MIN_VALUE)
                .build();

        assertEquals(MIN_VALUE, result.getMinValue());
    }

    @Test
    void step_returnsExpectedValue() {
        final var result = SingleArithmeticSequenceResultDto.builder()
                .step(STEP)
                .build();

        assertEquals(STEP, result.getStep());
    }

    @Test
    void count_returnsExpectedValue() {
        final var result = SingleArithmeticSequenceResultDto.builder()
                .count(COUNT)
                .build();

        assertEquals(COUNT, result.getCount());
    }

    @Test
    void maxValue_returnsExpectedValue() {
        final var result = SingleArithmeticSequenceResultDto.builder()
                .maxValue(MAX_VALUE)
                .build();

        assertEquals(MAX_VALUE, result.getMaxValue());
    }

    @Test
    void sum_returnsExpectedValue() {
        final var result = SingleArithmeticSequenceResultDto.builder()
                .sum(SUM)
                .build();

        assertEquals(SUM, result.getSum());
    }
}