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

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SingleArithmeticSequenceResultDtoTest {

    private static final int MIN_VALUE = 123;

    private static final int STEP = 1;

    private static final int COUNT = 10;

    private static final int MAX_VALUE = 456;

    private static final int SUM = 789;

    private SingleArithmeticSequenceResultDto result;

    @BeforeEach
    public void setUp() {
        result = new SingleArithmeticSequenceResultDto();
    }

    @Test
    public void minValue_returnsExpectedValue() {
        // check default value
        assertEquals(0, result.getMinValue());

        // set new value
        result.setMinValue(MIN_VALUE);

        // check
        assertEquals(MIN_VALUE, result.getMinValue());
    }

    @Test
    public void step_returnsExpectedValue() {
        // check default value
        assertEquals(0, result.getStep());

        // set new value
        result.setStep(STEP);

        // check
        assertEquals(STEP, result.getStep());
    }

    @Test
    public void count_returnsExpectedValue() {
        // check default value
        assertEquals(0, result.getCount());

        // set new value
        result.setCount(COUNT);

        // check
        assertEquals(COUNT, result.getCount());
    }

    @Test
    public void maxValue_returnsExpectedValue() {
        // check default value
        assertEquals(0, result.getMaxValue());

        // set new value
        result.setMaxValue(MAX_VALUE);

        // check
        assertEquals(MAX_VALUE, result.getMaxValue());
    }

    @Test
    public void sum_returnsExpectedValue() {
        // check default value
        assertEquals(0, result.getSum());

        // set new value
        result.setSum(SUM);

        // check
        assertEquals(SUM, result.getSum());
    }
}