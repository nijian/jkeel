package com.github.nijian.jkeel.algorithms;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * Algorithms provide the ability to compute values. An algorithm is usually created by algorithm factory as singleton
 * object.
 *
 * @param <I> raw input bean type
 * @param <R> calculation result type
 * @param <C> algorithm context type, e.g. template algorithm context
 * @author nj
 * @since 0.0.1
 */
public abstract class Algorithm<I, R, C extends AlgorithmContext> {

    private static Logger logger = LoggerFactory.getLogger(Algorithm.class);

    /**
     * Perform calculating using this algorithm.
     *
     * @param rawInput raw input
     * @param ac       algorithm context
     * @return calculation result
     */
    public final R perform(I rawInput, C ac) {
        return perform(rawInput, ac, false);
    }

    /**
     * Perform calculating using this algorithm.
     *
     * @param rawInput    raw input
     * @param ac          algorithm context
     * @param isDebugMode is in debug mode or not
     * @return calculation result
     */
    public final R perform(I rawInput, C ac, boolean isDebugMode) {
        return perform(rawInput, null, ac, isDebugMode);
    }

    /**
     * Perform calculating using this algorithm.
     *
     * @param rawInput raw input
     * @param var      variables collection
     * @param ac       algorithm context
     * @return calculation result
     */
    public final R perform(I rawInput, Map<String, ?> var, C ac) {
        return perform(rawInput, var, ac, false);
    }

    /**
     * Perform calculating using this algorithm.
     *
     * @param rawInput    raw input
     * @param var         variables collection
     * @param ac          algorithm context
     * @param isDebugMode is in debug mode or not
     * @return calculation result
     */
    public final R perform(I rawInput, Map<String, ?> var, C ac, boolean isDebugMode) {

        long start = System.currentTimeMillis();

        Map<String, ?> input;
        try {
            input = convertInput(rawInput, var, ac);
        } catch (Exception e) {
            logger.error("Failed to convert raw input", e);
            throw new RuntimeException("Failed to convert raw input", e);
        }

        long converted = System.currentTimeMillis();
        logger.info("Converting input completed with [{}]ms", converted - start);

        R result;
        try {
            if (input == null) {
                result = calc(rawInput, ac);
            } else {
                result = calc(input, ac);
            }
        } catch (Exception e) {
            logger.error("Failed to perform calculation", e);
            throw new RuntimeException("Failed to perform calculation", e);
        }

        long calculated = System.currentTimeMillis();
        logger.info("Calc completed with [{}]ms", calculated - converted);

        //should be a new thread, and ignore any failed
        if (isDebugMode) {
            debug(result);
        }

        return result;
    }

    /**
     * Delegate converting to algorithm implementation.
     *
     * @param rawInput raw input
     * @param var      variables collection
     * @param ac       algorithm context
     * @return converted input
     */
    protected Map<String, ?> convertInput(I rawInput, Map<String, ?> var, C ac) {
        return null;
    }

    /**
     * Delegate calculating to algorithm implementation.
     *
     * @param input converted input
     * @param ac    algorithm context
     * @param <T>   real input type
     * @return calculation result
     */
    protected abstract <T> R calc(T input, C ac);

    /**
     * Delegate debug output to algorithm implementation.
     *
     * @param result calculation result
     */
    protected abstract void debug(R result);

}
