package com.github.nijian.jkeel.algorithms;

public class SampleAlgorithmFactory implements AlgorithmFactory {

    @Override
    public Algorithm getAlgorithm(String algorithmName) {
        //should be singleton
        return new SampleAlgorithm();
    }
}
