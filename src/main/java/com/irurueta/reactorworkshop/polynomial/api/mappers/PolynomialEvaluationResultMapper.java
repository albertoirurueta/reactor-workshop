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

import com.irurueta.reactorworkshop.polynomial.api.dto.PolynomialEvaluationResultDto;
import com.irurueta.reactorworkshop.polynomial.domain.entities.PolynomialEvaluationResult;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

/**
 * Maps polynomial evaluation results to DTO's.
 */
@Mapper(componentModel = "spring", uses = {ComplexMapper.class, PolynomialMapperImpl.class})
public interface PolynomialEvaluationResultMapper {

    /**
     * Maps a single polynomial evaluation result to DTO.
     *
     * @param result result to be mapped.
     * @return mapped value.
     */
    @Mappings({
            @Mapping(source = "roots", target = "roots"),
            @Mapping(source = "minima", target = "minima"),
            @Mapping(source = "maxima", target = "maxima"),
            @Mapping(source = "polynomial", target = "polynomialParameters"),
            @Mapping(source = "firstDerivative", target = "firstDerivativePolynomialParameters"),
            @Mapping(source = "secondDerivative", target = "secondDerivativePolynomialParameters"),
            @Mapping(source = "integration", target = "integrationPolynomialParameters")
    })
    PolynomialEvaluationResultDto mapToDto(PolynomialEvaluationResult result);
}