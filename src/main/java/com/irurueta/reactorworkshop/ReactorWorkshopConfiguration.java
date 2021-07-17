package com.irurueta.reactorworkshop;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Contains configuration for this application.
 */
@Configuration
@ConfigurationProperties(prefix = "reactorworkshop")
public class ReactorWorkshopConfiguration {

    /**
     * Maximum allowed polynomial degree for provided input evaluation steps.
     */
    private int maxPolynomialDegree;

    /**
     * Maximum allowed depth of polynomial evaluation steps tree.
     */
    private int maxPolynomialTreeDepth;

    /**
     * Maximum allowed number of polynomial trees to evaluate in a single request.
     */
    private int maxPolynomialCount;

    /**
     * Maximum allowed number of arithmetic sequences to compute in a single request.
     */
    private int maxArithmeticSequenceCount;

    /**
     * Gets maximum allowed polynomial degree for provided input evaluation steps.
     *
     * @return maximum allowed polynomial degree.
     */
    public int getMaxPolynomialDegree() {
        return maxPolynomialDegree;
    }

    /**
     * Sets maximum allowed polynomial degree fro provided input evaluation steps.
     *
     * @param maxPolynomialDegree maximum allowed polynomial degree.
     * @throws IllegalArgumentException if provided value is less than 1.
     */
    public void setMaxPolynomialDegree(final int maxPolynomialDegree) {
        if (maxPolynomialDegree < 1) {
            throw new IllegalArgumentException("Invalid max polynomial degree value: " + maxPolynomialDegree);
        }
        this.maxPolynomialDegree = maxPolynomialDegree;
    }

    /**
     * Gets maximum allowed depth of polynomial evaluation steps tree.
     *
     * @return maximum allowed depth of polynomial evaluation steps tree.
     */
    public int getMaxPolynomialTreeDepth() {
        return maxPolynomialTreeDepth;
    }

    /**
     * Sets maximum allowed depth of polynomial evaluation steps tree.
     *
     * @param maxPolynomialTreeDepth maximum allowed depth of polynomial evaluation steps tree.
     * @throws IllegalArgumentException if provided value is less than 1.
     */
    public void setMaxPolynomialTreeDepth(final int maxPolynomialTreeDepth) {
        if (maxPolynomialTreeDepth < 1) {
            throw new IllegalArgumentException("Invalid polynomial tree depth value: " + maxPolynomialTreeDepth);
        }

        this.maxPolynomialTreeDepth = maxPolynomialTreeDepth;
    }

    /**
     * Gets maximum allowed number of polynomial trees to evaluate in a single request.
     *
     * @return maximum allowed number of polynomial trees to evaluate in a single request.
     */
    public int getMaxPolynomialCount() {
        return maxPolynomialCount;
    }

    /**
     * Sets maximum allowed number of polynomial trees to evaluate in a single request.
     *
     * @param maxPolynomialCount maximum allowed number of polynomial trees to evaluate in a single request.
     * @throws IllegalArgumentException if provided value is less than 1.
     */
    public void setMaxPolynomialCount(final int maxPolynomialCount) {
        if (maxPolynomialCount < 1) {
            throw new IllegalArgumentException("Invalid maximum allowed number of polynomial trees: "
                    + maxPolynomialCount);
        }

        this.maxPolynomialCount = maxPolynomialCount;
    }

    /**
     * Gets maximum allowed number of arithmetic sequences to compute in a single request.
     *
     * @return maximum allowed number of arithmetic sequences to compute in a single request.
     */
    public int getMaxArithmeticSequenceCount() {
        return maxArithmeticSequenceCount;
    }

    /**
     * Sets maximum allowed number of arithmetic sequences to compute in a single request.
     *
     * @param maxArithmeticSequenceCount maximum allowed number of arithmetic sequences to compute in a single request.
     * @throws IllegalArgumentException if provided value is less than 1.
     */
    public void setMaxArithmeticSequenceCount(final int maxArithmeticSequenceCount) {
        if (maxArithmeticSequenceCount < 1) {
            throw new IllegalArgumentException("Invalid maximum allowed number of arithmetic sequences:"
                    + maxArithmeticSequenceCount);
        }

        this.maxArithmeticSequenceCount = maxArithmeticSequenceCount;
    }
}
