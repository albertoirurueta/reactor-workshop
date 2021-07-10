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
package com.irurueta.reactorworkshop.arithmeticsequence.domain.exceptions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UnsupportedArithmeticSequenceMethodExceptionTest {

    @Test
    public void class_extendsRuntimeException() {
        assertTrue(RuntimeException.class.isAssignableFrom(UnsupportedArithmeticSequenceMethodException.class));
    }

    @Test
    public void constructor_returnsExpectedInstance() {
        final var ex = new UnsupportedArithmeticSequenceMethodException();
        assertNotNull(ex);
    }
}
