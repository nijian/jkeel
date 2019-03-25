package com.github.nijian.jkeel.algorithms.serration.entity;

import com.github.nijian.jkeel.algorithms.serration.operands.BigDecimalOperand;

public class ItemInstance {

    private int index;
    private Item item;
    private BigDecimalOperand value;

    public ItemInstance(Item item) {
        this.item = item;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public BigDecimalOperand getValue() {
        return value;
    }

    public void setValue(BigDecimalOperand value) {
        this.value = value;
    }
}
