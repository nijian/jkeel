package com.github.nijian.jkeel.concept.spi;

import com.github.nijian.jkeel.concept.Manager;

public interface ConceptFactory {

    <M extends Manager, C extends java.util.function.Function> C getConcept(M manager, String conceptClassName, Class<C> conceptType);

}
