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
import com.irurueta.reactorworkshop.arithmeticsequence.api.dto.MultipleArithmeticSequenceSummaryDto;
import org.springframework.stereotype.Component;

import java.time.Duration;

/**
 * Creates an instance of {@link MultipleArithmeticSequenceSummaryDto} to be used as a summary response in REST API.
 */
@Component
public class MultipleArithmeticSequenceSummaryDtoFactory {

    /**
     * A memory usage estimator to obtain currently used memory.
     */
    private final MemoryUsageEstimator memoryUsageEstimator;

    /**
     * Constructor.
     *
     * @param memoryUsageEstimator A memory usage estimator to obtain currently used memory.
     */
    public MultipleArithmeticSequenceSummaryDtoFactory(final MemoryUsageEstimator memoryUsageEstimator) {
        this.memoryUsageEstimator = memoryUsageEstimator;
    }

    /**
     * Builds an instance of {@link MultipleArithmeticSequenceSummaryDto}.
     *
     * @param totalSum total sum of all sums obtained for all computed arithmetic sequences.
     * @param count    total number of arithmetic sequences that have been computed.
     * @param duration duration it took to execute the request.
     * @return created {@link MultipleArithmeticSequenceSummaryDto}.
     */
    public MultipleArithmeticSequenceSummaryDto build(final long totalSum, final int count, final Duration duration) {
        return MultipleArithmeticSequenceSummaryDto.builder()
                .totalSum(totalSum)
                .count(count)
                .executionDurationSeconds(duration.getSeconds())
                .executionDurationNanos(duration.getNano())
                .memoryUsageBytes(memoryUsageEstimator.getMemoryUsageBytes())
                .build();
    }
}
