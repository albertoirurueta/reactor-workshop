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

import lombok.Data;

/**
 * DTO containing results for evaluating a single tree of evaluation steps, which results in a single polynomial.
 */
@Data
public class PolynomialEvaluationResultDto {

    /**
     * Roots of resulting polynomial.
     */
    private ComplexDto[] roots;

    /**
     * Minima of resulting polynomial.
     */
    private double[] minima;

    /**
     * Maxima of resulting polynomial.
     */
    private double[] maxima;

    /**
     * Parameters of resulting polynomial.
     */
    private double[] polynomialParameters;

    /**
     * Parameters of 1st derivative of resulting polynomial.
     */
    private double[] firstDerivativePolynomialParameters;

    /**
     * Parameters of 2nd derivative of resulting polynomial.
     */
    private double[] secondDerivativePolynomialParameters;

    /**
     * Parameters of integration of resulting polynomial.
     */
    private double[] integrationPolynomialParameters;
}
