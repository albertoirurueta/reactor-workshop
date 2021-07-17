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

import com.irurueta.reactorworkshop.TestUtils;
import com.irurueta.reactorworkshop.arithmeticsequence.api.dto.MultipleArithmeticSequenceDetailDto;
import com.irurueta.reactorworkshop.arithmeticsequence.api.dto.MultipleArithmeticSequenceSummaryDto;
import com.irurueta.reactorworkshop.arithmeticsequence.api.dto.SingleArithmeticSequenceResultDto;
import com.irurueta.reactorworkshop.arithmeticsequence.api.dto.factories.MultipleArithmeticSequenceDetailDtoFactory;
import com.irurueta.reactorworkshop.arithmeticsequence.api.dto.factories.MultipleArithmeticSequenceSummaryDtoFactory;
import com.irurueta.reactorworkshop.arithmeticsequence.api.mappers.MultipleArithmeticSequenceDataMapperImpl;
import com.irurueta.reactorworkshop.arithmeticsequence.api.mappers.SingleArithmeticSequenceResultMapperImpl;
import com.irurueta.reactorworkshop.arithmeticsequence.domain.entities.ArithmeticSequenceMethod;
import com.irurueta.reactorworkshop.arithmeticsequence.domain.entities.MultipleArithmeticSequenceData;
import com.irurueta.reactorworkshop.arithmeticsequence.domain.entities.SingleArithmeticSequenceResult;
import com.irurueta.reactorworkshop.arithmeticsequence.domain.services.ArithmeticSequenceValidationService;
import com.irurueta.reactorworkshop.arithmeticsequence.domain.services.FastArithmeticSequenceService;
import com.irurueta.reactorworkshop.arithmeticsequence.domain.services.MultipleArithmeticSequenceService;
import com.irurueta.reactorworkshop.arithmeticsequence.domain.services.ReactiveMultipleArithmeticSequenceService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.lang.annotation.Annotation;
import java.time.Duration;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ArithmeticSequenceControllerTest {

    private static final int MAX = 100;

    @Mock
    private MultipleArithmeticSequenceService nonReactiveService;

    @Mock
    private ReactiveMultipleArithmeticSequenceService reactiveService;

    @Mock
    private ArithmeticSequenceValidationService validationService;

    @Spy
    private MultipleArithmeticSequenceDataMapperImpl dataMapper;

    @Spy
    private SingleArithmeticSequenceResultMapperImpl resultMapper;

    @Mock
    private MultipleArithmeticSequenceDetailDtoFactory detailFactory;

    @Mock
    private MultipleArithmeticSequenceSummaryDtoFactory summaryFactory;

    @InjectMocks
    private ArithmeticSequenceController controller;

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
        assertNotNull(TestUtils.getClassAnnotation(ArithmeticSequenceController.class, RestController.class));
    }

    @Test
    void class_hasRequestMappingAnnotation() {
        final RequestMapping annotation = TestUtils.getClassAnnotation(ArithmeticSequenceController.class, RequestMapping.class);

        assertNotNull(annotation);
        assertEquals(1, annotation.value().length);
        assertEquals("/arithmetic-sequence", annotation.value()[0]);
    }

    @Test
    void constructor_injectsExpectedInstances() {
        assertSame(nonReactiveService, ReflectionTestUtils.getField(controller, "nonReactiveService"));
        assertSame(reactiveService, ReflectionTestUtils.getField(controller, "reactiveService"));
        assertSame(validationService, ReflectionTestUtils.getField(controller, "validationService"));
        assertSame(dataMapper, ReflectionTestUtils.getField(controller, "dataMapper"));
        assertSame(resultMapper, ReflectionTestUtils.getField(controller, "resultMapper"));
        assertSame(detailFactory, ReflectionTestUtils.getField(controller, "detailFactory"));
        assertSame(summaryFactory, ReflectionTestUtils.getField(controller, "summaryFactory"));
    }

    @ParameterizedTest
    @CsvSource({
            "computeDetailNonReactive, /non-reactive/detail",
            "computeSummaryNonReactive, /non-reactive/summary",
            "computeDetailReactive, /reactive/detail",
            "computeSummaryReactive, /reactive/summary"
    })
    void checkAnnotations(final String methodName, final String path) throws NoSuchMethodException {
        final var parameterTypes = new Class[]{Integer.TYPE, Integer.TYPE, Integer.TYPE, String.class};
        final GetMapping methodAnnotation = TestUtils.getMethodAnnotation(GetMapping.class, controller,
                methodName, parameterTypes);

        assertNotNull(methodAnnotation);
        assertEquals(1, methodAnnotation.value().length);
        assertEquals(path, methodAnnotation.value()[0]);

        final Annotation[][] paramAnnotations = TestUtils.getMethodParameterAnnotations(
                controller, methodName, parameterTypes);

        assertEquals(4, paramAnnotations.length);

        assertEquals(1, paramAnnotations[0].length);
        final var annotation1 = (RequestParam) paramAnnotations[0][0];
        assertEquals("minValue", annotation1.value());

        assertEquals(1, paramAnnotations[1].length);
        final var annotation2 = (RequestParam) paramAnnotations[1][0];
        assertEquals("step", annotation2.value());

        assertEquals(1, paramAnnotations[2].length);
        final var annotation3 = (RequestParam) paramAnnotations[2][0];
        assertEquals("count", annotation3.value());

        assertEquals(1, paramAnnotations[3].length);
        final var annotation4 = (RequestParam) paramAnnotations[3][0];
        assertEquals("sequenceMethod", annotation4.value());
    }

    @Test
    void computeDetailNonReactive_returnsExpectedResult() {
        final var random = new Random();
        final var minValue = random.nextInt(MAX);
        final var step = random.nextInt(MAX);
        final var count = 1 + random.nextInt(MAX);
        final var sequenceMethod = ArithmeticSequenceMethod.FAST.getValue();

        final var dataMapperResult = MultipleArithmeticSequenceData.builder().build();
        when(dataMapper.mapFromDto(eq(minValue), eq(step), eq(count), eq(sequenceMethod))).thenReturn(dataMapperResult);

        final var singleServiceResult = SingleArithmeticSequenceResult.builder().build();
        final List<SingleArithmeticSequenceResult> nonReactiveServiceResult =
                Collections.singletonList(singleServiceResult);
        when(nonReactiveService.compute(dataMapperResult))
                .thenReturn(nonReactiveServiceResult);

        final var singleResultDto = SingleArithmeticSequenceResultDto.builder().build();
        when(resultMapper.mapToDto(singleServiceResult)).thenReturn(singleResultDto);

        final var expected = MultipleArithmeticSequenceDetailDto.builder().build();
        when(detailFactory.build(anyList(), any(Duration.class))).thenReturn(expected);

        final var result = controller.computeDetailNonReactive(
                minValue, step, count, sequenceMethod);

        assertSame(expected, result);

        verify(validationService, only()).validate(count);
        verify(nonReactiveService, only()).compute(dataMapperResult);
        verify(dataMapper, only()).mapFromDto(minValue, step, count, sequenceMethod);
        verify(resultMapper, only()).mapToDto(singleServiceResult);

        final var durationCaptor = ArgumentCaptor.forClass(Duration.class);
        verify(detailFactory, only()).build(eq(Collections.singletonList(singleResultDto)), durationCaptor.capture());

        final var duration = durationCaptor.getValue();
        assertFalse(duration.isZero());
        assertFalse(duration.isNegative());
    }

    @Test
    void computeDetailNonReactive_whenValidationFails_throwsIllegalArgumentException() {
        mockValidationServiceError();

        final var random = new Random();
        final var minValue = random.nextInt(MAX);
        final var step = random.nextInt(MAX);
        final var count = 1 + random.nextInt(MAX);
        final var sequenceMethod = ArithmeticSequenceMethod.FAST.getValue();

        final var dataMapperResult = MultipleArithmeticSequenceData.builder().build();
        when(dataMapper.mapFromDto(minValue, step, count, sequenceMethod)).thenReturn(dataMapperResult);

        final var singleServiceResult = SingleArithmeticSequenceResult.builder().build();
        final List<SingleArithmeticSequenceResult> nonReactiveServiceResult =
                Collections.singletonList(singleServiceResult);
        when(nonReactiveService.compute(dataMapperResult))
                .thenReturn(nonReactiveServiceResult);

        final var singleResultDto = SingleArithmeticSequenceResultDto.builder().build();
        when(resultMapper.mapToDto(singleServiceResult)).thenReturn(singleResultDto);

        final var expected = MultipleArithmeticSequenceDetailDto.builder().build();
        when(detailFactory.build(anyList(), any(Duration.class))).thenReturn(expected);

        assertThrows(IllegalArgumentException.class, () -> controller.computeDetailNonReactive(
                minValue, step, count, sequenceMethod));
    }

    @Test
    void computeSummaryNonReactive_returnsExpectedResult() {
        final var random = new Random();
        final var minValue = random.nextInt(MAX);
        final var step = random.nextInt(MAX);
        final var count = 1 + random.nextInt(MAX);
        final var sequenceMethod = ArithmeticSequenceMethod.FAST.getValue();
        final var totalSum = new FastArithmeticSequenceService().compute(minValue, step, count);

        final var dataMapperResult = MultipleArithmeticSequenceData.builder().build();
        when(dataMapper.mapFromDto(eq(minValue), eq(step), eq(count), eq(sequenceMethod))).thenReturn(dataMapperResult);

        final var singleServiceResult = SingleArithmeticSequenceResult.builder()
                .sum(totalSum).build();
        final List<SingleArithmeticSequenceResult> nonReactiveServiceResult =
                Collections.singletonList(singleServiceResult);
        when(nonReactiveService.compute(dataMapperResult))
                .thenReturn(nonReactiveServiceResult);

        final var expected = MultipleArithmeticSequenceSummaryDto.builder().build();
        when(summaryFactory.build(eq((long) totalSum), eq(count), any(Duration.class))).thenReturn(expected);

        final var result = controller.computeSummaryNonReactive(
                minValue, step, count, sequenceMethod);

        assertSame(expected, result);

        verify(validationService, only()).validate(count);
        verify(nonReactiveService, only()).compute(dataMapperResult);
        verify(dataMapper, only()).mapFromDto(minValue, step, count, sequenceMethod);

        final var durationCaptor = ArgumentCaptor.forClass(Duration.class);
        verify(summaryFactory, only()).build(eq((long) totalSum), eq(count),
                durationCaptor.capture());

        final var duration = durationCaptor.getValue();
        assertFalse(duration.isZero());
        assertFalse(duration.isNegative());
    }

    @Test
    void computeSummaryNonReactive_whenValidationFails_throwsIllegalArgumentException() {
        mockValidationServiceError();

        final var random = new Random();
        final var minValue = random.nextInt(MAX);
        final var step = random.nextInt(MAX);
        final var count = 1 + random.nextInt(MAX);
        final var sequenceMethod = ArithmeticSequenceMethod.FAST.getValue();
        final var totalSum = new FastArithmeticSequenceService().compute(minValue, step, count);

        final var dataMapperResult = MultipleArithmeticSequenceData.builder().build();
        when(dataMapper.mapFromDto(minValue, step, count, sequenceMethod)).thenReturn(dataMapperResult);

        final var singleServiceResult = SingleArithmeticSequenceResult.builder()
                .sum(totalSum).build();
        final List<SingleArithmeticSequenceResult> nonReactiveServiceResult =
                Collections.singletonList(singleServiceResult);
        when(nonReactiveService.compute(dataMapperResult))
                .thenReturn(nonReactiveServiceResult);

        final var expected = MultipleArithmeticSequenceSummaryDto.builder().build();
        when(summaryFactory.build(eq((long) totalSum), eq(count), any(Duration.class))).thenReturn(expected);

        assertThrows(IllegalArgumentException.class, () -> controller.computeSummaryNonReactive(
                minValue, step, count, sequenceMethod));
    }

    @Disabled
    @Test
    void computeDetailReactive_returnsExpectedResult() {
        final var random = new Random();
        final var minValue = random.nextInt(MAX);
        final var step = random.nextInt(MAX);
        final var count = 1 + random.nextInt(MAX);
        final var sequenceMethod = ArithmeticSequenceMethod.FAST.getValue();

        final var dataMapperResult = MultipleArithmeticSequenceData.builder().build();
        when(dataMapper.mapFromDto(eq(minValue), eq(step), eq(count), eq(sequenceMethod))).thenReturn(dataMapperResult);

        final var singleServiceResult = SingleArithmeticSequenceResult.builder().build();
        final var reactiveServiceResult = Flux.just(singleServiceResult);
        when(reactiveService.compute(dataMapperResult)).thenReturn(reactiveServiceResult);

        final var singleResultDto = SingleArithmeticSequenceResultDto.builder().build();
        when(resultMapper.mapToDto(singleServiceResult)).thenReturn(singleResultDto);

        final var expected = MultipleArithmeticSequenceDetailDto.builder().build();
        when(detailFactory.build(anyList(), any(Duration.class))).thenReturn(expected);

        final var flux = controller.computeDetailReactive(
                minValue, step, count, sequenceMethod);

        StepVerifier.create(flux).expectNext(expected).verifyComplete();

        verify(validationService, only()).validate(count);
        verify(reactiveService, only()).compute(dataMapperResult);
        verify(dataMapper, only()).mapFromDto(minValue, step, count, sequenceMethod);
        verify(resultMapper, only()).mapToDto(singleServiceResult);

        final var durationCaptor = ArgumentCaptor.forClass(Duration.class);
        verify(detailFactory, only()).build(eq(Collections.singletonList(singleResultDto)), durationCaptor.capture());

        final var duration = durationCaptor.getValue();
        assertFalse(duration.isZero());
        assertFalse(duration.isNegative());
    }

    @Disabled
    @Test
    void computeDetailReactive_whenValidationFails_returnsExpectedResult() {
        mockValidationServiceError();

        final var random = new Random();
        final var minValue = random.nextInt(MAX);
        final var step = random.nextInt(MAX);
        final var count = 1 + random.nextInt(MAX);
        final var sequenceMethod = ArithmeticSequenceMethod.FAST.getValue();

        final var dataMapperResult = MultipleArithmeticSequenceData.builder().build();
        when(dataMapper.mapFromDto(minValue, step, count, sequenceMethod)).thenReturn(dataMapperResult);

        final var singleServiceResult = SingleArithmeticSequenceResult.builder().build();
        final var reactiveServiceResult = Flux.just(singleServiceResult);
        when(reactiveService.compute(dataMapperResult)).thenReturn(reactiveServiceResult);

        final var singleResultDto = SingleArithmeticSequenceResultDto.builder().build();
        when(resultMapper.mapToDto(singleServiceResult)).thenReturn(singleResultDto);

        final var expected = MultipleArithmeticSequenceDetailDto.builder().build();
        when(detailFactory.build(anyList(), any(Duration.class))).thenReturn(expected);

        assertThrows(IllegalArgumentException.class, () -> controller.computeDetailReactive(
                minValue, step, count, sequenceMethod));
    }

    @Disabled
    @Test
    void computeSummaryReactive_returnsExpectedResult() {
        final var random = new Random();
        final var minValue = random.nextInt(MAX);
        final var step = random.nextInt(MAX);
        final var count = 1 + random.nextInt(MAX);
        final var sequenceMethod = ArithmeticSequenceMethod.FAST.getValue();
        final var totalSum = new FastArithmeticSequenceService().compute(minValue, step, count);

        final var dataMapperResult = MultipleArithmeticSequenceData.builder().build();
        when(dataMapper.mapFromDto(eq(minValue), eq(step), eq(count), eq(sequenceMethod))).thenReturn(dataMapperResult);

        final var singleServiceResult = SingleArithmeticSequenceResult.builder()
                .sum(totalSum).build();
        final var reactiveServiceResult = Flux.just(singleServiceResult);
        when(reactiveService.compute(dataMapperResult)).thenReturn(reactiveServiceResult);

        final var expected = MultipleArithmeticSequenceSummaryDto.builder().build();
        when(summaryFactory.build(eq((long) totalSum), eq(count), any(Duration.class))).thenReturn(expected);

        final var flux = controller.computeSummaryReactive(
                minValue, step, count, sequenceMethod);

        StepVerifier.create(flux).expectNext(expected).verifyComplete();

        verify(validationService, only()).validate(count);
        verify(reactiveService, only()).compute(dataMapperResult);
        verify(dataMapper, only()).mapFromDto(minValue, step, count, sequenceMethod);

        final var durationCaptor = ArgumentCaptor.forClass(Duration.class);
        verify(summaryFactory, only()).build(eq((long) totalSum), eq(count),
                durationCaptor.capture());

        final var duration = durationCaptor.getValue();
        assertFalse(duration.isZero());
        assertFalse(duration.isNegative());
    }

    @Disabled
    @Test
    void computeSummaryReactive_whenValidationFails_returnsExpectedResult() {
        mockValidationServiceError();

        final var random = new Random();
        final var minValue = random.nextInt(MAX);
        final var step = random.nextInt(MAX);
        final var count = 1 + random.nextInt(MAX);
        final var sequenceMethod = ArithmeticSequenceMethod.FAST.getValue();
        final var totalSum = new FastArithmeticSequenceService().compute(minValue, step, count);

        final var dataMapperResult = MultipleArithmeticSequenceData.builder().build();
        when(dataMapper.mapFromDto(minValue, step, count, sequenceMethod)).thenReturn(dataMapperResult);

        final var singleServiceResult = SingleArithmeticSequenceResult.builder()
                .sum(totalSum).build();
        final var reactiveServiceResult = Flux.just(singleServiceResult);
        when(reactiveService.compute(dataMapperResult)).thenReturn(reactiveServiceResult);

        final var expected = MultipleArithmeticSequenceSummaryDto.builder().build();
        when(summaryFactory.build(eq((long) totalSum), eq(count), any(Duration.class))).thenReturn(expected);

        assertThrows(IllegalArgumentException.class, () -> controller.computeSummaryReactive(
                minValue, step, count, sequenceMethod));
    }

    private void mockValidationServiceError() {
        doThrow(IllegalArgumentException.class).when(validationService).validate(anyInt());
    }
}