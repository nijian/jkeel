package com.github.nijian.jkeel.concept;

public abstract class Mapping<M extends Manager, T, R> extends ManagedConcept<M, T, R> {

    @Override
    public R apply(ConceptInput<M, T> mappingConfigTConceptInput) {
        return null;
    }
}
