package com.github.nijian.jkeel.concept;

public interface Manageable<M extends Manager> {

    default void setManager(M manager) {
    }

    default M getManager() {
        return null;
    }
}
