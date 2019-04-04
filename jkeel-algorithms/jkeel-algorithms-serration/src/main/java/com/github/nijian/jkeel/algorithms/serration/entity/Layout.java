package com.github.nijian.jkeel.algorithms.serration.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import groovy.lang.Closure;

import java.util.List;
import java.util.Map;

/**
 * Layout config
 *
 * @author nj
 * @since 0.0.1
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Layout {

    /**
     * layout count name
     */
    private String loutCountName;

    /**
     * item count name
     */
    private String itemCountName;

    /**
     * is parallel calculation or not
     */
    private boolean parallel = false;

    /**
     * parallel area list
     */
    private List<ParallelArea> parallelAreas;

    /**
     * strategy name
     */
    private String strategyName;

    public void exec(final LayoutInstance layoutInstance, final Map<String, Closure> closureMap, Object calc) {
        if (strategyName != null) {
            Closure closure = closureMap.get(strategyName);
            if (closure != null) {
                closure.setDelegate(calc);
                closure.setResolveStrategy(Closure.DELEGATE_ONLY);
                closure.call();
            }
        }
    }

    /**
     * Get layout count name
     *
     * @return layout count name
     */
    public String getLoutCountName() {
        return loutCountName;
    }

    /**
     * Set layout count name
     *
     * @param loutCountName layout count name
     */
    public void setLoutCountName(String loutCountName) {
        this.loutCountName = loutCountName;
    }

    /**
     * Get item count name
     *
     * @return item count name
     */
    public String getItemCountName() {
        return itemCountName;
    }

    /**
     * Set item count name
     *
     * @param itemCountName item count name
     */
    public void setItemCountName(String itemCountName) {
        this.itemCountName = itemCountName;
    }

    /**
     * is parallel calculation or not
     *
     * @return true or false
     */
    public boolean isParallel() {
        return parallel;
    }

    /**
     * is parallel calculation or not
     *
     * @param parallel true or false
     */
    public void setParallel(boolean parallel) {
        this.parallel = parallel;
    }

    /**
     * Get parallel area list
     *
     * @return parallel area list
     */
    public List<ParallelArea> getParallelAreas() {
        return parallelAreas;
    }

    /**
     * Set parallel area list
     *
     * @param parallelAreas parallel area list
     */
    public void setParallelAreas(List<ParallelArea> parallelAreas) {
        this.parallelAreas = parallelAreas;
    }

    /**
     * Get strategy name
     *
     * @return strategy name
     */
    public String getStrategyName() {
        return strategyName;
    }

    /**
     * Set strategy name
     *
     * @param strategyName strategy name
     */
    public void setStrategyName(String strategyName) {
        this.strategyName = strategyName;
    }
}
