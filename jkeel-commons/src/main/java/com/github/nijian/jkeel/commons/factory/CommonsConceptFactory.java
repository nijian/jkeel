package com.github.nijian.jkeel.commons.factory;

import com.github.nijian.jkeel.concept.Manager;
import com.github.nijian.jkeel.concept.spi.ConceptFactory;

public final class CommonsConceptFactory implements ConceptFactory {

    @Override
    public <M extends Manager, C extends java.util.function.Function> C getConcept(M manager, String conceptClassName, Class<C> conceptType) {

        C concept = null;

        try {
            Class<?> c = Class.forName(conceptClassName);
            concept = (C) c.newInstance();
        } catch (Exception e) {

        }

        return concept;
    }
}
