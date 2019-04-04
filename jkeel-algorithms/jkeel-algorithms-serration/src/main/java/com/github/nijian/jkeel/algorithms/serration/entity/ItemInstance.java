package com.github.nijian.jkeel.algorithms.serration.entity;

import com.github.nijian.jkeel.algorithms.serration.operands.BigDecimalOperand;

/**
 * Item instance
 *
 * @author nj
 * @since 0.0.1
 */
public class ItemInstance {

    /**
     * index in list
     */
    private int index;

    /**
     * Item
     */
    private Item item;

    /**
     * item instance value
     */
    private BigDecimalOperand value;

    /**
     * Constructor
     *
     * @param item item
     */
    public ItemInstance(Item item) {
        this.item = item;
    }

    /**
     * Get index
     *
     * @return index
     */
    public int getIndex() {
        return index;
    }

    /**
     * Set index
     *
     * @param index index
     */
    public void setIndex(int index) {
        this.index = index;
    }

    /**
     * Get item
     *
     * @return item
     */
    public Item getItem() {
        return item;
    }

    /**
     * Set item
     *
     * @param item item
     */
    public void setItem(Item item) {
        this.item = item;
    }

    /**
     * Get value
     *
     * @return item instance value
     */
    public BigDecimalOperand getValue() {
        return value;
    }

    /**
     * Set value
     *
     * @param value item instance value
     */
    public void setValue(BigDecimalOperand value) {
        this.value = value;
    }
}
