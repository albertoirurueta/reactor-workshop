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

import com.irurueta.reactorworkshop.polynomial.domain.entities.EvaluationStep;
import com.irurueta.reactorworkshop.polynomial.domain.entities.PolynomialEvaluationResult;
import com.irurueta.reactorworkshop.polynomial.domain.exceptions.PolynomialEvaluationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Non-reactively evaluates a list of trees of evaluation steps to obtain resulting polynomials and their associated
 * results (roots, extrema, derivatives and integration).
 */
@Service
public class MultiplePolynomialEvaluationService {

    /**
     * Service to evaluate a single evaluation step tree to obtain a single resulting polynomial.
     */
    private final PolynomialEvaluationService singlePolynomialService;

    /**
     * Constructor.
     *
     * @param singlePolynomialService service to evaluate a single evaluation step tree.
     */
    public MultiplePolynomialEvaluationService(final PolynomialEvaluationService singlePolynomialService) {
        this.singlePolynomialService = singlePolynomialService;
    }

    /**
     * Evaluates a list of evaluation steps to obtain their corresponding polynomials and results and applying provided
     * delay (if any) between each obtained polynomial.
     *
     * @param evaluationSteps list of trees of evaluation steps to obtain resulting polynomials and their results.
     * @param delayMillis delay expressed in milliseconds to be applied between each obtained polynomial, or null if
     *                    none must be applied at all.
     * @return list containing results of evaluating all polynomials.
     * @throws InterruptedException if thread is interrupted
     */
    public List<PolynomialEvaluationResult> evaluate(final List<EvaluationStep> evaluationSteps,
                                                     final Long delayMillis) throws InterruptedException {

        try {
            return evaluationSteps.stream().map(step -> {
                final var result = singlePolynomialService.evaluate(step);

                try {
                    if (delayMillis != null && delayMillis > 0) {
                        Thread.sleep(delayMillis);
                    }
                } catch (final InterruptedException e) {
                    throw new PolynomialEvaluationException(e);
                }

                return result;
            }).collect(Collectors.toList());
        } catch (PolynomialEvaluationException e) {
            // rethrow interrupted exception to properly interrupt threads.
            if (e.getCause() instanceof InterruptedException) {
                throw (InterruptedException) e.getCause();
            }
            throw e;
        }
    }
}
