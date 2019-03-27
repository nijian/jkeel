package com.github.nijian.jkeel.algorithms.serration.entity;

import com.github.nijian.jkeel.algorithms.serration.operands.BigDecimalOperand;
import groovy.lang.Closure;

import java.util.List;

public class Item<I> {

    private String name;
    private boolean out = false;
    private int scale = 2;

    public void calc(I input, Closure<Number> closure, ItemInstance itemInstance, int index, List<BigDecimalOperand> outValueList) {
        BigDecimalOperand operand = new BigDecimalOperand(closure.call(input, index));

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
