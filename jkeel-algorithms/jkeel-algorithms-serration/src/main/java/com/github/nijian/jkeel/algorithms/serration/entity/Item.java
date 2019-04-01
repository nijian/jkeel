package com.github.nijian.jkeel.algorithms.serration.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.github.nijian.jkeel.algorithms.serration.operands.BigDecimalOperand;
import groovy.lang.Closure;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Item is Serration algorithm calculation minimum unit. It's defined in the LayoutTemplate.
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

    public void calc(I input, ItemInstance itemInstance, Closure<Number> closure, List<BigDecimalOperand> outValueList) {
        calc(input, itemInstance, closure);
        outValueList.add(itemInstance.getValue());
    }

    public void calc(I input, ItemInstance itemInstance, Closure<Number> closure) {
        try {
            Number rawValue = closure.call(input, itemInstance.getIndex());
            BigDecimalOperand operand = new BigDecimalOperand(rawValue, scale, true);
            itemInstance.setValue(operand);
        } catch (Exception e) {
            logger.error("Failed to calculate value for Item:{}", name);
            throw new RuntimeException("Failed to calculate Item value", e);
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
