package com.github.nijian.jkeel.algorithms.serration.entity


/**
 * DO NOT CHANGE THIS CLASS UNLESS YOU ARE CLEAR WHAT EXACT IMPACT FOR PERFORMANCE!!!
 *
 * Created by johnson.ni
 */
final class ItemInstanceAnchor {
    List<ItemInstance> itemInstances

    ItemInstanceAnchor(Item item, int itemsLen) {
        itemInstances = new ArrayList<ItemInstance>(itemsLen)
        for (int i = 0; i < itemsLen; i++) {
            itemInstances.add(new ItemInstance(item))
        }
    }
}
