package com.github.nijian.jkeel.algorithms;

import java.util.Map;

public class SampleAlgorithm extends Algorithm<String, Map, SimpleTemplate, SimpleConfig> {

    @Override
    public Map<String, ?> calc(Map<String, ?> input, AlgorithmContext<SimpleTemplate, SimpleConfig> ac) {
        return null;
    }

    @Override
    protected Map<String, ?> convertInput(String rawInput, Map<String, ?> var, AlgorithmContext<SimpleTemplate, SimpleConfig> ac) {
        return null;
    }

    @Override
    protected void debug(Map result) {

    }
}
