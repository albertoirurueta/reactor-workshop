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
package com.irurueta.reactorworkshop.polynomial.api.controllers;

import com.irurueta.reactorworkshop.polynomial.api.dto.EvaluationStepsDto;
import com.irurueta.reactorworkshop.polynomial.api.dto.MultiplePolynomialEvaluationResultDto;
import com.irurueta.reactorworkshop.polynomial.api.dto.factories.MultiplePolynomialEvaluationResultDtoFactory;
import com.irurueta.reactorworkshop.polynomial.api.mappers.EvaluationStepMapper;
import com.irurueta.reactorworkshop.polynomial.api.mappers.PolynomialEvaluationResultMapper;
import com.irurueta.reactorworkshop.polynomial.domain.services.MultiplePolynomialEvaluationService;
import com.irurueta.reactorworkshop.polynomial.domain.services.PolynomialParametersValidationService;
import com.irurueta.reactorworkshop.polynomial.domain.services.ReactiveMultiplePolynomialEvaluationService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.time.Instant;
import java.util.stream.Collectors;

/**
 * REST controller in charge of evaluating polynomials, finding their roots, extrema, derivatives and integrations.
 */
@RestController
@RequestMapping("/polynomial")
public class PolynomialController {

    /**
     * Service in charge of non-reactively evaluating multiple trees of polynomial evaluation steps, obtaining their
     * final results and finding their roots, extrema, derivatives and integrations.
     */
    private final MultiplePolynomialEvaluationService evaluationService;

    /**
     * Service in charge of reactively evaluating multiple trees of polynomial evaluation steps, obtaining their final
     * results and finding their roots, extrema, derivatives and integrations.
     */
    private final ReactiveMultiplePolynomialEvaluationService reactiveEvaluationService;

    /**
     * Service to validate provided input polynomials.
     */
    private final PolynomialParametersValidationService validationService;

    /**
     * Maps polynomial evaluation steps between DTO's and domain.
     */
    private final EvaluationStepMapper stepMapper;

    /**
     * Maps polynomial evaluation results between domain and DTO's.
     */
    private final PolynomialEvaluationResultMapper resultMapper;

    /**
     * Build DTO instances containing results of requests.
     */
    private final MultiplePolynomialEvaluationResultDtoFactory factory;

    /**
     * Constructor.
     *
     * @param evaluationService         service in charge of non-reactively evaluating polynomials.
     * @param reactiveEvaluationService service in charge of reactively evaluating polynomials.
     * @param validationService         service to validate order of provided input polynomials.
     * @param stepMapper                maps polynomial evaluation steps from DTO's.
     * @param resultMapper              maps polynomial evaluation results to DTO's.
     * @param factory                   builds DTO instances containing results.
     */
    public PolynomialController(
            final MultiplePolynomialEvaluationService evaluationService,
            final ReactiveMultiplePolynomialEvaluationService reactiveEvaluationService,
            final PolynomialParametersValidationService validationService,
            final EvaluationStepMapper stepMapper,
            final PolynomialEvaluationResultMapper resultMapper,
            final MultiplePolynomialEvaluationResultDtoFactory factory) {
        this.evaluationService = evaluationService;
        this.reactiveEvaluationService = reactiveEvaluationService;
        this.validationService = validationService;
        this.stepMapper = stepMapper;
        this.resultMapper = resultMapper;
        this.factory = factory;
    }

    /**
     * Evaluates a collection of evaluation steps to compute the final polynomials for each provided step and compute
     * their roots, extrema, derivatives and integrations.
     * This implementation is non reactive.
     * Optionally a delay can be provided between evaluations of different polynomials to observe the impact in
     * performance (used memory, cpu and threads) in the server.
     *
     * @param steps       a collection containing trees of evaluation steps to obtain polynomials.
     * @param delayMillis optional amount of delay between polynomials in the collection (expressed in milliseconds).
     * @return results of evaluation.
     */
    @PostMapping("/non-reactive")
    public MultiplePolynomialEvaluationResultDto evaluateNonReactive(
            @RequestBody final EvaluationStepsDto steps,
            @RequestParam(value = "delay", required = false) final Long delayMillis) {
        final var startInstant = Instant.now();

        final var stepList = stepMapper.mapFromDto(steps);
        validationService.validate(stepList);

        final var results = evaluationService.evaluate(stepList, delayMillis)
                .stream().map(resultMapper::mapToDto).collect(Collectors.toList());

        final var endInstant = Instant.now();
        final var duration = Duration.between(startInstant, endInstant);

        return factory.build(results, duration);
    }

    /**
     * Evaluates a collection of evaluation steps to compute the final polynomials for each provided step and compute
     * their roots, extrema, derivatives and integrations.
     * This implementation is reactive.
     * Optionally a delay can be provided between evaluations of different polynomials to observe the impact in
     * performance (used memory, cpu and threads) in the server.
     *
     * @param steps       a collection containing trees of evaluation steps to obtain polynomials.
     * @param delayMillis optional amount of delay between polynomials in the collection (expressed in milliseconds).
     * @return results of evaluation.
     */
    @PostMapping("/reactive")
    public Mono<MultiplePolynomialEvaluationResultDto> evaluateReactive(
            @RequestBody final EvaluationStepsDto steps,
            @RequestParam(value = "delay", required = false) final Long delayMillis) {

        return Mono.error(new UnsupportedOperationException());
    }
}
