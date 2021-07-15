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

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class MultipleArithmeticSequenceDataTest {

    private static final int MIN_VALUE = 123;

    private static final int STEP = 1;

    private static final int COUNT = 10;

    private MultipleArithmeticSequenceData data;

    @BeforeEach
    void setUp() {
        data = new MultipleArithmeticSequenceData();
    }

    @Test
    void minValue_returnsExpectedValue() {
        // check default value
        assertEquals(0, data.getMinValue());

        // set new value
        data.setMinValue(MIN_VALUE);

        // check
        assertEquals(MIN_VALUE, data.getMinValue());
    }

    @Test
    void step_returnsExpectedValue() {
        // check default value
        assertEquals(0, data.getStep());

        // set new value
        data.setStep(STEP);

        // check
        assertEquals(STEP, data.getStep());
    }

    @Test
    void count_returnsExpectedValue() {
        // check default value
        assertEquals(0, data.getCount());

        // set new value
        data.setCount(COUNT);

        // check
        assertEquals(COUNT, data.getCount());
    }

    @Test
    void sequenceMethod_returnsExpectedValue() {
        // check default value
        assertNull(data.getSequenceMethod());

        // set new value
        data.setSequenceMethod(ArithmeticSequenceMethod.FAST);

        // check
        assertEquals(ArithmeticSequenceMethod.FAST, data.getSequenceMethod());
    }
}
