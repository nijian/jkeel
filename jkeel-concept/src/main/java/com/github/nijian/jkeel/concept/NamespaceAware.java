package com.github.nijian.jkeel.concept;

public interface NamespaceAware {

    default String ns(){
        return this.getClass().getPackage().getAnnotation(Namespace.class).value();
    }
}
