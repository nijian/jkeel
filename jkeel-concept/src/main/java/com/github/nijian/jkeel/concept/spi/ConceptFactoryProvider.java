package com.github.nijian.jkeel.concept.spi;

import com.github.nijian.jkeel.concept.Manager;

import java.util.Iterator;
import java.util.ServiceLoader;

public class ConceptFactoryProvider {

    private static ConceptFactoryProvider provider;

    private ServiceLoader<ConceptFactory> loader;

    private ConceptFactoryProvider() {
        loader = ServiceLoader.load(ConceptFactory.class);
    }

    public static ConceptFactoryProvider getInstance() {
        if (provider != null) {
            return provider;
        }

        synchronized (ConceptFactoryProvider.class) {
            if (provider == null) {
                provider = new ConceptFactoryProvider();
            }
            return provider;
        }
    }


    public <M extends Manager, C extends java.util.function.Function> C getConcept(M manager, String conceptClassName, Class<C> conceptType) {

        C concept = null;
        Iterator<ConceptFactory> factories = loader.iterator();
        while (concept == null && factories.hasNext()) {
            ConceptFactory factory = factories.next();
            concept = factory.getConcept(manager, conceptClassName, conceptType);
        }

        if (concept == null) {
            throw new RuntimeException("Concept Not Found");
        }
        return concept;
    }
}
