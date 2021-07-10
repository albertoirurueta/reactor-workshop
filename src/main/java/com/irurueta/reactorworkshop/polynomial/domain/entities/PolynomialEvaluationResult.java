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
package com.irurueta.reactorworkshop.polynomial.domain.entities;

import com.irurueta.algebra.Complex;
import com.irurueta.numerical.polynomials.Polynomial;
import lombok.Builder;
import lombok.Data;

/**
 * Contains results for evaluating a single tree of evaluation steps, which results in a single polynomial.
 */
@Data
@Builder
public class PolynomialEvaluationResult {

    /**
     * Roots of resulting polynomial.
     */
    private Complex[] roots;

    /**
     * Minima of resulting polynomial.
     */
    private double[] minima;

    /**
     * Maxima of resulting polynomial.
     */
    private double[] maxima;

    /**
     * Resulting polynomial.
     */
    private Polynomial polynomial;

    /**
     * 1st derivative of resulting polynomial.
     */
    private Polynomial firstDerivative;

    /**
     * 2nd derivative of resulting polynomial.
     */
    private Polynomial secondDerivative;

    /**
     * Integration of resulting polynomial.
     */
    private Polynomial integration;
}
