package com.github.nijian.jkeel.algorithms;

import com.github.nijian.jkeel.algorithms.serration.CalcConfig;
import com.github.nijian.jkeel.algorithms.serration.entity.LayoutTemplate;

public final class SerrationFactory implements AlgorithmFactory {

    @Override
    public Algorithm<?, ?, LayoutTemplate, CalcConfig> getAlgorithm(String algorithmName) {
        if (algorithmName.equals(Serration.class.getName())) {
            return Serration.getInstance();
        }
        return null;
    }
}
