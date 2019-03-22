package com.github.nijian.jkeel.algorithms.serration.entity

import com.github.nijian.jkeel.algorithms.serration.operands.BigDecimalOperand


/**
 * DO NOT CHANGE THIS CLASS UNLESS YOU ARE CLEAR WHAT EXACT IMPACT FOR PERFORMANCE!!!
 *
 * Created by johnson.ni
 */
final class ItemInstance {
    int index
    Item item
    BigDecimalOperand value

    ItemInstance(Item item) {
        this.item = item
    }
}
