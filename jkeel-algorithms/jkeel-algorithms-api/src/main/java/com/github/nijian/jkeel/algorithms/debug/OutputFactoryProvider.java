package com.github.nijian.jkeel.algorithms.debug;

import java.util.Iterator;
import java.util.ServiceLoader;

public class OutputFactoryProvider {

    private static OutputFactoryProvider provider;

    private ServiceLoader<OutputFactory> loader;

    private OutputFactoryProvider() {
        loader = ServiceLoader.load(OutputFactory.class);
    }

    public static OutputFactoryProvider getInstance() {
        if (provider != null) {
            return provider;
        }

        synchronized (OutputFactoryProvider.class) {
            if (provider == null) {
                provider = new OutputFactoryProvider();
            }
            return provider;
        }
    }

    public Output getOutput(String text) {

        Output output = null;
        Iterator<OutputFactory> factories = loader.iterator();
        while (output == null && factories.hasNext()) {
            OutputFactory factory = factories.next();
            output = factory.getOutput(text);
        }
        return output;
    }
}
