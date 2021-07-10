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

import lombok.Data;

/**
 * Contains data to start the computation of multiple arithmetic sequences.
 */
@Data
public class MultipleArithmeticSequenceData {

    /**
     * Minimum value where arithmetic sequences will start.
     */
    private int minValue;

    /**
     * Step between values in computed arithmetic sequences.
     */
    private int step;

    /**
     * Number of arithmetic sequences to be computed.
     */
    private int count;

    /**
     * Method used for arithmetic sequence computation.
     */
    private ArithmeticSequenceMethod sequenceMethod;
}
