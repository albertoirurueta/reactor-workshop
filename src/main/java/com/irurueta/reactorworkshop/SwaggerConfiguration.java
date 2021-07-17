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

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.Collections;

/**
 * Contains configuration to enable swagger.
 */
@Configuration
public class SwaggerConfiguration {

    /**
     * Gets main bean containing Swagger configuration.
     *
     * @return bean containing Swagger configuration.
     */
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.irurueta.reactorworkshop"))
                .paths(PathSelectors.any())
                .build().apiInfo(getApiInfo());
    }

    /**
     * Gets information about the API.
     *
     * @return information about the API.
     */
    private ApiInfo getApiInfo() {
        return new ApiInfo(
                "Reactor workshop API",
                "Contains APIs for exercises proposed in this workshop",
                "1.0",
                null,
                new Contact("Alberto Irurueta Carro",
                        "https://www.github.com/albertoirurueta",
                        "alberto@irurueta.com"),
                "Apache License, Version 2.0",
                "https://www.apache.org/licenses/LICENSE-2.0",
                Collections.emptyList());
    }
}
