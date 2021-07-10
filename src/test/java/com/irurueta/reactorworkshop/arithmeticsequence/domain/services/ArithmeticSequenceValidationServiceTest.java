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
package com.irurueta.reactorworkshop.arithmeticsequence.domain.services;

import com.irurueta.reactorworkshop.ReactorWorkshopConfiguration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ArithmeticSequenceValidationServiceTest {

    private static final int MAX_ARITHMETIC_SEQUENCE_COUNT = 2;

    private ArithmeticSequenceValidationService service;

    @BeforeEach
    public void setUp() {
        final var configuration = mock(ReactorWorkshopConfiguration.class);
        when(configuration.getMaxArithmeticSequenceCount()).thenReturn(MAX_ARITHMETIC_SEQUENCE_COUNT);

        service = new ArithmeticSequenceValidationService(configuration);
    }

    @Test
    public void constructor_setsMaxArithmeticSequenceCount() {
        assertEquals(MAX_ARITHMETIC_SEQUENCE_COUNT, service.getMaxArithmeticSequenceCount());
    }

    @Test
    public void validate_whenValid_returnsSuccessfully() {
        assertDoesNotThrow(() -> service.validate(1));
    }

    @Test
    public void validate_whenNotValid_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> service.validate(3));
    }
}