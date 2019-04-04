package com.github.nijian.jkeel.algorithms.serration.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import groovy.lang.Closure;

import java.util.List;
import java.util.Map;

/**
 * ParallelArea
 *
 * @author nj
 * @since 0.0.1
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ParallelArea {

    /**
     * Processor name
     */
    private String processorName;

    /**
     * Item list
     */
    private List<Item> items;

    public void exec(final ParallelAreaInstance parallelAreaInstance, final Map<String, Closure> closureMap, Object calc) {
        if (processorName != null) {
            Closure closure = closureMap.get(processorName);
            if (closure != null) {
                closure.setDelegate(calc);
                closure.setResolveStrategy(Closure.DELEGATE_ONLY);
                closure.call();
            }
        }
    }

    /**
     * Get processor name
     *
     * @return processor name
     */
    public String getProcessorName() {
        return processorName;
    }

    /**
     * Set processor name
     *
     * @param processorName processor name
     */
    public void setProcessorName(String processorName) {
        this.processorName = processorName;
    }

    /**
     * Get item list
     *
     * @return item list
     */
    public List<Item> getItems() {
        return items;
    }

    /**
     * Set item list
     *
     * @param items item list
     */
    public void setItems(List<Item> items) {
        this.items = items;
    }
}
