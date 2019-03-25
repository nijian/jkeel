package com.github.nijian.jkeel.algorithms.serration.entity;

import java.util.List;

public class ParallelAreaInstance {

    private final ParallelArea parallelArea;
    private List<ItemInstanceAnchor> itemInstanceAnchors;

    ParallelAreaInstance(ParallelArea parallelArea) {
        this.parallelArea = parallelArea;
    }

    public ParallelArea getParallelArea() {
        return parallelArea;
    }

    public List<ItemInstanceAnchor> getItemInstanceAnchors() {
        return itemInstanceAnchors;
    }

    public void setItemInstanceAnchors(List<ItemInstanceAnchor> itemInstanceAnchors) {
        this.itemInstanceAnchors = itemInstanceAnchors;
    }
}
