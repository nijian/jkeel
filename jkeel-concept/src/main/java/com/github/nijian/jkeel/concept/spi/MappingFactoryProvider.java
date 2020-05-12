package com.github.nijian.jkeel.concept.spi;

import com.github.nijian.jkeel.concept.Mapping;

import java.util.Iterator;
import java.util.ServiceLoader;

public class MappingFactoryProvider {

    private static MappingFactoryProvider provider;

    private ServiceLoader<MappingFactory> loader;

    private MappingFactoryProvider() {
        loader = ServiceLoader.load(MappingFactory.class);
    }

    public static MappingFactoryProvider getInstance() {
        if (provider != null) {
            return provider;
        }

        synchronized (MappingFactoryProvider.class) {
            if (provider == null) {
                provider = new MappingFactoryProvider();
            }
            return provider;
        }
    }

    public Mapping getMapping(String name) {

        Mapping mapping = null;
        Iterator<MappingFactory> factories = loader.iterator();
        while (mapping == null && factories.hasNext()) {
            MappingFactory factory = factories.next();
            mapping = factory.getMapping(name);
        }

        if (mapping == null) {
            throw new RuntimeException("mapping Not Found");
        }
        return mapping;
    }
}
