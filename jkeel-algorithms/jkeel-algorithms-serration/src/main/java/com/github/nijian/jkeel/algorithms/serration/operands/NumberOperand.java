package com.github.nijian.jkeel.algorithms.serration.operands;

import java.math.MathContext;

/**
 * hello
 *
 * @param <T> number type
 */
public abstract class NumberOperand<T extends Number> extends Number {
    public T getValue() {
        return value;
    }

    protected static final MathContext mc = new MathContext(10);
    protected T value;
}
