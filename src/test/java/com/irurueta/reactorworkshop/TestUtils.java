package com.irurueta.reactorworkshop;

import java.lang.annotation.Annotation;

public class TestUtils {

    public static <T extends Annotation> T getClassAnnotation(final Class<?> c, final Class<T> annotationClass) {
        return c.getAnnotation(annotationClass);
    }

    public static <T extends Annotation> T getMethodAnnotation(
            final Class<T> annotationClass, final Object object, final String methodName,
            final Class<?>... parameterTypes) throws NoSuchMethodException {
        final var method = object.getClass().getMethod(methodName, parameterTypes);
        return method.getDeclaredAnnotation(annotationClass);
    }

    public static Annotation[][] getMethodParameterAnnotations(
            final Object object, final String methodName, final Class<?>... parameterTypes)
            throws NoSuchMethodException {
        final var method = object.getClass().getDeclaredMethod(methodName, parameterTypes);
        return method.getParameterAnnotations();
    }
}
