package com.github.nijian.jkeel.algorithms;

import java.util.Map;

public class SampleAlgorithm extends Algorithm<String, Map, String> {

    @Override
    public Map<String, ?> calc(Map<String, ?> input, AlgorithmContext<?, ?, String> ac) {
        return null;
    }

    @Override
    public String getName() {
        return getClass().getName();
    }

    @Override
    protected Map<String, ?> convertInput(String rawInput, Map<String, ?> var, AlgorithmContext<?, ?, String> ac) {
        return null;
    }
}
