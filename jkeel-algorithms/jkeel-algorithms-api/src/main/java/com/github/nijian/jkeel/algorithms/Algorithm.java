package com.github.nijian.jkeel.algorithms;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * Created by nijian
 *
 * @param <I> input type
 * @param <R> return type
 */
public abstract class Algorithm<I, R, C extends AlgorithmContext> {

    private static Logger logger = LoggerFactory.getLogger(Algorithm.class);

    public R perform(I rawInput, C ac) {
        return perform(rawInput, ac, false);
    }

    public R perform(I rawInput, C ac, boolean isDebugMode) {
        return perform(rawInput, null, ac, isDebugMode);
    }

    public R perform(I rawInput, Map<String, ?> var, C ac) {
        return perform(rawInput, var, ac, false);
    }

    public R perform(I rawInput, Map<String, ?> var, C ac, boolean isDebugMode) {

        long start = System.currentTimeMillis();
        Map<String, ?> input = convertInput(rawInput, var, ac);
        long converted = System.currentTimeMillis();
        logger.info("Converting input completed with [{}]ms", converted - start);

        R result = calc(input, ac);
        long calculated = System.currentTimeMillis();
        logger.info("Calculation completed with [{}]ms", calculated - converted);

        if (isDebugMode) {
            debug(result);
        }

        return result;
    }

    protected abstract Map<String, ?> convertInput(I rawInput, Map<String, ?> var, C ac);

    protected abstract R calc(Map<String, ?> input, C ac);

    protected abstract void debug(R result);

}
