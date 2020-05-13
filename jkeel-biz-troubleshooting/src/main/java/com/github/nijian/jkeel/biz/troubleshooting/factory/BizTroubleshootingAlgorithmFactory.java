package com.github.nijian.jkeel.biz.troubleshooting.factory;

import com.github.nijian.jkeel.biz.troubleshooting.algorithm.FooAlgorithm;
import com.github.nijian.jkeel.concept.Algorithm;
import com.github.nijian.jkeel.concept.spi.AlgorithmFactory;

public class BizTroubleshootingAlgorithmFactory implements AlgorithmFactory {

    @Override
    public Algorithm getAlgorithm(String name) {
        if (name.equals("FOO")) {
            return new FooAlgorithm();
        }

        return null;
    }
}
