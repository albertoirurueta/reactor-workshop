package com.irurueta.reactorworkshop.polynomial.api.mappers;

import com.irurueta.reactorworkshop.TestUtils;
import com.irurueta.reactorworkshop.polynomial.api.dto.EvaluationStepDto;
import com.irurueta.reactorworkshop.polynomial.api.dto.EvaluationStepsDto;
import com.irurueta.reactorworkshop.polynomial.domain.entities.Operation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.stereotype.Component;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EvaluationStepMapperTest {

    private static final double[] POLY_PARAMS_0 = new double[]{1.0, 1.0};

    private static final double[] POLY_PARAMS_1 = new double[]{2.0, 2.0};

    private static final double[] POLY_PARAMS_2 = new double[]{3.0, 3.0};

    private PolynomialMapper polynomialMapper;

    private EvaluationStepMapper mapper;

    @BeforeEach
    void setUp() {
        polynomialMapper = spy(new PolynomialMapperImpl());
        mapper = new EvaluationStepMapperImpl();
        ReflectionTestUtils.setField(mapper, "polynomialMapper", polynomialMapper);
    }

    @Test
    void generatedClass_hasComponentAnnotation() {
        assertNotNull(TestUtils.getClassAnnotation(EvaluationStepMapperImpl.class, Component.class));
    }

    @Test
    void generatedClass_implementsEvaluationStepMapper() {
        assertTrue(EvaluationStepMapper.class.isAssignableFrom(EvaluationStepMapperImpl.class));
    }

    @Test
    void mapFromDto_whenNull_returnsNullOrEmpty() {
        assertNull(mapper.mapFromDto((EvaluationStepDto) null));
        assertTrue(mapper.mapFromDto((EvaluationStepsDto) null).isEmpty());
        assertNull(mapper.mapFromDto((String) null));
    }

    @Test
    void mapFromDto_whenLiteralStep_returnsExpectedValue() {
        final var step = EvaluationStepDto.builder()
                .operation(Operation.LITERAL.getValue())
                .literalPolynomialParameters(POLY_PARAMS_0)
                .build();

        final var result = mapper.mapFromDto(step);

        assertEquals(Operation.LITERAL, result.getOperation());
        assertArrayEquals(POLY_PARAMS_0, result.getLiteralPolynomial().getPolyParams(), 0.0);
        assertNull(result.getOperand1());
        assertNull(result.getOperand2());

        verify(polynomialMapper, only()).mapFromDto(eq(POLY_PARAMS_0));
    }

    @Test
    void mapFromDto_whenSummationStep_returnsExpectedValue() {
        final var step = EvaluationStepDto.builder()
                .operation(Operation.SUMMATION.getValue())
                .operand1(EvaluationStepDto.builder()
                        .operation(Operation.LITERAL.getValue())
                        .literalPolynomialParameters(POLY_PARAMS_1)
                        .build())
                .operand2(EvaluationStepDto.builder()
                        .operation(Operation.LITERAL.getValue())
                        .literalPolynomialParameters(POLY_PARAMS_2)
                        .build())
                .build();

        final var result = mapper.mapFromDto(step);

        assertEquals(Operation.SUMMATION, result.getOperation());
        assertNull(result.getLiteralPolynomial());

        assertNotNull(result.getOperand1());
        assertEquals(Operation.LITERAL, result.getOperand1().getOperation());
        assertArrayEquals(POLY_PARAMS_1, result.getOperand1().getLiteralPolynomial().getPolyParams(), 0.0);
        assertNull(result.getOperand1().getOperand1());
        assertNull(result.getOperand1().getOperand2());

        assertNotNull(result.getOperand2());
        assertEquals(Operation.LITERAL, result.getOperand2().getOperation());
        assertArrayEquals(POLY_PARAMS_2, result.getOperand2().getLiteralPolynomial().getPolyParams(), 0.0);
        assertNull(result.getOperand2().getOperand1());
        assertNull(result.getOperand2().getOperand2());

        verify(polynomialMapper, times(1)).mapFromDto(eq(POLY_PARAMS_1));
        verify(polynomialMapper, times(1)).mapFromDto(eq(POLY_PARAMS_2));
        verify(polynomialMapper, times(1)).mapFromDto(eq(null));
        verifyNoMoreInteractions(polynomialMapper);
    }

    @Test
    void mapFromDto_whenSubtractionStep_returnsExpectedValue() {
        final var step = EvaluationStepDto.builder()
                .operation(Operation.SUBTRACTION.getValue())
                .operand1(EvaluationStepDto.builder()
                        .operation(Operation.LITERAL.getValue())
                        .literalPolynomialParameters(POLY_PARAMS_1)
                        .build())
                .operand2(EvaluationStepDto.builder()
                        .operation(Operation.LITERAL.getValue())
                        .literalPolynomialParameters(POLY_PARAMS_2)
                        .build())
                .build();

        final var result = mapper.mapFromDto(step);

        assertEquals(Operation.SUBTRACTION, result.getOperation());
        assertNull(result.getLiteralPolynomial());

        assertNotNull(result.getOperand1());
        assertEquals(Operation.LITERAL, result.getOperand1().getOperation());
        assertArrayEquals(POLY_PARAMS_1, result.getOperand1().getLiteralPolynomial().getPolyParams(), 0.0);
        assertNull(result.getOperand1().getOperand1());
        assertNull(result.getOperand1().getOperand2());

        assertNotNull(result.getOperand2());
        assertEquals(Operation.LITERAL, result.getOperand2().getOperation());
        assertArrayEquals(POLY_PARAMS_2, result.getOperand2().getLiteralPolynomial().getPolyParams(), 0.0);
        assertNull(result.getOperand2().getOperand1());
        assertNull(result.getOperand2().getOperand2());

        verify(polynomialMapper, times(1)).mapFromDto(eq(POLY_PARAMS_1));
        verify(polynomialMapper, times(1)).mapFromDto(eq(POLY_PARAMS_2));
        verify(polynomialMapper, times(1)).mapFromDto(eq(null));
        verifyNoMoreInteractions(polynomialMapper);
    }

    @Test
    void mapFromDto_whenMultiplicationStep_returnsExpectedValue() {
        final var step = EvaluationStepDto.builder()
                .operation(Operation.MULTIPLICATION.getValue())
                .operand1(EvaluationStepDto.builder()
                        .operation(Operation.LITERAL.getValue())
                        .literalPolynomialParameters(POLY_PARAMS_1)
                        .build())
                .operand2(EvaluationStepDto.builder()
                        .operation(Operation.LITERAL.getValue())
                        .literalPolynomialParameters(POLY_PARAMS_2)
                        .build())
                .build();

        final var result = mapper.mapFromDto(step);

        assertEquals(Operation.MULTIPLICATION, result.getOperation());
        assertNull(result.getLiteralPolynomial());

        assertNotNull(result.getOperand1());
        assertEquals(Operation.LITERAL, result.getOperand1().getOperation());
        assertArrayEquals(POLY_PARAMS_1, result.getOperand1().getLiteralPolynomial().getPolyParams(), 0.0);
        assertNull(result.getOperand1().getOperand1());
        assertNull(result.getOperand1().getOperand2());

        assertNotNull(result.getOperand2());
        assertEquals(Operation.LITERAL, result.getOperand2().getOperation());
        assertArrayEquals(POLY_PARAMS_2, result.getOperand2().getLiteralPolynomial().getPolyParams(), 0.0);
        assertNull(result.getOperand2().getOperand1());
        assertNull(result.getOperand2().getOperand2());

        verify(polynomialMapper, times(1)).mapFromDto(eq(POLY_PARAMS_1));
        verify(polynomialMapper, times(1)).mapFromDto(eq(POLY_PARAMS_2));
        verify(polynomialMapper, times(1)).mapFromDto(eq(null));
        verifyNoMoreInteractions(polynomialMapper);
    }

    @Test
    void mapFromDto_whenEvaluationSteps_returnsExpectedValue() {
        final var step = EvaluationStepDto.builder()
                .operation(Operation.LITERAL.getValue())
                .literalPolynomialParameters(POLY_PARAMS_0)
                .build();
        final var steps = new EvaluationStepsDto();
        steps.setSteps(Collections.singletonList(step));

        final var result = mapper.mapFromDto(steps);

        assertEquals(1, result.size());
        assertEquals(Operation.LITERAL, result.get(0).getOperation());
        assertArrayEquals(POLY_PARAMS_0, result.get(0).getLiteralPolynomial().getPolyParams(), 0.0);
        assertNull(result.get(0).getOperand1());
        assertNull(result.get(0).getOperand2());

        verify(polynomialMapper, only()).mapFromDto(eq(POLY_PARAMS_0));

    }

    @Test
    void mapFromDto_whenOperation_returnsExpectedValue() {
        assertEquals(Operation.LITERAL, mapper.mapFromDto(Operation.LITERAL.getValue()));
        assertEquals(Operation.SUMMATION, mapper.mapFromDto(Operation.SUMMATION.getValue()));
        assertEquals(Operation.SUBTRACTION, mapper.mapFromDto(Operation.SUBTRACTION.getValue()));
        assertEquals(Operation.MULTIPLICATION, mapper.mapFromDto(Operation.MULTIPLICATION.getValue()));
    }
}
