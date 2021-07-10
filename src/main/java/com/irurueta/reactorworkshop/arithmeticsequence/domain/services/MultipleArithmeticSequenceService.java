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

import com.irurueta.reactorworkshop.arithmeticsequence.domain.entities.MultipleArithmeticSequenceData;
import com.irurueta.reactorworkshop.arithmeticsequence.domain.entities.SingleArithmeticSequenceResult;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Non-reactively computes multiple arithmetic sequences.
 */
@Service
public class MultipleArithmeticSequenceService extends
        BaseMultipleArithmeticSequenceService<List<SingleArithmeticSequenceResult>> {

    /**
     * Constructor.
     *
     * @param exhaustiveArithmeticSequenceService service to exhaustively compute sum of elements in an arithmetic
     *                                            sequence.
     * @param fastArithmeticSequenceService       service to compute sum of elements in an arithmetic sequence using
     *                                            Gauss formula.
     */
    public MultipleArithmeticSequenceService(
            final ExhaustiveArithmeticSequenceService exhaustiveArithmeticSequenceService,
            final FastArithmeticSequenceService fastArithmeticSequenceService) {
        super(exhaustiveArithmeticSequenceService, fastArithmeticSequenceService);
    }

    /**
     * Internally computes multiple arithmetic sequences.
     * Computation is done non-reactively and results are stored in a list.
     *
     * @param data data containing parameters to compute multiple arithmetic sequences.
     * @param service service to compute each arithmetic sequence either exhaustively or using Gauss formula.
     * @return list containing results.
     */
    @Override
    protected List<SingleArithmeticSequenceResult> internalCompute(
            final MultipleArithmeticSequenceData data, final ArithmeticSequenceService service) {

        return createStream(data, service).collect(Collectors.toList());
    }
}
