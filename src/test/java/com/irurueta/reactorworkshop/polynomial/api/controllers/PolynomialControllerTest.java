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

import com.irurueta.reactorworkshop.TestUtils;
import com.irurueta.reactorworkshop.polynomial.api.dto.EvaluationStepsDto;
import com.irurueta.reactorworkshop.polynomial.api.dto.MultiplePolynomialEvaluationResultDto;
import com.irurueta.reactorworkshop.polynomial.api.dto.PolynomialEvaluationResultDto;
import com.irurueta.reactorworkshop.polynomial.api.dto.factories.MultiplePolynomialEvaluationResultDtoFactory;
import com.irurueta.reactorworkshop.polynomial.api.mappers.EvaluationStepMapper;
import com.irurueta.reactorworkshop.polynomial.api.mappers.PolynomialEvaluationResultMapper;
import com.irurueta.reactorworkshop.polynomial.domain.entities.EvaluationStep;
import com.irurueta.reactorworkshop.polynomial.domain.entities.PolynomialEvaluationResult;
import com.irurueta.reactorworkshop.polynomial.domain.services.MultiplePolynomialEvaluationService;
import com.irurueta.reactorworkshop.polynomial.domain.services.PolynomialParametersValidationService;
import com.irurueta.reactorworkshop.polynomial.domain.services.ReactiveMultiplePolynomialEvaluationService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.lang.annotation.Annotation;
import java.time.Duration;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class PolynomialControllerTest {

    private static final long DELAY_MILLIS = 500L;

    @Mock
    private MultiplePolynomialEvaluationService evaluationService;

    @Mock
    private ReactiveMultiplePolynomialEvaluationService reactiveEvaluationService;

    @Mock
    private PolynomialParametersValidationService validationService;

    @Mock
    private EvaluationStepMapper stepMapper;

    @Mock
    private PolynomialEvaluationResultMapper resultMapper;

    @Mock
    private MultiplePolynomialEvaluationResultDtoFactory factory;

    @InjectMocks
    private PolynomialController controller;

    private AutoCloseable autoCloseable;

    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }

    @Test
    void class_hasRestControllerAnnotation() {
        assertNotNull(TestUtils.getClassAnnotation(PolynomialController.class, RestController.class));
    }

    @Test
    void class_hasRequestMappingAnnotation() {
        final RequestMapping annotation = TestUtils.getClassAnnotation(PolynomialController.class, RequestMapping.class);

        assertNotNull(annotation);
        assertEquals(1, annotation.value().length);
        assertEquals("/polynomial", annotation.value()[0]);
    }

    @Test
    void constructor_injectsExpectedInstances() {
        assertSame(evaluationService, ReflectionTestUtils.getField(controller, "evaluationService"));
        assertSame(reactiveEvaluationService,
                ReflectionTestUtils.getField(controller, "reactiveEvaluationService"));
        assertSame(validationService, ReflectionTestUtils.getField(controller, "validationService"));
        assertSame(stepMapper, ReflectionTestUtils.getField(controller, "stepMapper"));
        assertSame(resultMapper, ReflectionTestUtils.getField(controller, "resultMapper"));
        assertSame(factory, ReflectionTestUtils.getField(controller, "factory"));
    }

    @Test
    void evaluateNonReactive_hasExpectedAnnotations() throws NoSuchMethodException {
        final var methodName = "evaluateNonReactive";
        final var parameterTypes = new Class[]{EvaluationStepsDto.class, Long.class};
        final PostMapping methodAnnotation = TestUtils.getMethodAnnotation(PostMapping.class, controller,
                methodName, parameterTypes);

        assertNotNull(methodAnnotation);
        assertEquals(1, methodAnnotation.value().length);
        assertEquals("/non-reactive", methodAnnotation.value()[0]);

        final Annotation[][] paramAnnotations = TestUtils.getMethodParameterAnnotations(
                controller, methodName, parameterTypes);

        assertEquals(2, paramAnnotations.length);

        assertEquals(1, paramAnnotations[0].length);
        final var annotation1 = (RequestBody) paramAnnotations[0][0];
        assertNotNull(annotation1);

        assertEquals(1, paramAnnotations[1].length);
        final var annotation2 = (RequestParam) paramAnnotations[1][0];
        assertEquals("delay", annotation2.value());
        assertFalse(annotation2.required());
    }

    @Test
    void evaluateNonReactive_returnsExpectedResult() {
        final var stepsDto = new EvaluationStepsDto();

        final var step = new EvaluationStep();
        final var steps = Collections.singletonList(step);
        when(stepMapper.mapFromDto(stepsDto)).thenReturn(steps);

        final var evaluationResult = PolynomialEvaluationResult.builder().build();
        final var evaluationResults = Collections.singletonList(evaluationResult);
        when(evaluationService.evaluate(steps, DELAY_MILLIS)).thenReturn(evaluationResults);

        final var evaluationResultDto = new PolynomialEvaluationResultDto();
        when(resultMapper.mapToDto(evaluationResult)).thenReturn(evaluationResultDto);

        final var expected = MultiplePolynomialEvaluationResultDto.builder().build();

        when(factory.build(anyList(), any(Duration.class))).thenReturn(expected);

        final var result = controller.evaluateNonReactive(stepsDto, DELAY_MILLIS);

        // check
        assertSame(expected, result);
        verify(stepMapper, only()).mapFromDto(stepsDto);
        verify(validationService, only()).validate(steps);
        verify(evaluationService, only()).evaluate(steps, DELAY_MILLIS);
        verify(resultMapper, only()).mapToDto(evaluationResult);

        final var evaluationResultsCaptor = ArgumentCaptor.forClass(List.class);
        final var durationCaptor = ArgumentCaptor.forClass(Duration.class);
        //noinspection unchecked
        verify(factory, only()).build(evaluationResultsCaptor.capture(), durationCaptor.capture());

        final var evaluationResultsDto = evaluationResultsCaptor.getValue();
        assertEquals(Collections.singletonList(evaluationResultDto), evaluationResultsDto);
        assertEquals(1, evaluationResultsDto.size());
        assertSame(evaluationResultDto, evaluationResultsDto.get(0));

        final var duration = durationCaptor.getValue();
        assertFalse(duration.isNegative());
        assertFalse(duration.isZero());
    }

    @Test
    void evaluateReactive_hasExpectedAnnotations() throws NoSuchMethodException {
        final var methodName = "evaluateReactive";
        final var parameterTypes = new Class[]{EvaluationStepsDto.class, Long.class};
        final PostMapping methodAnnotation = TestUtils.getMethodAnnotation(PostMapping.class, controller,
                methodName, parameterTypes);

        assertNotNull(methodAnnotation);
        assertEquals(1, methodAnnotation.value().length);
        assertEquals("/reactive", methodAnnotation.value()[0]);

        final Annotation[][] paramAnnotations = TestUtils.getMethodParameterAnnotations(
                controller, methodName, parameterTypes);

        assertEquals(2, paramAnnotations.length);

        assertEquals(1, paramAnnotations[0].length);
        final var annotation1 = (RequestBody) paramAnnotations[0][0];
        assertNotNull(annotation1);

        assertEquals(1, paramAnnotations[1].length);
        final var annotation2 = (RequestParam) paramAnnotations[1][0];
        assertEquals("delay", annotation2.value());
        assertFalse(annotation2.required());
    }

    @Test
    void evaluateReactive_returnsExpectedResult() {
        final var stepsDto = new EvaluationStepsDto();

        final var step = new EvaluationStep();
        final var steps = Collections.singletonList(step);
        when(stepMapper.mapFromDto(stepsDto)).thenReturn(steps);

        final var evaluationResult = PolynomialEvaluationResult.builder().build();
        final var evaluationResultsFlux = Flux.just(evaluationResult);
        //noinspection unchecked
        when(reactiveEvaluationService.evaluate(any(Flux.class), eq(DELAY_MILLIS))).thenReturn(evaluationResultsFlux);

        final var evaluationResultDto = new PolynomialEvaluationResultDto();
        when(resultMapper.mapToDto(evaluationResult)).thenReturn(evaluationResultDto);

        final var expected = MultiplePolynomialEvaluationResultDto.builder().build();

        when(factory.build(anyList(), any(Duration.class))).thenReturn(expected);

        final var flux = controller.evaluateReactive(stepsDto, DELAY_MILLIS);

        // check
        StepVerifier.create(flux).expectNext(expected).verifyComplete();

        verify(stepMapper, only()).mapFromDto(stepsDto);
        verify(validationService, times(1)).validate(steps);
        //noinspection unchecked
        verify(reactiveEvaluationService, only()).evaluate(any(Flux.class), eq(DELAY_MILLIS));
        verify(resultMapper, only()).mapToDto(evaluationResult);

        final var evaluationResultsCaptor = ArgumentCaptor.forClass(List.class);
        final var durationCaptor = ArgumentCaptor.forClass(Duration.class);
        //noinspection unchecked
        verify(factory, only()).build(evaluationResultsCaptor.capture(), durationCaptor.capture());

        final var evaluationResultsDto = evaluationResultsCaptor.getValue();
        assertEquals(Collections.singletonList(evaluationResultDto), evaluationResultsDto);
        assertEquals(1, evaluationResultsDto.size());
        assertSame(evaluationResultDto, evaluationResultsDto.get(0));

        final var duration = durationCaptor.getValue();
        assertFalse(duration.isNegative());
        assertFalse(duration.isZero());
    }
}