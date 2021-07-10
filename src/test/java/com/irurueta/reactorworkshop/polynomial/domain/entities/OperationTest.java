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
package com.irurueta.reactorworkshop.polynomial.domain.entities;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OperationTest {

    @Test
    public void enum_hasExpectedValues() {
        assertEquals(4, Operation.values().length);
    }

    @Test
    public void getValue_returnsExpectedValues() {
        assertEquals("summation", Operation.SUMMATION.getValue());
        assertEquals("subtraction", Operation.SUBTRACTION.getValue());
        assertEquals("multiplication", Operation.MULTIPLICATION.getValue());
        assertEquals("literal", Operation.LITERAL.getValue());
    }

    @Test
    public void fromValue_returnsExpectedEnumInstance() {
        assertEquals(Operation.SUMMATION, Operation.fromValue("summation"));
        assertEquals(Operation.SUBTRACTION, Operation.fromValue("subtraction"));
        assertEquals(Operation.MULTIPLICATION, Operation.fromValue("multiplication"));
        assertEquals(Operation.LITERAL, Operation.fromValue("literal"));

        assertEquals(Operation.SUMMATION, Operation.fromValue("SUMMATION"));
        assertEquals(Operation.SUBTRACTION, Operation.fromValue("SUBTRACTION"));
        assertEquals(Operation.MULTIPLICATION, Operation.fromValue("MULTIPLICATION"));
        assertEquals(Operation.LITERAL, Operation.fromValue("LITERAL"));

        assertNull(Operation.fromValue("wrong"));
        assertNull(Operation.fromValue(null));

    }
}