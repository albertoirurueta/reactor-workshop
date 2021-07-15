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
package com.irurueta.reactorworkshop.polynomial.api.mappers;

import com.irurueta.reactorworkshop.polynomial.api.dto.EvaluationStepDto;
import com.irurueta.reactorworkshop.polynomial.api.dto.EvaluationStepsDto;
import com.irurueta.reactorworkshop.polynomial.domain.entities.EvaluationStep;
import com.irurueta.reactorworkshop.polynomial.domain.entities.Operation;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Mapts evaluation steps from DTO's to domain.
 */
@Mapper(componentModel = "spring", uses = {PolynomialMapper.class})
public interface EvaluationStepMapper {

    /**
     * Maps a single evaluation step.
     *
     * @param step an evaluation step to be mapped.
     * @return mapped value.
     */
    @Mapping(source = "literalPolynomialParameters", target = "literalPolynomial")
    EvaluationStep mapFromDto(final EvaluationStepDto step);

    /**
     * Maps evaluation steps.
     *
     * @param steps evaluation steps to be mapped.
     * @return mapped value.
     */
    default List<EvaluationStep> mapFromDto(final EvaluationStepsDto steps) {
        if (steps == null) {
            return Collections.emptyList();
        }

        return steps.getSteps().stream().map(this::mapFromDto).collect(Collectors.toList());
    }

    /**
     * Maps a step operation.
     *
     * @param value string value to be mapped.
     * @return mapped operation.
     */
    default Operation mapFromDto(final String value) {
        return Operation.fromValue(value);
    }
}
