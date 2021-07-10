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
package com.irurueta.reactorworkshop;

import org.junit.jupiter.api.Test;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import static org.junit.jupiter.api.Assertions.*;

public class ReactorWorkshopConfigurationTest {

    @Test
    public void class_hasExpectedConfigurationAnnotation() {
        assertNotNull(TestUtils.getClassAnnotation(ReactorWorkshopConfiguration.class, Configuration.class));
    }

    @Test
    public void class_hasExpectedConfigurationPropertiesAnnotation() {
        final var annotation = TestUtils.getClassAnnotation(ReactorWorkshopConfiguration.class,
                ConfigurationProperties.class);

        assertNotNull(annotation);
        assertEquals("reactorworkshop", annotation.prefix());
    }

    @Test
    public void getMaxPolynomialDegree_returnsExpectedValue() {
        final var config = new ReactorWorkshopConfiguration();

        // check default value
        assertEquals(0, config.getMaxPolynomialDegree());

        // set new value
        config.setMaxPolynomialDegree(5);

        // check
        assertEquals(5, config.getMaxPolynomialDegree());
    }

    @Test
    public void setMaxPolynomialDegree_whenTooSmall_throwsIllegalArgumentException() {
        final var config = new ReactorWorkshopConfiguration();

        assertThrows(IllegalArgumentException.class, () -> config.setMaxPolynomialDegree(0));
    }

    @Test
    public void getMaxPolynomialTreeDepth_returnsExpectedValue() {
        final var config = new ReactorWorkshopConfiguration();

        // check default value
        assertEquals(0, config.getMaxPolynomialTreeDepth());

        // set new value
        config.setMaxPolynomialTreeDepth(20);

        // check
        assertEquals(20, config.getMaxPolynomialTreeDepth());
    }

    @Test
    public void setMaxPolynomialTreeDepth_whenTooSmall_throwsIllegalArgumentException() {
        final var config = new ReactorWorkshopConfiguration();

        assertThrows(IllegalArgumentException.class, () -> config.setMaxPolynomialTreeDepth(0));
    }

    @Test
    public void getMaxPolynomialCount_returnsExpectedValue() {
        final var config = new ReactorWorkshopConfiguration();

        // check default value
        assertEquals(0, config.getMaxPolynomialCount());

        // set new value
        config.setMaxPolynomialCount(30);

        // check
        assertEquals(30, config.getMaxPolynomialCount());
    }

    @Test
    public void setMaxPolynomialCount_whenTooSmall_throwsIllegalArgumentException() {
        final var config = new ReactorWorkshopConfiguration();

        assertThrows(IllegalArgumentException.class, () -> config.setMaxPolynomialCount(0));
    }

    @Test
    public void getMaxArithmeticSequenceCount_returnsExpectedValue() {
        final var config = new ReactorWorkshopConfiguration();

        // check default value
        assertEquals(0, config.getMaxArithmeticSequenceCount());

        // set new value
        config.setMaxArithmeticSequenceCount(50);

        // check
        assertEquals(50, config.getMaxArithmeticSequenceCount());
    }

    @Test
    public void setMaxArithmeticSequenceCount_whenTooSmall_throwsIllegalArgumentException() {
        final var config = new ReactorWorkshopConfiguration();

        assertThrows(IllegalArgumentException.class, () -> config.setMaxArithmeticSequenceCount(0));
    }
}
