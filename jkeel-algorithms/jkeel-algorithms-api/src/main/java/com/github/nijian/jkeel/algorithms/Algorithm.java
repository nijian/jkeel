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
public abstract class Algorithm<I, R, T extends AlgorithmTemplate, C extends AlgorithmConfig> {

    private static Logger logger = LoggerFactory.getLogger(Algorithm.class);

    public R perform(I rawInput, AlgorithmContext<T, C> ac) {
        return perform(rawInput, null, ac);
    }

    public R perform(I rawInput, Map<String, ?> var, AlgorithmContext<T, C> ac) {

        long start = System.currentTimeMillis();
        Map<String, ?> input = convertInput(rawInput, var, ac);
        long converted = System.currentTimeMillis();
        logger.info("Converting input completed with [{}]ms", converted - start);

        R result = calc(input, ac);
        long calculated = System.currentTimeMillis();
        logger.info("Calculation completed with [{}]ms", calculated - converted);

        //TODO missing other functions, e.g. log calculation details for verification
//        if (ac.getConfig().isDebugEnabled()) {
//            debug(result);
//        }
        debug(result);

        return result;
    }

    protected abstract Map<String, ?> convertInput(I rawInput, Map<String, ?> var, AlgorithmContext<T, C> ac);

    protected abstract R calc(Map<String, ?> input, AlgorithmContext<T, C> ac);

    protected abstract String debug(R result);

}
