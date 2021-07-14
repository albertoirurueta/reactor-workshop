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
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.spi.DocumentationType;

import static org.junit.jupiter.api.Assertions.*;

public class SwaggerConfigurationTest {

    @Test
    public void class_hasExpectedConfigurationAnnotation() {
        assertNotNull(TestUtils.getClassAnnotation(SwaggerConfiguration.class, Configuration.class));
    }

    @Test
    public void api_hasBeanAnnotation() throws NoSuchMethodException {
        final var configuration = new SwaggerConfiguration();
        assertNotNull(TestUtils.getMethodAnnotation(Bean.class, configuration, "api"));
    }

    @Test
    public void api_returnsExpectedValue() {
        final var configuration = new SwaggerConfiguration();
        final var docket = configuration.api();

        assertEquals(DocumentationType.SWAGGER_2, docket.getDocumentationType());
    }
}