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

public class ArithmeticSequenceMethodTest {

    @Test
    public void enum_hasExpectedValues() {
        assertEquals(2, ArithmeticSequenceMethod.values().length);
    }

    @Test
    public void getValue_returnsExpectedValues() {
        assertEquals("exhaustive", ArithmeticSequenceMethod.EXHAUSTIVE.getValue());
        assertEquals("fast", ArithmeticSequenceMethod.FAST.getValue());
    }

    @Test
    public void fromValue_returnsExpectedEnumInstance() {
        assertEquals(ArithmeticSequenceMethod.EXHAUSTIVE, ArithmeticSequenceMethod.fromValue("exhaustive"));
        assertEquals(ArithmeticSequenceMethod.FAST, ArithmeticSequenceMethod.fromValue("fast"));

        assertEquals(ArithmeticSequenceMethod.EXHAUSTIVE, ArithmeticSequenceMethod.fromValue("EXHAUSTIVE"));
        assertEquals(ArithmeticSequenceMethod.FAST, ArithmeticSequenceMethod.fromValue("FAST"));

        assertNull(ArithmeticSequenceMethod.fromValue("wrong"));
        assertNull(ArithmeticSequenceMethod.fromValue(null));
    }
}
