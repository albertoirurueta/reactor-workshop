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
package com.irurueta.reactorworkshop.polynomial.domain.services;

import com.irurueta.reactorworkshop.ReactorWorkshopConfiguration;
import com.irurueta.reactorworkshop.polynomial.domain.entities.EvaluationStep;
import com.irurueta.reactorworkshop.polynomial.domain.entities.Operation;
import com.irurueta.reactorworkshop.polynomial.domain.exceptions.InvalidEvaluationStepException;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

/**
 * Validates polynomial degree of provided input evaluation step tree, along with the depth of the tree and the number
 * of polynomials.
 * Notice that maximum allowed values are configurable, but not refreshable. To modify them, service must be restarted.
 */
@Service
public class PolynomialParametersValidationService {

    /**
     * Maximum allowed degree of provided input polynomials.
     */
    private final int maxDegree;

    /**
     * Maximum allowed depth of polynomial evaluation steps tree.
     */
    private final int maxPolynomialTreeDepth;

    /**
     * Maximum allowed number of polynomial trees to evaluate in a single request.
     */
    private final int maxPolynomialCount;

    /**
     * Constructor.
     *
     * @param configuration service configuration.
     */
    public PolynomialParametersValidationService(final ReactorWorkshopConfiguration configuration) {
        this.maxDegree = configuration.getMaxPolynomialDegree();
        this.maxPolynomialTreeDepth = configuration.getMaxPolynomialTreeDepth();
        this.maxPolynomialCount = configuration.getMaxPolynomialCount();
    }

    /**
     * Validates a list of polynomial evaluation steps to check maximum allowed polynomial degree,
     * maximum depth of evaluation tree and maximum number of polynomials.
     *
     * @param steps steps to validate.
     * @throws InvalidEvaluationStepException if validation fails.
     */
    public void validate(final List<EvaluationStep> steps) {
        if (steps == null) {
            return;
        }

        if (steps.size() > maxPolynomialCount) {
            throw new InvalidEvaluationStepException("Too many evaluation steps: " + steps.size()
                    + ". Maximum allowed is: " + maxPolynomialCount);
        }
        steps.forEach(step -> validateStep(step, 0));
    }

    /**
     * Validate a polynomial evaluation step to check maximum allowed polynomial degree,
     * and maximum depth of evaluation tree.
     *
     * @param step       input evaluation step tree.
     * @param depthLevel depth level of evaluation tree.
     * @throws InvalidEvaluationStepException if validation fails.
     */
    private void validateStep(final EvaluationStep step, int depthLevel) {
        if (step == null) {
            return;
        }

        if (depthLevel > maxPolynomialTreeDepth) {
            throw new InvalidEvaluationStepException("Maximum depth of evaluation step tree exceeded. Current depth: "
                    + depthLevel + ". Maximum allowed depth: " + maxPolynomialTreeDepth);
        }

        // validate length of polynomial parameters for literal steps
        final var degree = step.getLiteralPolynomial() != null ? step.getLiteralPolynomial().getDegree() : 0;
        if (step.getOperation() == Operation.LITERAL && degree > maxDegree) {
            throw new InvalidEvaluationStepException("Maximum allowed degree exceeded for polynomial: "
                    + Arrays.toString(step.getLiteralPolynomial().getPolyParams()) + ". Degree: " + degree
                    + ". Maximum allowed degree: " + maxDegree);
        } else {
            final var nextDepthLevel = depthLevel + 1;
            validateStep(step.getOperand1(), nextDepthLevel);
            validateStep(step.getOperand2(), nextDepthLevel);
        }
    }

    /**
     * Gets maximum allowed degree of provided input polynomials.
     *
     * @return maximum allowed degree of provided input polynomials.
     */
    public int getMaxDegree() {
        return maxDegree;
    }

    /**
     * Gets maximum allowed depth of polynomial evaluation steps trees.
     *
     * @return maximum allowed depth of polynomial evaluation steps trees.
     */
    public int getMaxPolynomialTreeDepth() {
        return maxPolynomialTreeDepth;
    }

    /**
     * Gets maximum allowed number of polynomial trees to evaluate in a single request.
     *
     * @return maximum allowed number of polynomial trees to evaluate in a single request.
     */
    public int getMaxPolynomialCount() {
        return maxPolynomialCount;
    }
}
