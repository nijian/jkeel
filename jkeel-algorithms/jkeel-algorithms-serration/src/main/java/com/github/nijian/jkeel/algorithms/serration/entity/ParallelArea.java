package com.github.nijian.jkeel.algorithms.serration.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import groovy.lang.Closure;

import javax.cache.Cache;
import java.util.List;

/**
 * ParallelArea
 *
 * @author nj
 * @since 0.0.1
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ParallelArea {

    private String processorName;
    private List<Item> items;

    public void exec(final ParallelAreaInstance parallelAreaInstance, final Cache<String, Closure> closureCache) {
        if (processorName != null) {
            Closure closure = closureCache.get(processorName);
            if (closure != null) {
                closure.setDelegate(parallelAreaInstance);
                closure.setResolveStrategy(Closure.DELEGATE_ONLY);
                closure.call();
            }
        }
    }

    public String getProcessorName() {
        return processorName;
    }

    public void setProcessorName(String processorName) {
        this.processorName = processorName;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }
}
