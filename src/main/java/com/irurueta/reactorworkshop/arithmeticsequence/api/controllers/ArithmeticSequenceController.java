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
package com.irurueta.reactorworkshop.arithmeticsequence.api.controllers;

import com.irurueta.reactorworkshop.arithmeticsequence.api.dto.MultipleArithmeticSequenceDetailDto;
import com.irurueta.reactorworkshop.arithmeticsequence.api.dto.MultipleArithmeticSequenceSummaryDto;
import com.irurueta.reactorworkshop.arithmeticsequence.api.dto.factories.MultipleArithmeticSequenceDetailDtoFactory;
import com.irurueta.reactorworkshop.arithmeticsequence.api.dto.factories.MultipleArithmeticSequenceSummaryDtoFactory;
import com.irurueta.reactorworkshop.arithmeticsequence.api.mappers.MultipleArithmeticSequenceDataMapper;
import com.irurueta.reactorworkshop.arithmeticsequence.api.mappers.SingleArithmeticSequenceResultMapper;
import com.irurueta.reactorworkshop.arithmeticsequence.domain.entities.SingleArithmeticSequenceResult;
import com.irurueta.reactorworkshop.arithmeticsequence.domain.services.ArithmeticSequenceValidationService;
import com.irurueta.reactorworkshop.arithmeticsequence.domain.services.MultipleArithmeticSequenceService;
import com.irurueta.reactorworkshop.arithmeticsequence.domain.services.ReactiveMultipleArithmeticSequenceService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.time.Instant;
import java.util.stream.Collectors;

/**
 * REST controller in charge of computing arithmetic sequences.
 */
@RestController
@RequestMapping("/arithmetic-sequence")
public class ArithmeticSequenceController {

    /**
     * Service to non-reactively execute arithmetic sequence computation
     */
    private final MultipleArithmeticSequenceService nonReactiveService;

    /**
     * Service to reactively execute arithmetic sequence computation
     */
    private final ReactiveMultipleArithmeticSequenceService reactiveService;

    /**
     * Service to validate provided input parameters.
     */
    private final ArithmeticSequenceValidationService validationService;

    /**
     * Maps request parameters into domain entities.
     */
    private final MultipleArithmeticSequenceDataMapper dataMapper;

    /**
     * Maps result to API DTO's.
     */
    private final SingleArithmeticSequenceResultMapper resultMapper;

    /**
     * Creates DTO's for detailed results.
     */
    private final MultipleArithmeticSequenceDetailDtoFactory detailFactory;

    /**
     * Creates DTO's for summary results.
     */
    private final MultipleArithmeticSequenceSummaryDtoFactory summaryFactory;

    /**
     * Constructor.
     *
     * @param nonReactiveService Service to non-reactively execute arithmetic sequence computation.
     * @param reactiveService    Service to reactively execute arithmetic sequence computation.
     * @param validationService  Service to validate provided input parameters.
     * @param dataMapper         Maps request parameters into domain entities.
     * @param resultMapper       Maps result to API DTO's.
     * @param detailFactory      Creates DTO's for detailed results.
     * @param summaryFactory     Creates DTO's for summary results.
     */
    public ArithmeticSequenceController(final MultipleArithmeticSequenceService nonReactiveService,
                                        final ReactiveMultipleArithmeticSequenceService reactiveService,
                                        final ArithmeticSequenceValidationService validationService,
                                        final MultipleArithmeticSequenceDataMapper dataMapper,
                                        final SingleArithmeticSequenceResultMapper resultMapper,
                                        final MultipleArithmeticSequenceDetailDtoFactory detailFactory,
                                        final MultipleArithmeticSequenceSummaryDtoFactory summaryFactory) {
        this.nonReactiveService = nonReactiveService;
        this.reactiveService = reactiveService;
        this.validationService = validationService;
        this.dataMapper = dataMapper;
        this.resultMapper = resultMapper;
        this.detailFactory = detailFactory;
        this.summaryFactory = summaryFactory;
    }

    /**
     * Non-reactively computes detailed results of arithmetic sequences.
     *
     * @param minValue       minimum value where arithmetic sequences will start.
     * @param step           step between values in computed arithmetic sequences.
     * @param count          number of arithmetic sequences to be computed (containing elements from 0 up to count).
     * @param sequenceMethod method used for arithmetic sequence computation. Can be either "exhaustive" or "fast".
     * @return requested detailed results of arithmetic sequences.
     */
    @GetMapping("/non-reactive/detail")
    public MultipleArithmeticSequenceDetailDto computeDetailNonReactive(
            @RequestParam("minValue") final int minValue,
            @RequestParam("step") final int step,
            @RequestParam("count") final int count,
            @RequestParam("sequenceMethod") final String sequenceMethod) {

        validationService.validate(count);

        final var startInstant = Instant.now();

        // Notice that here a whole list of results is built. Memory usage will increase as count becomes larger
        final var results = nonReactiveService.compute(
                dataMapper.mapFromDto(minValue, step, count, sequenceMethod)).stream()
                .map(resultMapper::mapToDto).collect(Collectors.toList());
        final var endInstant = Instant.now();
        final var duration = Duration.between(startInstant, endInstant);

        return detailFactory.build(results, duration);
    }

