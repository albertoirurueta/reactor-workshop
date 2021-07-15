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
import com.irurueta.reactorworkshop.arithmeticsequence.domain.entities.ArithmeticSequenceMethod;
import com.irurueta.reactorworkshop.arithmeticsequence.domain.entities.MultipleArithmeticSequenceData;
import com.irurueta.reactorworkshop.arithmeticsequence.domain.exceptions.UnsupportedArithmeticSequenceMethodException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.stereotype.Service;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MultipleArithmeticSequenceServiceTest {

    private static final int MAX = 100;

    @Spy
    private ExhaustiveArithmeticSequenceService exhaustiveService;

    @Spy
    private FastArithmeticSequenceService fastService;

    @InjectMocks
    private MultipleArithmeticSequenceService service;

    private AutoCloseable autoCloseable;

    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }

    @Test
    void class_hasServiceAnnotation() {
        assertNotNull(TestUtils.getClassAnnotation(MultipleArithmeticSequenceService.class, Service.class));
    }

    @Test
    void class_extendsBaseMultipleArithmeticSequenceService() {
        assertTrue(BaseMultipleArithmeticSequenceService.class.isAssignableFrom(
                MultipleArithmeticSequenceService.class));
    }

    @Test
    void constructor_injectsExpectedInstances() {
        assertSame(exhaustiveService, ReflectionTestUtils.getField(service, "exhaustiveArithmeticSequenceService"));
        assertSame(fastService, ReflectionTestUtils.getField(service, "fastArithmeticSequenceService"));
    }

    @Test
    void compute_whenNegativeZeroCount_throwsIllegalArgumentException() {
        final var data = buildData(ArithmeticSequenceMethod.EXHAUSTIVE);
        data.setCount(0);

        assertThrows(IllegalArgumentException.class, () -> service.compute(data));
    }

    @Test
    void compute_whenExhaustiveData_returnsExpectedResult() {
        final var data = buildData(ArithmeticSequenceMethod.EXHAUSTIVE);
        final var results = service.compute(data);

        assertEquals(data.getCount(), results.size());

        verifyNoInteractions(fastService);
        verify(exhaustiveService, times(data.getCount())).compute(anyInt(), anyInt(), anyInt());

        for (final var result : results) {
            final var minValue = result.getMinValue();
            final var step = result.getStep();
            final var count = result.getCount();
            final var sum = result.getSum();
            final var maxValue = result.getMaxValue();

            assertEquals(sum, exhaustiveService.compute(minValue, step, count));
            assertEquals(maxValue, minValue + step * (count - 1));
        }
    }

    @Test
    void compute_whenFastData_returnsExpectedResult() {
        final var data = buildData(ArithmeticSequenceMethod.FAST);
        final var results = service.compute(data);

        assertEquals(data.getCount(), results.size());

        verifyNoInteractions(exhaustiveService);
        verify(fastService, times(data.getCount())).compute(anyInt(), anyInt(), anyInt());

        for (final var result : results) {
            final var minValue = result.getMinValue();
            final var step = result.getStep();
            final var count = result.getCount();
            final var sum = result.getSum();
            final var maxValue = result.getMaxValue();

            assertEquals(sum, fastService.compute(minValue, step, count));
            assertEquals(maxValue, minValue + step * (count - 1));
        }
    }

    @Test
    void compute_whenNoSequenceMethod_throwsUnsupportedArithmeticSequenceMethodException() {
        final var data = buildData(null);
        assertThrows(UnsupportedArithmeticSequenceMethodException.class, () -> service.compute(data));
    }

    private static MultipleArithmeticSequenceData buildData(final ArithmeticSequenceMethod method) {
        final var random = new Random();
        final var minValue = random.nextInt(MAX);
        final var step = random.nextInt(MAX);
        final var count = 1 + random.nextInt(MAX);

        final var result = new MultipleArithmeticSequenceData();
        result.setMinValue(minValue);
        result.setStep(step);
        result.setCount(count);
        result.setSequenceMethod(method);

        return result;
    }
}