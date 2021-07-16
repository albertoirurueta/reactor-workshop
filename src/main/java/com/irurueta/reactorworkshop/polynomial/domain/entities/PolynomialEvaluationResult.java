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
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

/**
 * Contains results for evaluating a single tree of evaluation steps, which results in a single polynomial.
 */
@Value
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class PolynomialEvaluationResult {

    /**
     * Roots of resulting polynomial.
     */
    Complex[] roots;

    /**
     * Minima of resulting polynomial.
     */
    double[] minima;

    /**
     * Maxima of resulting polynomial.
     */
    double[] maxima;

    /**
     * Resulting polynomial.
     */
    Polynomial polynomial;

    /**
     * 1st derivative of resulting polynomial.
     */
    Polynomial firstDerivative;

    /**
     * 2nd derivative of resulting polynomial.
     */
    Polynomial secondDerivative;

    /**
     * Integration of resulting polynomial.
     */
    Polynomial integration;
}
