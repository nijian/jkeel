package com.github.nijian.jkeel.code.control;

public interface CodeApplyStrategy<T> {

    boolean filter();
    T condition();

}
