package com.github.nijian.jkeel.algorithms.serration.operands;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;

/**
 * Created by Johnson.Ni
 */
public class BigDecimalOperand extends NumberOperand<BigDecimal> implements Comparable<BigDecimalOperand> {

    public BigDecimalOperand(BigDecimalOperand o, int scale) {
        if (o.value.compareTo(BigDecimal.ZERO) <= 0) {
            this.value = BigDecimal.ZERO;
        } else {
            this.value = o.value.setScale(scale, RoundingMode.HALF_UP);
        }
    }

    public BigDecimalOperand(Number value) {
        if (value instanceof BigDecimal) {
            this.value = (BigDecimal) value;
        } else if (value instanceof BigDecimalOperand) {
            this.value = ((BigDecimalOperand) value).getValue();
        } else if (value instanceof Integer) {
            this.value = new BigDecimal((Integer) value);
        } else if (value instanceof Double) {
            this.value = BigDecimal.valueOf((Double) value);
        } else {
            throw new RuntimeException("Unsupported number type");
        }
    }

    public BigDecimalOperand(BigDecimal value) {
        this.value = value;
    }

    public BigDecimalOperand(int value) {
        this.value = new BigDecimal(value);
    }

    public BigDecimalOperand(String value) {
        this.value = new BigDecimal(value);
    }

    public BigDecimalOperand plus(BigDecimalOperand o) {
        return new BigDecimalOperand(getValue().add(o.getValue(), mc));
    }

    public BigDecimalOperand plus(BigDecimal o) {
        return new BigDecimalOperand(getValue().add(o, mc));
    }

    public BigDecimalOperand plus(BigInteger o) {
        return new BigDecimalOperand(getValue().add(new BigDecimal(o)));
    }

    public BigDecimalOperand plus(int o) {
        return new BigDecimalOperand(getValue().add(new BigDecimal(o), mc));
    }

    public BigDecimalOperand plus(long o) {
        return new BigDecimalOperand(getValue().add(BigDecimal.valueOf(o), mc));
    }

    public BigDecimalOperand plus(double o) {
        return new BigDecimalOperand(getValue().add(BigDecimal.valueOf(o), mc));
    }

    public BigDecimalOperand minus(BigDecimalOperand o) {
        return new BigDecimalOperand(getValue().subtract(o.getValue(), mc));
    }

    public BigDecimalOperand minus(BigDecimal o) {
        return new BigDecimalOperand(getValue().subtract(o, mc));
    }

    public BigDecimalOperand minus(BigInteger o) {
        return new BigDecimalOperand(getValue().subtract(new BigDecimal(o), mc));
    }

    public BigDecimalOperand minus(int o) {
        return new BigDecimalOperand(getValue().subtract(new BigDecimal(o), mc));
    }

    public BigDecimalOperand minus(long o) {
        return new BigDecimalOperand(getValue().subtract(BigDecimal.valueOf(o), mc));
    }

    public BigDecimalOperand minus(double o) {
        return new BigDecimalOperand(getValue().subtract(BigDecimal.valueOf(o), mc));
    }

    public BigDecimalOperand multiply(BigDecimalOperand o) {
        return new BigDecimalOperand(getValue().multiply(o.getValue(), mc));
    }

    public BigDecimalOperand multiply(BigDecimal o) {
        return new BigDecimalOperand(getValue().multiply(o, mc));
    }

    public BigDecimalOperand multiply(BigInteger o) {
        return new BigDecimalOperand(getValue().multiply(new BigDecimal(o), mc));
    }

    public BigDecimalOperand multiply(int o) {
        return new BigDecimalOperand(getValue().multiply(new BigDecimal(o), mc));
    }

    public BigDecimalOperand multiply(long o) {
        return new BigDecimalOperand(getValue().multiply(BigDecimal.valueOf(o), mc));
    }

    public BigDecimalOperand multiply(double o) {
        return new BigDecimalOperand(getValue().multiply(BigDecimal.valueOf(o), mc));
    }

    public BigDecimalOperand div(BigDecimalOperand o) {
        return new BigDecimalOperand(getValue().divide(o.getValue(), mc));
    }

    public BigDecimalOperand div(BigDecimal o) {
        return new BigDecimalOperand(getValue().divide(o, mc));
    }

    public BigDecimalOperand div(BigInteger o) {
        return new BigDecimalOperand(getValue().divide(new BigDecimal(o), mc));
    }

    public BigDecimalOperand div(int o) {
        return new BigDecimalOperand(getValue().divide(new BigDecimal(o), mc));
    }

    public BigDecimalOperand div(long o) {
        return new BigDecimalOperand(getValue().divide(BigDecimal.valueOf(o), mc));
    }

    public BigDecimalOperand div(double o) {
        return new BigDecimalOperand(getValue().divide(BigDecimal.valueOf(o), mc));
    }

    //max

    public BigDecimalOperand max(BigDecimalOperand o) {
        return (compareTo(o) >= 0 ? this : o);
    }

    public BigDecimalOperand max(int o) {
        BigDecimalOperand v = new BigDecimalOperand(o);
        return (compareTo(new BigDecimalOperand(o)) >= 0 ? this : v);
    }

    @Override
    public int compareTo(BigDecimalOperand o) {
        return value.compareTo(o.value);
    }


    @Override
    public int intValue() {
        return getValue().intValue();
    }

    @Override
    public long longValue() {
        return getValue().longValue();
    }

    @Override
    public float floatValue() {
        return getValue().floatValue();
    }

    @Override
    public double doubleValue() {
        return getValue().doubleValue();
    }

}
