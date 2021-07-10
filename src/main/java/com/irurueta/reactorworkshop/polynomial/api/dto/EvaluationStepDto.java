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

import lombok.Builder;
import lombok.Data;

/**
 * DTO containing a step in the tree of evaluations to compute a polynomial.
 */
@Data
@Builder
public class EvaluationStepDto {

    /**
     * Operation to perform at this step. Can be either "summation", "subtraction", "multiplication" or "literal".
     * Summation is used to add two polynomials obtained from evaluating the steps in {@link #operand1} and
     * {@link #operand2}.
     * Subtraction is used to subtract two polynomials obtained from evaluating the steps in {@link #operand1} and
     * {@link #operand2}.
     * Multiplication is used to multiply two polynomials obtained from evaluating the steps in {@link #operand1} and
     * {@link #operand2}.
     * Literal indicates that this step contains the literal polynomial parameters to be used.
     */
    private String operation;

    /**
     * Contains an additional step to be used as 1st operand for "summation", "subtraction" or "multiplication"
     * operations.
     */
    private EvaluationStepDto operand1;

    /**
     * Contains an additional step to be used as 2nd operand for "summation", "subtraction" or "multiplication"
     * operations.
     */
    private EvaluationStepDto operand2;

    /**
     * Polynomial parameters to be used literally.
     */
    private double[] literalPolynomialParameters;
}
