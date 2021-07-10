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
package com.irurueta.reactorworkshop.polynomial.api.dto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

public class ComplexDtoTest {

    private ComplexDto dto;

    @BeforeEach
    public void setUp() {
        dto = new ComplexDto();
    }

    @Test
    public void real_returnsExpectedValue() {
        // check default value
        assertEquals(0.0, dto.getReal(), 0.0);

        // set new value
        final var real = new Random().nextDouble();
        dto.setReal(real);

        // check
        assertEquals(real, dto.getReal(), 0.0);
    }

    @Test
    public void imaginary_returnsExpectedValue() {
        // check default value
        assertEquals(0.0, dto.getImaginary(), 0.0);

        // set new value
        final var imaginary = new Random().nextDouble();
        dto.setImaginary(imaginary);

        // check
        assertEquals(imaginary, dto.getImaginary(), 0.0);
    }
}