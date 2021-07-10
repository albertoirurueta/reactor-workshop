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
package com.irurueta.reactorworkshop.arithmeticsequence.domain.entities;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Indicates the way arithmetic sequences are computed.
 */
public enum ArithmeticSequenceMethod {
    /**
     * Arithmetic sequence is computed exhaustively by adding up all their elements. This can be used to evaluate CPU
     * performance of implemented solution.
     */
    EXHAUSTIVE("exhaustive"),

    /**
     * Arithmetic sequence is computed using Gauss formula.
     */
    FAST("fast");

    /**
     * Lookup map to speed up conversion from a string value into an enum instance.
     */
    private static final Map<String, ArithmeticSequenceMethod> LOOKUP = Arrays.stream(ArithmeticSequenceMethod.values())
            .collect(Collectors.toMap(key -> key.value, value -> value));

    /**
     * Internal string representation of this enum type.
     */
    private final String value;

    /**
     * Constructor.
     *
     * @param value internal string representation.
     */
    ArithmeticSequenceMethod(final String value) {
        this.value = value;
    }

    /**
     * Gets internal string representation of this enum type.
     *
     * @return internal string representation.
     */
    public String getValue() {
        return value;
    }

    /**
     * Creates an instance of this enum type from a string representation.
     * If no match is found, null is returned.
     * This method is case insensitive.
     *
     * @param value string representation to convert from.
     * @return converted enum type or null if no match is found.
     */
    public static ArithmeticSequenceMethod fromValue(final String value) {
        return value != null ? LOOKUP.getOrDefault(value.toLowerCase(), null) : null;
    }
}
