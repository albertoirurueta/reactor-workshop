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
package com.irurueta.reactorworkshop.polynomial.api.dto.factories;

import com.irurueta.reactorworkshop.MemoryUsageEstimator;
import com.irurueta.reactorworkshop.polynomial.api.dto.MultiplePolynomialEvaluationResultDto;
import com.irurueta.reactorworkshop.polynomial.api.dto.PolynomialEvaluationResultDto;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.List;

@Component
public class MultiplePolynomialEvaluationResultDtoFactory {

    /**
     * A memory usage estimator to obtain currently used memory.
     */
    private final MemoryUsageEstimator memoryUsageEstimator;

    public MultiplePolynomialEvaluationResultDtoFactory(final MemoryUsageEstimator memoryUsageEstimator) {
        this.memoryUsageEstimator = memoryUsageEstimator;
    }

    public MultiplePolynomialEvaluationResultDto build(
            final List<PolynomialEvaluationResultDto> results, final Duration duration) {
        return MultiplePolynomialEvaluationResultDto.builder()
                .results(results)
                .executionDurationSeconds(duration.getSeconds())
                .executionDurationNanos(duration.getNano())
                .memoryUsageBytes(memoryUsageEstimator.getMemoryUsageBytes())
                .build();
    }
}
