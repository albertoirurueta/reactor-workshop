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
package com.irurueta.reactorworkshop.arithmeticsequence.api.dto.factories;

import com.irurueta.reactorworkshop.MemoryUsageEstimator;
import com.irurueta.reactorworkshop.TestUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.stereotype.Component;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

class MultipleArithmeticSequenceSummaryDtoFactoryTest {

    private static final int TOTAL_SUM = 100;

    private static final int COUNT = 10;

    private static final long SECONDS = 1L;

    private static final int NANOS = 2;

    @Spy
    private MemoryUsageEstimator memoryUsageEstimator;

    @InjectMocks
    private MultipleArithmeticSequenceSummaryDtoFactory factory;

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
    void class_hasComponentAnnotation() {
        assertNotNull(TestUtils.getClassAnnotation(MultipleArithmeticSequenceSummaryDtoFactory.class, Component.class));
    }

    @Test
    void constructor_injectsExpectedInstances() {
        assertSame(memoryUsageEstimator, ReflectionTestUtils.getField(factory, "memoryUsageEstimator"));
    }

    @Test
    void build_returnsExpectedValue() {
        final var duration = Duration.ofSeconds(SECONDS, NANOS);
        final var dto = factory.build(TOTAL_SUM, COUNT, duration);

        assertEquals(TOTAL_SUM, dto.getTotalSum());
        assertEquals(COUNT, dto.getCount());
        assertEquals(SECONDS, dto.getExecutionDurationSeconds());
        assertEquals(NANOS, dto.getExecutionDurationNanos());
        assertTrue(dto.getMemoryUsageBytes() > 0);
    }
}