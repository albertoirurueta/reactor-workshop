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

import java.lang.annotation.Annotation;

/**
 * Utility class for tests.
 */
public class TestUtils {

    /**
     * Gets annotation at class level.
     *
     * @param c               class to obtain an annotation from.
     * @param annotationClass annotation class to be obtained.
     * @param <T>             type of annotation to be obtained.
     * @return found annotation or null.
     */
    public static <T extends Annotation> T getClassAnnotation(final Class<?> c, final Class<T> annotationClass) {
        return c.getAnnotation(annotationClass);
    }

    /**
     * Gets annotation at method level.
     *
     * @param annotationClass annotation class to be obtained.
     * @param object          object to get annotations from.
     * @param methodName      name of method where annotation is searched.
     * @param parameterTypes  parameter types of method where annotation is searched (can be ignored if method has no
     *                        parameters).
     * @param <T>             type of annotation to be obtained.
     * @return found annotation or null.
     * @throws NoSuchMethodException if method with provided name and parameter types does not exist on provided
     *                               object class.
     */
    public static <T extends Annotation> T getMethodAnnotation(
            final Class<T> annotationClass, final Object object, final String methodName,
            final Class<?>... parameterTypes) throws NoSuchMethodException {
        final var method = object.getClass().getMethod(methodName, parameterTypes);
        return method.getDeclaredAnnotation(annotationClass);
    }

    /**
     * Gets annotations for parameters in a method.
     *
     * @param object         object to get annotations from.
     * @param methodName     name of method where annotations are searched.
     * @param parameterTypes parameter types of methods where annotations are searched (can be ignored if method has no
     *                       parameters).
     * @return array of array containing parameter annotations. First index indicates parameter position in the method,
     * second index indicates annotation position for the method. First dimension length will always be the total number
     * of parameters in the method. Null values indicate that a given parameter has no annotations.
     * @throws NoSuchMethodException if method with provided name and parameter types does not exist on provided object
     *                               class.
     */
    public static Annotation[][] getMethodParameterAnnotations(
            final Object object, final String methodName, final Class<?>... parameterTypes)
            throws NoSuchMethodException {
        final var method = object.getClass().getDeclaredMethod(methodName, parameterTypes);
        return method.getParameterAnnotations();
    }
}
