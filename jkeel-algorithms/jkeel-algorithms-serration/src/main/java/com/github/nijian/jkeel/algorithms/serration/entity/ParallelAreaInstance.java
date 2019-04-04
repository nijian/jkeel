package com.github.nijian.jkeel.algorithms.serration.entity;

import java.util.List;

/**
 * ParallelAreaInstance
 *
 * @author nj
 * @since 0.0.1
 */
public class ParallelAreaInstance {

    /**
     * parallel area
     */
    private final ParallelArea parallelArea;

    /**
     * item instance anchor list
     */
    private List<ItemInstanceAnchor> itemInstanceAnchors;

    /**
     * Constructor
     *
     * @param parallelArea parallel area
     */
    ParallelAreaInstance(ParallelArea parallelArea) {
        this.parallelArea = parallelArea;
    }

    /**
     * Get parallel area
     *
     * @return parallel area
     */
    public ParallelArea getParallelArea() {
        return parallelArea;
    }

    /**
     * Get item instance anchor list
     *
     * @return item instance anchor list
     */
    public List<ItemInstanceAnchor> getItemInstanceAnchors() {
        return itemInstanceAnchors;
    }

    /**
     * Set item instance anchor list
     *
     * @param itemInstanceAnchors item instance anchor list
     */
    public void setItemInstanceAnchors(List<ItemInstanceAnchor> itemInstanceAnchors) {
        this.itemInstanceAnchors = itemInstanceAnchors;
    }
}
