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

import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

public class MultiplePolynomialEvaluationResultDtoTest {

    private static final long EXECUTION_DURATION_SECONDS = 1L;

    private static final int EXECUTION_DURATION_NANOS = 100000;

    private static final long MEMORY_USAGE_BYTES = 1024L * 1024L;

    @Test
    public void build_whenNoValues_returnsDefaultValues() {
        final var dto = MultiplePolynomialEvaluationResultDto.builder().build();

        assertNull(dto.getResults());
        assertNull(dto.getExecutionDurationSeconds());
        assertNull(dto.getExecutionDurationNanos());
        assertNull(dto.getMemoryUsageBytes());
    }

    @Test
    public void results_hasExpectedValue() {
        final var results = Collections.<PolynomialEvaluationResultDto>emptyList();
        final var dto = MultiplePolynomialEvaluationResultDto.builder()
                .results(results).build();

        assertSame(results, dto.getResults());
    }

    @Test
    public void executionDurationSeconds_hasExpectedValue() {
        final var dto = MultiplePolynomialEvaluationResultDto.builder()
                .executionDurationSeconds(EXECUTION_DURATION_SECONDS).build();

        assertEquals(EXECUTION_DURATION_SECONDS, dto.getExecutionDurationSeconds());
    }

    @Test
    public void executionDurationNanos_hasExpectedValue() {
        final var dto = MultiplePolynomialEvaluationResultDto.builder()
                .executionDurationNanos(EXECUTION_DURATION_NANOS).build();

        assertEquals(EXECUTION_DURATION_NANOS, dto.getExecutionDurationNanos());
    }

    @Test
    public void memoryUsageBytes_hasExpectedValue() {
        final var dto = MultiplePolynomialEvaluationResultDto.builder()
                .memoryUsageBytes(MEMORY_USAGE_BYTES).build();

        assertEquals(MEMORY_USAGE_BYTES, dto.getMemoryUsageBytes());
    }
}