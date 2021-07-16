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
package com.irurueta.reactorworkshop.polynomial.api.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

/**
 * DTO containing results for evaluating a single tree of evaluation steps, which results in a single polynomial.
 */
@Value
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class PolynomialEvaluationResultDto {

    /**
     * Roots of resulting polynomial.
     */
    ComplexDto[] roots;

    /**
     * Minima of resulting polynomial.
     */
    double[] minima;

    /**
     * Maxima of resulting polynomial.
     */
    double[] maxima;

    /**
     * Parameters of resulting polynomial.
     */
    double[] polynomialParameters;

    /**
     * Parameters of 1st derivative of resulting polynomial.
     */
    double[] firstDerivativePolynomialParameters;

    /**
     * Parameters of 2nd derivative of resulting polynomial.
     */
    double[] secondDerivativePolynomialParameters;

    /**
     * Parameters of integration of resulting polynomial.
     */
    double[] integrationPolynomialParameters;

    /**
     * Builder class.
     */
    public static class PolynomialEvaluationResultDtoBuilder {
    }
}
