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

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class MultipleArithmeticSequenceDetailDtoTest {

    private static final Long SECONDS = 123L;

    private static final Integer NANOS = 456;

    private static final Long BYTES = 789L;

    @Test
    public void build_whenNoValues_returnsDefaultValues() {
        final var detail = MultipleArithmeticSequenceDetailDto.builder().build();

        assertNull(detail.getResults());
        assertNull(detail.getExecutionDurationSeconds());
        assertNull(detail.getExecutionDurationNanos());
        assertNull(detail.getMemoryUsageBytes());
    }

    @Test
    public void results_hasExpectedValue() {
        final List<SingleArithmeticSequenceResultDto> results = Collections.emptyList();
        final var detail = MultipleArithmeticSequenceDetailDto.builder().results(results).build();

        assertSame(results, detail.getResults());
    }

    @Test
    public void executionDurationSeconds_hasExpectedValue() {
        final var detail = MultipleArithmeticSequenceDetailDto.builder().executionDurationSeconds(SECONDS).build();
        assertEquals(SECONDS, detail.getExecutionDurationSeconds());
    }

    @Test
    public void executionDurationNanos_hasExpectedValue() {
        final var detail = MultipleArithmeticSequenceDetailDto.builder().executionDurationNanos(NANOS).build();
        assertEquals(NANOS, detail.getExecutionDurationNanos());
    }

    @Test
    public void memoryUsageBytes_hasExpectedValue() {
        final var detail = MultipleArithmeticSequenceDetailDto.builder().memoryUsageBytes(BYTES).build();
        assertEquals(BYTES, detail.getMemoryUsageBytes());
    }
}