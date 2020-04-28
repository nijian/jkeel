package com.github.nijian.jkeel.concept;

import java.util.function.Function;

public abstract class Concept<V, R> implements Function<ConceptInput<? extends Manager, V>, R> {

}