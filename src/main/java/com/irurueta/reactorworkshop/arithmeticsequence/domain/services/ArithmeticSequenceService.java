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

/**
 * Defines interface of a service in charge of computed.
 */
public interface ArithmeticSequenceService {

    /**
     * Computes the sum of all elements in an arithmetic sequence containing provided parameters.
     *
     * @param minValue minimum value of first element in the sequence.
     * @param step     step between elements in the sequence.
     * @param count    number of elements in the sequence.
     * @return sum of all elements in the sequence.
     */
    int compute(int minValue, int step, int count);
}
