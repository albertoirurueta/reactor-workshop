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
package com.irurueta.reactorworkshop.arithmeticsequence.domain.entities;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.Setter;

/**
 * Contains result of a single arithmetic sequence that was computed.
 */
@Data
@Setter(AccessLevel.PRIVATE)
@Builder
public class SingleArithmeticSequenceResult {

    /**
     * Minimum value where arithmetic sequence starts.
     */
    private int minValue;

    /**
     * Step between values in computed arithmetic sequence.
     */
    private int step;

    /**
     * Number of elements in the arithmetic sequence.
     */
    private int count;

    /**
     * Maximum value of last element in arithmetic sequence.
     */
    private int maxValue;

    /**
     * Sum of elements in the arithmetic sequence.
     */
    private int sum;
}
