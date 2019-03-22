package com.github.nijian.jkeel.algorithms.serration.entity

import com.github.nijian.jkeel.algorithms.serration.operands.BigDecimalOperand


/**
 * DO NOT CHANGE THIS CLASS UNLESS YOU ARE CLEAR WHAT EXACT IMPACT FOR PERFORMANCE!!!
 *
 * Created by johnson.ni
 */
final class LayoutOutputInstance {
    Map<String, List<BigDecimalOperand>> map

    LayoutOutputInstance() {
        map = new HashMap<String, List<BigDecimalOperand>>()
    }
}
