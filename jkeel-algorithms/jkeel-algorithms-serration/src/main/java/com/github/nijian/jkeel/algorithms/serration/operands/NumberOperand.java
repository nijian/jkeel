package com.github.nijian.jkeel.algorithms.serration.operands;

import java.math.MathContext;

/**
 * Created by Johnson.Ni
 *
 * @param < T >
 */
public abstract class NumberOperand<T extends Number> extends Number {
    public T getValue() {
        return value;
    }

    protected static final MathContext mc = new MathContext(10);
    protected T value;
}
