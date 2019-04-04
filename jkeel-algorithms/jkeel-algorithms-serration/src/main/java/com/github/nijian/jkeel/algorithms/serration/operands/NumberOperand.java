package com.github.nijian.jkeel.algorithms.serration.operands;

import java.math.MathContext;

/**
 * NumberOperand
 *
 * @param <T> number type
 * @author nj
 * @since 0.0.1
 */
public abstract class NumberOperand<T extends Number> extends Number {

    /**
     * math context
     */
    protected static final MathContext mc = new MathContext(10);

    /**
     * value
     */
    protected T value;

    /**
     * Get value
     *
     * @return value
     */
    public T getValue() {
        return value;
    }


}
