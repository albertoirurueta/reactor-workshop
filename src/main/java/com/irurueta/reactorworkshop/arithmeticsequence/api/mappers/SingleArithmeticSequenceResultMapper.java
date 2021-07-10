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

import com.irurueta.reactorworkshop.arithmeticsequence.api.dto.SingleArithmeticSequenceResultDto;
import com.irurueta.reactorworkshop.arithmeticsequence.domain.entities.SingleArithmeticSequenceResult;
import org.mapstruct.Mapper;

/**
 * Maps result of request from domain to DTO's.
 */
@Mapper(componentModel = "spring")
public interface SingleArithmeticSequenceResultMapper {

    /**
     * Maps result of a single arithmetic sequence from domain to DTO.
     *
     * @param result result to be mapped.
     * @return mapped result.
     */
    SingleArithmeticSequenceResultDto mapToDto(final SingleArithmeticSequenceResult result);
}
