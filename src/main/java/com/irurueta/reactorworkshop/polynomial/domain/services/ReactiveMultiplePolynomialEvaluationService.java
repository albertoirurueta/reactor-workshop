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
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.time.Duration;

/**
 * Reactively evaluates a list of trees of evaluation steps to obtain resulting polynomials and their associated
 * results (roots, extrea, derivatives and integration).
 */
@Service
public class ReactiveMultiplePolynomialEvaluationService {

    /**
     * Service to evaluate a single evaluation step tree to obtain a single resulting polynomial.
     */
    private final PolynomialEvaluationService singlePolynomialService;

    /**
     * Constructor.
     *
     * @param singlePolynomialService service to evaluate a single evaluation step tree.
     */
    public ReactiveMultiplePolynomialEvaluationService(final PolynomialEvaluationService singlePolynomialService) {
        this.singlePolynomialService = singlePolynomialService;
    }

    /**
     * Evaluates a list of evaluation steps to obtain their corresponding polynomials and results and applying provided
     * delay (if any) between each obtained polynomial.
     *
     * @param evaluationSteps list of trees of evaluation steps to obtain resulting polynomials and their results.
     * @param delayMillis     delay expressed in milliseconds to be applied between each obtained polynomial, or null if
     *                        none must be applied at all.
     * @return flux containing results of evaluating all polynomials. Because they are executed in parallel, order might
     * differ respect provided input values.
     */
    public Flux<PolynomialEvaluationResult> evaluate(final Flux<EvaluationStep> evaluationSteps,
                                                     final Long delayMillis) {

        return Flux.error(new UnsupportedOperationException());
    }
}
