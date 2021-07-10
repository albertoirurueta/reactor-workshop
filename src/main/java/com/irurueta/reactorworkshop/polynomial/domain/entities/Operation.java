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
package com.irurueta.reactorworkshop.polynomial.domain.entities;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Operation to be executed for an {@link EvaluationStep}.
 */
public enum Operation {
    /**
     * Sums two polynomials provided as operands (operand1 + operand2).
     */
    SUMMATION("summation"),

    /**
     * Subtracts two polynomials provided as operands (operand1 - operand2).
     */
    SUBTRACTION("subtraction"),

    /**
     * Multiples two polynomials provided as operands (operand1 * operand2).
     */
    MULTIPLICATION("multiplication"),

    /**
     * Evaluates a step as a literal polynomial.
     */
    LITERAL("literal");

    /**
     * Lookup map to speed up conversion from a string value into an operation enum instance.
     */
    private static final Map<String, Operation> LOOKUP = Arrays.stream(Operation.values())
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
    Operation(final String value) {
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
    public static Operation fromValue(final String value) {
        return value != null ? LOOKUP.getOrDefault(value.toLowerCase(), null) : null;
    }
}
