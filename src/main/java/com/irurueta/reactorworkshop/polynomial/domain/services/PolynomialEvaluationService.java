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

import com.irurueta.numerical.NumericalException;
import com.irurueta.numerical.polynomials.Polynomial;
import com.irurueta.reactorworkshop.polynomial.domain.entities.EvaluationStep;
import com.irurueta.reactorworkshop.polynomial.domain.entities.Operation;
import com.irurueta.reactorworkshop.polynomial.domain.entities.PolynomialEvaluationResult;
import com.irurueta.reactorworkshop.polynomial.domain.exceptions.InvalidEvaluationStepException;
import com.irurueta.reactorworkshop.polynomial.domain.exceptions.PolynomialEvaluationException;
import org.springframework.stereotype.Service;

/**
 * Evaluates a single evaluation step tree to obtain a single resulting polynomial and its associated results (roots,
 * extrema, derivatives and integration).
 */
@Service
public class PolynomialEvaluationService {

    /**
     * Evaluates a single evaluation step tree to obtain a single resulting polynomial and its associated results
     * (roots, extrema, derivatives and integration).
     *
     * @param step a single evaluation step tree.
     * @return results for resulting polynomial.
     * @throws PolynomialEvaluationException if computation of roots or extrema of resulting evaluated polynomial fails
     *                                       for some reason.
     * @throws InvalidEvaluationStepException if tree of evaluation steps contains invalid data.
     */
    public PolynomialEvaluationResult evaluate(final EvaluationStep step) {
        try {
            final var polynomial = buildPolynomialFromStep(step);

            final var roots = polynomial.getRoots();
            final var minima = polynomial.getMinima();
            final var maxima = polynomial.getMaxima();

            final var firstDerivative = polynomial.derivativeAndReturnNew();
            final var secondDerivative = polynomial.nthDerivativeAndReturnNew(2);
            final var integration = polynomial.integrationAndReturnNew();

            return PolynomialEvaluationResult.builder()
                    .roots(roots)
                    .minima(minima)
                    .maxima(maxima)
                    .polynomial(polynomial)
                    .firstDerivative(firstDerivative)
                    .secondDerivative(secondDerivative)
                    .integration(integration)
                    .build();

        } catch (final NumericalException e) {
            throw new PolynomialEvaluationException(e);
        }
    }

    /**
     * Internally evaluates a single step.
     * If the step is literal, returns provided input, otherwise it is evaluated.
     *
     * @param step step to be evaluated.
     * @return evaluated step or provided one if it is literal.
     * @throws InvalidEvaluationStepException if step has no associated operation.
     */
    private EvaluationStep internalEvaluate(final EvaluationStep step) {
        if (step.getOperation() != null) {
            switch (step.getOperation()) {
                case SUMMATION:
                    return evaluateSummationStep(step);
                case SUBTRACTION:
                    return evaluateSubtractionStep(step);
                case MULTIPLICATION:
                    return evaluateMultiplicationStep(step);
                case LITERAL:
                    validateLiteralStep(step);
                    return step;
            }
        }

        throw new InvalidEvaluationStepException("Invalid operation");
    }

    /**
     * Evaluates a step containing a summation of two polynomials.
     *
     * @param step step to be evaluated.
     * @return a literal step containing summation of input step operands.
     */
    private EvaluationStep evaluateSummationStep(final EvaluationStep step) {
        validateOperands(step);

        final var polynomial1 = buildPolynomialFromStep(step.getOperand1());
        final var polynomial2 = buildPolynomialFromStep(step.getOperand2());

        return buildLiteralEvaluationStep(polynomial1.addAndReturnNew(polynomial2));
    }

    /**
     * Evaluates a step containing a subtraction of two polynomials.
     *
     * @param step step to be evaluated.
     * @return a literal step containing subtraction of input step operands.
     */
    private EvaluationStep evaluateSubtractionStep(final EvaluationStep step) {
        validateOperands(step);

        final var polynomial1 = buildPolynomialFromStep(step.getOperand1());
        final var polynomial2 = buildPolynomialFromStep(step.getOperand2());

        polynomial2.multiplyByScalar(-1.0);

        return buildLiteralEvaluationStep(polynomial1.addAndReturnNew(polynomial2));
    }

    /**
     * Evaluates a step containing a multiplication of two polynomials.
     *
     * @param step step to be evaluated.
     * @return a literal step containing multiplication of intput step operands.
     */
    private EvaluationStep evaluateMultiplicationStep(final EvaluationStep step) {
        validateOperands(step);

        final var polynomial1 = buildPolynomialFromStep(step.getOperand1());
        final var polynomial2 = buildPolynomialFromStep(step.getOperand2());

        return buildLiteralEvaluationStep(polynomial1.multiplyAndReturnNew(polynomial2));
    }

    /**
     * Builds a polynomial from an evaluation step by evaluating it and returning its literal polynomial.
     *
     * @param step step to be evaluated.
     * @return evaluated polynomial.
     */
    private Polynomial buildPolynomialFromStep(final EvaluationStep step) {
        return internalEvaluate(step).getLiteralPolynomial();
    }

    /**
     * Builds a literal evaluation step from provided polynomial.
     *
     * @param polynomial a polynomial to build a literal evaluation step.
     * @return a literal evaluation step containing provided polynomial.
     */
    private EvaluationStep buildLiteralEvaluationStep(final Polynomial polynomial) {
        final var step = new EvaluationStep();
        step.setOperation(Operation.LITERAL);
        step.setLiteralPolynomial(polynomial);

        return step;
    }

    /**
     * Validates operands of a non-literal evaluation step requiring both operands.
     *
     * @param step step to be validated.
     * @throws InvalidEvaluationStepException if any operand is missing.
     */
    private void validateOperands(final EvaluationStep step) {
        if (step.getOperand1() == null || step.getOperand2() == null) {
            throw new InvalidEvaluationStepException("Operands are missing");
        }
    }

    /**
     * Validates that a literal step contains a literal polynomial.
     *
     * @param step step to be validated.
     * @throws InvalidEvaluationStepException if literal polynomial is missing.
     */
    private void validateLiteralStep(final EvaluationStep step) {
        if (step.getLiteralPolynomial() == null) {
            throw new InvalidEvaluationStepException("Literal polynomial is missing");
        }
    }
}
