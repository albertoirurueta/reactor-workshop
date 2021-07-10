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
package com.irurueta.reactorworkshop.arithmeticsequence.api.mappers;

import com.irurueta.reactorworkshop.arithmeticsequence.domain.entities.ArithmeticSequenceMethod;
import com.irurueta.reactorworkshop.arithmeticsequence.domain.entities.MultipleArithmeticSequenceData;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

/**
 * Mapper to convert arithmetic sequence request data into domain entities.
 */
@Mapper(componentModel = "spring")
public interface MultipleArithmeticSequenceDataMapper {

    /**
     * Maps request data into a domain entity containing such information.
     *
     * @param minValue       minimum value of all requested arithmetic sequences.
     * @param step           step between values of all requested arithmetic sequences.
     * @param count          maximum number of elements of requested arithmetic sequences. Multiple arithmetic sequences
     *                       will be computed containing from 0 up to count elements.
     * @param sequenceMethod method used to compute arithmetic sequence. Can be either "exhaustive" or "fast".
     * @return mapped entity.
     */
    @Mappings({
            @Mapping(source = "minValue", target = "minValue"),
            @Mapping(source = "step", target = "step"),
            @Mapping(source = "count", target = "count"),
            @Mapping(source = "sequenceMethod", target = "sequenceMethod")
    })
    MultipleArithmeticSequenceData mapFromDto(final Integer minValue, final Integer step, final Integer count,
                                              final String sequenceMethod);

    /**
     * Maps a string into an {@link ArithmeticSequenceMethod}.
     *
     * @param sequenceMethod a string value.
     * @return mapped domain enum.
     */
    default ArithmeticSequenceMethod mapFromString(final String sequenceMethod) {
        return ArithmeticSequenceMethod.fromValue(sequenceMethod);
    }
}
