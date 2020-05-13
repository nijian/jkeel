package com.github.nijian.jkeel.concept.spi;

import com.github.nijian.jkeel.concept.Algorithm;

import java.util.Iterator;
import java.util.ServiceLoader;

public class AlgorithmFactoryProvider {

    private static AlgorithmFactoryProvider provider;

    private ServiceLoader<AlgorithmFactory> loader;

    private AlgorithmFactoryProvider() {
        loader = ServiceLoader.load(AlgorithmFactory.class);
    }

    public static AlgorithmFactoryProvider getInstance() {
        if (provider != null) {
            return provider;
        }

        synchronized (AlgorithmFactoryProvider.class) {
            if (provider == null) {
                provider = new AlgorithmFactoryProvider();
            }
            return provider;
        }
    }

    public Algorithm getAlgorithm(String name) {

        Algorithm algorithm = null;
        Iterator<AlgorithmFactory> factories = loader.iterator();
        while (algorithm == null && factories.hasNext()) {
            AlgorithmFactory factory = factories.next();
            algorithm = factory.getAlgorithm(name);
        }

        if (algorithm == null) {
            throw new RuntimeException("algorithm Not Found");
        }
        return algorithm;
    }
}
