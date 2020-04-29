package com.github.nijian.jkeel.concept;

import java.util.function.Function;

public abstract class Concept<V, R> implements Function<ConceptInput<? extends Manager, V>, R> {

    public abstract Class<V> getInputType();

    public abstract Class<R> getReturnType();

}