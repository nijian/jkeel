package com.github.nijian.jkeel.algorithms.serration.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * Item instance anchor
 *
 * @author nj
 * @since 0.0.1
 */
public class ItemInstanceAnchor {

    /**
     * item instance list
     */
    private List<ItemInstance> itemInstances;

    /**
     * Constructor
     *
     * @param item     item
     * @param itemsLen item list size
     */
    public ItemInstanceAnchor(Item item, int itemsLen) {
        itemInstances = new ArrayList<>(itemsLen);
        for (int i = 0; i < itemsLen; i++) {
            itemInstances.add(new ItemInstance(item));
        }
    }

    /**
     * Get item instance list
     *
     * @return item instance list
     */
    public List<ItemInstance> getItemInstances() {
        return itemInstances;
    }

    /**
     * Set item instance list
     *
     * @param itemInstances item instance list
     */
    public void setItemInstances(List<ItemInstance> itemInstances) {
        this.itemInstances = itemInstances;
    }
}
