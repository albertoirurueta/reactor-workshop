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

import com.irurueta.numerical.polynomials.Polynomial;
import lombok.Data;

/**
 * Contains a step in the tree of evaluations to compute a polynomial.
 */
@Data
public class EvaluationStep {

    /**
     * Operation to perform at this step.
     * {@link Operation#SUMMATION} is used to add two polynomials obtained from evaluating the steps in
     * {@link #operand1} and {@link #operand2}.
     * {@link Operation#SUBTRACTION} is used to subtract two polynomials obtained from evaluating the steps in
     * {@link #operand1} and {@link #operand2}.
     * {@link Operation#MULTIPLICATION} is used to multiply two polynomials obtained from evaluating the steps in
     * {@link #operand1} and {@link #operand2}.
     * {@link Operation#LITERAL} indicates that this step contains the literal polynomial parameters to be used.
     */
    private Operation operation;

    /**
     * Contains an additional step to be used as 1st operand for {@link Operation#SUMMATION},
     * {@link Operation#SUBTRACTION} or {@link Operation#MULTIPLICATION} operations.
     */
    private EvaluationStep operand1;

    /**
     * Contains an additional step to be used as 2nd operand for {@link Operation#SUMMATION},
     * {@link Operation#SUBTRACTION} or {@link Operation#MULTIPLICATION} operations.
     */
    private EvaluationStep operand2;

    /**
     * Polynomial to be used literally.
     */
    private Polynomial literalPolynomial;
}
