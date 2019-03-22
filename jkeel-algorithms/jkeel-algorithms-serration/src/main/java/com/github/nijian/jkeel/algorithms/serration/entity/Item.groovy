package com.github.nijian.jkeel.algorithms.serration.entity


import com.github.nijian.jkeel.algorithms.serration.MixinFuncs
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.github.nijian.jkeel.algorithms.serration.operands.BigDecimalOperand

/**
 * DO NOT CHANGE THIS CLASS UNLESS YOU ARE CLEAR WHAT EXACT IMPACT FOR PERFORMANCE!!!
 *
 *
 * Created by johnson.ni
 */
@JsonIgnoreProperties(ignoreUnknown = true)
final class Item<I> implements MixinFuncs {
    String name
    boolean out = false
    int scale = 2

    void calc(I input, Closure closure, ItemInstance itemInstance, int index, List<BigDecimalOperand> outValueList) {
        try {
            Object v = closure.call(input, index)
            BigDecimalOperand operand
            if (v instanceof BigDecimalOperand) {
                operand = v
            } else {
                operand = new BigDecimalOperand(v)
            }

//        if (value.compareTo(BigDecimal.ZERO) <= 0) {
//            value = BigDecimal.ZERO
//        } else {
//            value = value.setScale(scale, RoundingMode.HALF_UP)
//        }
            itemInstance.setValue(operand)
            if (outValueList != null) {
                outValueList.add(operand)
            }
        }catch(Exception e){
            e.printStackTrace()
            System.out.println(name)
            throw new RuntimeException(e)
        }
    }
}
