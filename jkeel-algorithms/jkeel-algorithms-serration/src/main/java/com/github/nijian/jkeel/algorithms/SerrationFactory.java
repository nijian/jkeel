package com.github.nijian.jkeel.algorithms;

public final class SerrationFactory implements AlgorithmFactory {

    @Override
    public Algorithm<?, ?, ?> getAlgorithm(String algorithmName) {
        if (algorithmName.equals(Serration.class.getName())) {
            return Serration.getInstance();
        }
        return null;
    }
}
