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
package com.irurueta.reactorworkshop.arithmeticsequence.domain.services;

import com.irurueta.reactorworkshop.ReactorWorkshopConfiguration;
import org.springframework.stereotype.Service;

/**
 * Validates provided data to compute an arithmetic sequence
 */
@Service
public class ArithmeticSequenceValidationService {

    /**
     * Maximum allowed number of arithmetic sequences to compute in a single request.
     */
    private final int maxArithmeticSequenceCount;

    /**
     * Constructor.
     *
     * @param configuration service configuration.
     */
    public ArithmeticSequenceValidationService(final ReactorWorkshopConfiguration configuration) {
        this.maxArithmeticSequenceCount = configuration.getMaxArithmeticSequenceCount();
    }

    /**
     * Validates number of arithmetic sequences to compute.
     *
     * @param count number of arithmetic sequences to compute.
     * @throws IllegalArgumentException if provided value exceeds allowed maximum.
     */
    public void validate(final int count) {
        if (count > maxArithmeticSequenceCount) {
            throw new IllegalArgumentException("Too many arithmetic sequences: " + count + ". Maximum allowed is: "
                    + maxArithmeticSequenceCount);
        }
    }

    /**
     * Gets maximum allowed number of arithmetic sequences to compute in a single request.
     *
     * @return maximum allowed number of arithmetic sequences to compute in a single request.
     */
    public int getMaxArithmeticSequenceCount() {
        return maxArithmeticSequenceCount;
    }
}
