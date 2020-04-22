package com.github.nijian.jkeel.concept.spi;

import com.github.nijian.jkeel.concept.ManagedConcept;
import com.github.nijian.jkeel.concept.Manager;

public interface ConceptFactory {

    <M extends Manager, C extends ManagedConcept> C getConcept(M manager, String conceptClassName, Class<C> conceptType);

}
