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

import com.irurueta.reactorworkshop.arithmeticsequence.domain.entities.ArithmeticSequenceMethod;
import com.irurueta.reactorworkshop.arithmeticsequence.domain.entities.MultipleArithmeticSequenceData;
import com.irurueta.reactorworkshop.arithmeticsequence.domain.entities.SingleArithmeticSequenceResult;
import com.irurueta.reactorworkshop.arithmeticsequence.domain.exceptions.UnsupportedArithmeticSequenceMethodException;

import javax.validation.constraints.NotNull;
import java.util.stream.Stream;

/**
 * Base class to compute multiple arithmetic sequences.
 * Implementations can be both reactive and non-reactive.
 *
 * @param <T> type of computation result.
 */
public abstract class BaseMultipleArithmeticSequenceService<T> {

    /**
     * Service to exhaustively compute sum of elements in an arithmetic sequence.
     */
    private final ExhaustiveArithmeticSequenceService exhaustiveArithmeticSequenceService;

    /**
     * Service to compute sum of elements in an arithmetic sequence using Gauss formula.
     */
    private final FastArithmeticSequenceService fastArithmeticSequenceService;

    /**
     * Constructor.
     *
     * @param exhaustiveArithmeticSequenceService service to exhaustively compute sum of elements in an arithmetic
     *                                            sequence.
     * @param fastArithmeticSequenceService       service to compute sum of elements in an arithmetic sequence using
     *                                            Gauss formula.
     */
    protected BaseMultipleArithmeticSequenceService(
            final ExhaustiveArithmeticSequenceService exhaustiveArithmeticSequenceService,
            final FastArithmeticSequenceService fastArithmeticSequenceService) {
        this.exhaustiveArithmeticSequenceService = exhaustiveArithmeticSequenceService;
        this.fastArithmeticSequenceService = fastArithmeticSequenceService;
    }

    /**
     * Computes multiple arithmetic sequences.
     *
     * @param data data containing parameters to compute multiple arithmetic sequences.
     * @return computed arithmetic sequences.
     */
    public T compute(@NotNull final MultipleArithmeticSequenceData data) {
        var sequenceMethod = data.getSequenceMethod();

        if (sequenceMethod == ArithmeticSequenceMethod.EXHAUSTIVE) {
            return internalCompute(data, exhaustiveArithmeticSequenceService);
        } else if (sequenceMethod == ArithmeticSequenceMethod.FAST) {
            return internalCompute(data, fastArithmeticSequenceService);
        }

        throw new UnsupportedArithmeticSequenceMethodException();
    }

    /**
     * Creates a stream to compute multiple arithmetic sequences.
     *
     * @param data    data containing parameters to compute multiple arithmetic sequences.
     * @param service service to compute each arithmetic sequence either exhaustively or using Gauss formula.
     * @return stream to compute multiple arithmetic sequences.
     */
    protected Stream<SingleArithmeticSequenceResult> createStream(
            final MultipleArithmeticSequenceData data, final ArithmeticSequenceService service) {
        var minValue = data.getMinValue();
        var step = data.getStep();
        var count = data.getCount();

        if (count < 1) {
            throw new IllegalArgumentException("Count must be 1 or greater.");
        }

        return Stream.iterate(1, n -> n + 1).limit(count).map(value -> {
            var sum = service.compute(minValue, step, value);
            return SingleArithmeticSequenceResult.builder()
                    .minValue(minValue)
                    .step(step)
                    .count(value)
                    .maxValue(minValue + step * (value - 1))
                    .sum(sum)
                    .build();
        });
    }

    /**
     * Internally computes multiple arithmetic sequences.
     *
     * @param data    data containing parameters to compute multiple arithmetic sequences.
     * @param service service to compute each arithmetic sequence either exhaustively or using Gauss formula.
     * @return computed result.
     */
    protected abstract T internalCompute(
            MultipleArithmeticSequenceData data, ArithmeticSequenceService service);
}
