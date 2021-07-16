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

import lombok.Builder;
import lombok.Value;

/**
 * DTO entity containing the summary results of computed arithmetic sequences.
 */
@Value
@Builder
public class MultipleArithmeticSequenceSummaryDto {

    /**
     * Total sum of estimated sums for each individually computed arithmetic sequence.
     */
    long totalSum;

    /**
     * Number of arithmetic sequence that have been computed.
     */
    int count;

    /**
     * Duration of execution of request expressed in seconds.
     */
    Long executionDurationSeconds;

    /**
     * Part of duration of execution of requests that took less than a second. This is used to have full duration
     * accuracy up to one nanosecond. This is expressed in nanoseconds.
     */
    Integer executionDurationNanos;

    /**
     * Amount of memory used during execution of this request. This value is approximate and depends on Garbage
     * Collection execution, that might trigger at different times depending on available system resources and JVM
     * implementation.
     */
    Long memoryUsageBytes;
}
