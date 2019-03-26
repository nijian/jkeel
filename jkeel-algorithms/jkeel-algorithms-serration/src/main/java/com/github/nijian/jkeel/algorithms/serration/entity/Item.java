package com.github.nijian.jkeel.algorithms.serration.entity;

import com.github.nijian.jkeel.algorithms.serration.operands.BigDecimalOperand;
import groovy.lang.Closure;

import java.math.BigDecimal;
import java.util.List;

public class Item<I> {

    private String name;
    private boolean out = false;
    private int scale = 2;

    public void calc(I input, Closure closure, ItemInstance itemInstance, int index, List<BigDecimalOperand> outValueList) {
        Object v = closure.call(input, index);
        BigDecimalOperand operand;
        if (v instanceof BigDecimalOperand) {
            operand = new BigDecimalOperand((BigDecimalOperand) v, scale);
        } else if (v instanceof Integer) {
            operand = new BigDecimalOperand((int) v);
        } else if (v instanceof BigDecimal) {
            operand = new BigDecimalOperand((BigDecimal) v);
        } else {
            operand = new BigDecimalOperand(BigDecimal.valueOf((double) v));
        }
        itemInstance.setValue(operand);
        if (outValueList != null) {
            outValueList.add(operand);
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isOut() {
        return out;
    }

    public void setOut(boolean out) {
        this.out = out;
    }

    public int getScale() {
        return scale;
    }

    public void setScale(int scale) {
        this.scale = scale;
    }
}
