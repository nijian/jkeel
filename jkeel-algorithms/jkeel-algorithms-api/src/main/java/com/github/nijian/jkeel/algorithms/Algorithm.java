package com.github.nijian.jkeel.algorithms;

import java.util.Map;

/**
 * Created by johnson.ni
 */
public abstract class Algorithm<I, R> {

    public R calc(I rawInput, AlgorithmContext ac) {
        return calc(rawInput, null, ac);
    }

    public abstract R calc(I rawInput, Map<String, ?> var, AlgorithmContext ac);

    public abstract String getName();

    protected abstract Map<String, ?> convertInput(I rawInput, Map<String, ?> var);

}
