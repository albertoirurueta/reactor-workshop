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
import static org.junit.jupiter.api.Assertions.assertNull;

public class MultipleArithmeticSequenceSummaryDtoTest {

    private static final long TOTAL_SUM = 123L;

    private static final int COUNT = 456;

    private static final Long SECONDS = 789L;

    private static final Integer NANOS = 987;

    private static final Long BYTES = 654L;

    @Test
    public void build_whenNoValues_returnsDefaultValues() {
        final var summary = MultipleArithmeticSequenceSummaryDto.builder().build();

        assertEquals(0, summary.getTotalSum());
        assertEquals(0, summary.getCount());
        assertNull(summary.getExecutionDurationSeconds());
        assertNull(summary.getExecutionDurationNanos());
        assertNull(summary.getMemoryUsageBytes());
    }

    @Test
    public void totalSum_hasExpectedValue() {
        final var summary = MultipleArithmeticSequenceSummaryDto.builder().totalSum(TOTAL_SUM).build();
        assertEquals(TOTAL_SUM, summary.getTotalSum());
    }

    @Test
    public void count_hasExpectedValue() {
        final var summary = MultipleArithmeticSequenceSummaryDto.builder().count(COUNT).build();
        assertEquals(COUNT, summary.getCount());
    }

    @Test
    public void executionDurationSeconds_hasExpectedValue() {
        final var summary = MultipleArithmeticSequenceSummaryDto.builder().executionDurationSeconds(SECONDS).build();
        assertEquals(SECONDS, summary.getExecutionDurationSeconds());
    }

    @Test
    public void executionDurationNanos_hasExpectedValue() {
        final var summary = MultipleArithmeticSequenceSummaryDto.builder().executionDurationNanos(NANOS).build();
        assertEquals(NANOS, summary.getExecutionDurationNanos());
    }

    @Test
    public void memoryUsageBytes_hasExpectedValue() {
        final var summary = MultipleArithmeticSequenceSummaryDto.builder().memoryUsageBytes(BYTES).build();
        assertEquals(BYTES, summary.getMemoryUsageBytes());
    }
}