    /**
     * Non-reactively computes summary results of arithmetic sequences.
     * Summary only contains total arithmetic sequences computed and their total sum, but
     * does not contain their detailed results.
     *
     * @param minValue       minimum value where arithmetic sequences will start.
     * @param step           step between values in computed arithmetic sequences.
     * @param count          number of arithmetic sequences to be computed (containing elements from 0 up to count).
     * @param sequenceMethod method used for arithmetic sequence computation. Can be either "exhaustive" or "fast".
     * @return requested summary results of arithmetic sequences.
     */
    @GetMapping("/non-reactive/summary")
    public MultipleArithmeticSequenceSummaryDto computeSummaryNonReactive(
            @RequestParam("minValue") final int minValue,
            @RequestParam("step") final int step,
            @RequestParam("count") final int count,
            @RequestParam("sequenceMethod") final String sequenceMethod) {

        validationService.validate(count);

        final var startInstant = Instant.now();

        // Here also a whole list of results is built, even though all results are added up, stil the whole list needs
        // to be kept in memory. For this reason, memory usage will increase as count increases
        final var totalSum = (Long) nonReactiveService.compute(
                dataMapper.mapFromDto(minValue, step, count, sequenceMethod)).stream()
                .mapToLong(SingleArithmeticSequenceResult::getSum).sum();

        final var endInstant = Instant.now();
        final var duration = Duration.between(startInstant, endInstant);

        return summaryFactory.build(totalSum, count, duration);
    }

    /**
     * Reactively computes detailed results of arithmetic sequences.
     *
     * @param minValue       minimum value where arithmetic sequences will start.
     * @param step           step between values in computed arithmetic sequences.
     * @param count          number of arithmetic sequences to be computed (containing elements from 0 up to count).
     * @param sequenceMethod method used for arithmetic sequence computation. Can be either "exhaustive" or "fast".
     * @return requested detailed results of arithmetic sequences.
     */
    @GetMapping("/reactive/detail")
    public Mono<MultipleArithmeticSequenceDetailDto> computeDetailReactive(
            @RequestParam("minValue") final int minValue,
            @RequestParam("step") final int step,
            @RequestParam("count") final int count,
            @RequestParam("sequenceMethod") final String sequenceMethod) {

        validationService.validate(count);

        final var startInstant = Instant.now();

        // Here results are also collected into a list, and consequently memory usage will increase as count increases.
        return reactiveService.compute(
                dataMapper.mapFromDto(minValue, step, count, sequenceMethod))
                .map(resultMapper::mapToDto).collectList().map(results -> {
                    final var endInstant = Instant.now();
                    final var duration = Duration.between(startInstant, endInstant);

                    return detailFactory.build(results, duration);
                });
    }

    /**
     * Reactively computes summary results of arithmetic sequences.
     * Summary only contains total arithmetic sequences computed and their total sum, but
     * does not contain their detailed results.
     * Because this implementation is fully reactive, memory usage will remain constantly
     * low regardless of the number of requested arithmetic sequences to be computed.
     *
     * @param minValue       minimum value where arithmetic sequences will start.
     * @param step           step between values in computed arithmetic sequences.
     * @param count          number of arithmetic sequences to be computed (containing elements from 0 up to count).
     * @param sequenceMethod method used for arithmetic sequence computation. Can be either "exhaustive" or "fast".
     * @return requested summary results of arithmetic sequences.
     */
    @GetMapping("/reactive/summary")
    public Mono<MultipleArithmeticSequenceSummaryDto> computeSummaryReactive(
            @RequestParam("minValue") final int minValue,
            @RequestParam("step") final int step,
            @RequestParam("count") final int count,
            @RequestParam("sequenceMethod") final String sequenceMethod) {

        validationService.validate(count);

        final var startInstant = Instant.now();

        // This is a fully reactive implementation. No lists are built in memory and memory usage will remain constant
        // regardless of count value
        return reactiveService.compute(
                dataMapper.mapFromDto(minValue, step, count, sequenceMethod))
                .map(item -> (long) item.getSum())
                .reduce(Long::sum).map(totalSum -> {

                    final var endInstant = Instant.now();
                    final var duration = Duration.between(startInstant, endInstant);

                    return summaryFactory.build(totalSum, count, duration);
                });
    }
}
