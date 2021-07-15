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
package com.irurueta.reactorworkshop.polynomial.api.mappers;

import com.irurueta.numerical.polynomials.Polynomial;
import org.mapstruct.Mapper;

/**
 * Maps a polynomial to DTO.
 */
@Mapper(componentModel = "spring")
public interface PolynomialMapper {

    /**
     * Maps polynomial to DTO.
     *
     * @param polynomial polynomial to be mapped.
     * @return mapped value.
     */
    default double[] mapToDto(final Polynomial polynomial) {
        if (polynomial == null) {
            return new double[0];
        }

        return polynomial.getPolyParams();
    }

    /**
     * Maps polynomial parameters from a DTO into a domain {@link Polynomial}.
     *
     * @param polynomialParameters polynomial parameters to be mapped.
     * @return mapped value.
     */
    default Polynomial mapFromDto(final double[] polynomialParameters) {
        return polynomialParameters != null ? new Polynomial(polynomialParameters) : null;
    }
}
