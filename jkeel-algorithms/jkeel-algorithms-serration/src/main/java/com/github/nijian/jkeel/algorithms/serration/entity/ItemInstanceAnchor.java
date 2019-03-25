package com.github.nijian.jkeel.algorithms.serration.entity;

import java.util.ArrayList;
import java.util.List;

public class ItemInstanceAnchor {

    private List<ItemInstance> itemInstances;

    public ItemInstanceAnchor(Item item, int itemsLen) {
        itemInstances = new ArrayList<>(itemsLen);
        for (int i = 0; i < itemsLen; i++) {
            itemInstances.add(new ItemInstance(item));
        }
    }

    public List<ItemInstance> getItemInstances() {
        return itemInstances;
    }

    public void setItemInstances(List<ItemInstance> itemInstances) {
        this.itemInstances = itemInstances;
    }
}
