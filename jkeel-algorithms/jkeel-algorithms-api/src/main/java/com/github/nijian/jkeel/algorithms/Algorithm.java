package com.github.nijian.jkeel.algorithms;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * Created by nijian
 *
 * @param <I> input type
 * @param <R> return type
 * @param <V> cache value type
 */
public abstract class Algorithm<I, R, V> {

    private static Logger logger = LoggerFactory.getLogger(Algorithm.class);

    public R perform(I rawInput, AlgorithmContext<?, ?, V> ac) {
        return perform(rawInput, null, ac);
    }

    public R perform(I rawInput, Map<String, ?> var, AlgorithmContext<?, ?, V> ac) {

        long start = System.currentTimeMillis();
        Map<String, ?> input = convertInput(rawInput, var, ac);
        long converted = System.currentTimeMillis();
        logger.info("Converting input completed with [{}]ms", converted - start);

        R result = calc(input, ac);
        long calculated = System.currentTimeMillis();
        logger.info("Calculation completed with [{}]ms", calculated - converted);

        //TODO missing other functions, e.g. log calculation details for verification

        return result;
    }

    public String getName() {
        return getClass().getName();
    }

    protected abstract Map<String, ?> convertInput(I rawInput, Map<String, ?> var, AlgorithmContext<?, ?, V> ac);

    protected abstract R calc(Map<String, ?> input, AlgorithmContext<?, ?, V> ac);

}
