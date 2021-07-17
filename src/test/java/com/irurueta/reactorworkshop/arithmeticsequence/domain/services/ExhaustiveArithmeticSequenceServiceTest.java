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
package com.irurueta.reactorworkshop.arithmeticsequence.domain.services;

import com.irurueta.reactorworkshop.TestUtils;
import org.junit.jupiter.api.Test;
import org.springframework.stereotype.Service;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class ExhaustiveArithmeticSequenceServiceTest {

    private static final int MAX = 100;

    @Test
    void class_hasServiceAnnotation() {
        assertNotNull(TestUtils.getClassAnnotation(ExhaustiveArithmeticSequenceService.class, Service.class));
    }

    @Test
    void class_extendsFromArithmeticSequenceService() {
        assertTrue(ArithmeticSequenceService.class.isAssignableFrom(ExhaustiveArithmeticSequenceService.class));
    }

    @Test
    void compute_returnsExpectedValue() {
        final var random = new Random();
        final var minValue = random.nextInt(MAX);
        final var step = random.nextInt(MAX);
        final var count = 1 + random.nextInt(MAX);

        int value = minValue;
        int result = minValue;
        for (int i = 1; i < count; i++) {
            value += step;
            result += value;
        }

        final var service = new ExhaustiveArithmeticSequenceService();
        assertEquals(result, service.compute(minValue, step, count));
    }

    @Test
    void compute_whenCountIsZero_throwsException() {
        final var random = new Random();
        final var minValue = random.nextInt(MAX);
        final var step = random.nextInt(MAX);
        final var count = 0;

        final var service = new ExhaustiveArithmeticSequenceService();
        assertThrows(IllegalArgumentException.class, () -> service.compute(minValue, step, count));
    }
}