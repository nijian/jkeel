package com.github.nijian.jkeel.algorithms;

import java.util.Map;

public class SampleAlgorithm extends Algorithm<String, Map, AlgorithmContext> {

    @Override
    public <T> Map<String, ?> calc(T input, AlgorithmContext ac) {
        return null;
    }

    @Override
    protected Map<String, ?> convertInput(String rawInput, Map<String, ?> var, AlgorithmContext ac) {
        return null;
    }

    @Override
    protected void debug(Map result) {

    }
}
