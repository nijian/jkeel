package com.github.nijian.jkeel.concept.spi;

import com.github.nijian.jkeel.concept.Algorithm;

public interface AlgorithmFactory {

    Algorithm getAlgorithm(String name);

}
