package com.github.nijian.jkeel.concept;

public abstract class Mapping<T, R> extends Concept<T, R> {

    @Override
    public R apply(ConceptInput<?, T> mappingConfigTConceptInput) {
        return null;
    }
}
