package com.github.nijian.jkeel.concept;

import java.util.function.Function;

public abstract class ManagedConcept<M extends Manager, T, R> implements ContextAware, Manageable<M>, Function<ConceptInput<M, T>, R> {

    protected M manager;


    @Override
    public void setManager(M manager) {
        this.manager = manager;
    }

    @Override
    public M getManager() {
        return manager;
    }


}
