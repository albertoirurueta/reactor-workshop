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
import com.irurueta.reactorworkshop.arithmeticsequence.api.dto.MultipleArithmeticSequenceDetailDto;
import com.irurueta.reactorworkshop.arithmeticsequence.api.dto.SingleArithmeticSequenceResultDto;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.List;

/**
 * Creates an instance of {@link MultipleArithmeticSequenceDetailDto} to be used as a detailed response in REST API.
 */
@Component
public class MultipleArithmeticSequenceDetailDtoFactory {

    /**
     * A memory usage estimator to obtain currently used memory.
     */
    private final MemoryUsageEstimator memoryUsageEstimator;

    /**
     * Constructor.
     *
     * @param memoryUsageEstimator A memory usage estimator to obtain currently used memory.
     */
    public MultipleArithmeticSequenceDetailDtoFactory(final MemoryUsageEstimator memoryUsageEstimator) {
        this.memoryUsageEstimator = memoryUsageEstimator;
    }

    /**
     * Builds an instance of {@link MultipleArithmeticSequenceDetailDto}.
     *
     * @param results  list of results for requested arithmetic sequences.
     * @param duration duration it took to execute the request.
     * @return created {@link MultipleArithmeticSequenceDetailDto}.
     */
    public MultipleArithmeticSequenceDetailDto build(
            final List<SingleArithmeticSequenceResultDto> results,
            final Duration duration) {
        return MultipleArithmeticSequenceDetailDto.builder()
                .results(results)
                .executionDurationSeconds(duration.getSeconds())
                .executionDurationNanos(duration.getNano())
                .memoryUsageBytes(memoryUsageEstimator.getMemoryUsageBytes())
                .build();
    }
}
