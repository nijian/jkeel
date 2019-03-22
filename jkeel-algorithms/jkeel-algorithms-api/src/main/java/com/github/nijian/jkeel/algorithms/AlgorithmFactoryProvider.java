package com.github.nijian.jkeel.algorithms;

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

    public Algorithm getAlgorithm(String text) {

        Algorithm algorithm = null;
        Iterator<AlgorithmFactory> factories = loader.iterator();
        while (algorithm == null && factories.hasNext()) {
            AlgorithmFactory factory = factories.next();
            algorithm = factory.getAlgorithm(text);
        }
        return algorithm;
    }
}
