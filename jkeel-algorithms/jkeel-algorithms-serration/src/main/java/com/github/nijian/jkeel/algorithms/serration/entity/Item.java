package com.github.nijian.jkeel.algorithms.serration.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.github.nijian.jkeel.algorithms.serration.operands.BigDecimalOperand;
import groovy.lang.Closure;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.List;

/**
 * Item is Serration algorithm calculation minimum unit.
 *
 * @param <I> final input type
 * @author nj
 * @since 0.0.1
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Item<I> {

    private static Logger logger = LoggerFactory.getLogger(Item.class);

    /**
     * Item name is unique in a LayoutTemplate.
     */
    private String name;

    /**
     * Calculated value will be moved to LayoutOutput or not?
     */
    private boolean out = false;

    /**
     * The scale of this value, default is 2.
     */
    private int scale = 2;

    /**
     * Calculate item instance value and output to item out map
     *
     * @param input          final/converted input
     * @param itemInstance   item instance
     * @param layoutInstance layout instance
     * @param isLidx         is layout index or not
     * @param closure        formula closure
     * @param outValueList   out value list in item out map
     */
    public void calc(final I input, final ItemInstance itemInstance, final LayoutInstance layoutInstance,
                     final boolean isLidx, final Closure<Number> closure, final List<BigDecimalOperand> outValueList) {
        calc(input, itemInstance, layoutInstance, isLidx, closure, outValueList, false);
    }

    /**
     * Calculate item instance value and output to item out map
     *
     * @param input          final/converted input
     * @param itemInstance   item instance
     * @param layoutInstance layout instance
     * @param isLidx         is layout index or not
     * @param closure        formula closure
     * @param outValueList   out value list in item out map
     * @param negToZero      negative to ZERO or not
     */
    public void calc(final I input, final ItemInstance itemInstance, final LayoutInstance layoutInstance,
                     final boolean isLidx, final Closure<Number> closure, final List<BigDecimalOperand> outValueList,
                     final boolean negToZero) {
        calc(input, itemInstance, layoutInstance, isLidx, closure, negToZero);
        outValueList.add(itemInstance.getValue());
    }

    /**
     * Calculate item instance value
     *
     * @param input          final/converted input
     * @param itemInstance   item instance
     * @param layoutInstance layout instance
     * @param isLidx         is layout index or not
     * @param closure        formula closure
     */
    public void calc(final I input, final ItemInstance itemInstance, final LayoutInstance layoutInstance,
                     final boolean isLidx, final Closure<Number> closure) {
        calc(input, itemInstance, layoutInstance, isLidx, closure, false);
    }

    /**
     * Calculate item instance value
     *
     * @param input          final/converted input
     * @param itemInstance   item instance
     * @param layoutInstance layout instance
     * @param isLidx         is layout index or not
     * @param closure        formula closure
     * @param negToZero      negative to ZERO or not
     */
    public void calc(final I input, final ItemInstance itemInstance, final LayoutInstance layoutInstance,
                     final boolean isLidx, final Closure<Number> closure, final boolean negToZero) {
        try {
            Number rawValue;
            if (isLidx) {
                rawValue = closure.call(input, layoutInstance.getIndex());
            } else {
                rawValue = closure.call(input, itemInstance.getIndex());
            }
            if (negToZero && rawValue.doubleValue() < 0) {
                rawValue = BigDecimal.ZERO;
            }
            BigDecimalOperand operand = new BigDecimalOperand(rawValue, scale, true);
            itemInstance.setValue(operand);
        } catch (Exception e) {
            logger.error("Failed to calculate value for Item:{}", name);
            throw new RuntimeException("Failed to calculate Item value", e);
        }
    }

    /**
     * Get item name
     *
     * @return item name
     */
    public String getName() {
        return name;
    }

    /**
     * Set item name
     *
     * @param name item name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Output item value to item out map or not?
     *
     * @return true or false
     */
    public boolean isOut() {
        return out;
    }

    /**
     * Set output flag
     *
     * @param out true or false
     */
    public void setOut(boolean out) {
        this.out = out;
    }

    /**
     * Get the scale config of item value
     *
     * @return scale config
     */
    public int getScale() {
        return scale;
    }

    /**
     * Set the scale config for item value
     *
     * @param scale scale config
     */
    public void setScale(int scale) {
        this.scale = scale;
    }

}
