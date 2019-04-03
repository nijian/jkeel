package com.github.nijian.jkeel.algorithms.serration.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import groovy.lang.Closure;

import javax.cache.Cache;
import java.util.List;
import java.util.Map;

/**
 * Layout
 *
 * @author nj
 * @since 0.0.1
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Layout {

    private String layoutCount;
    private String itemGroupCount;
    private boolean parallel = false;
    private List<ParallelArea> parallelAreas;
    private String strategyName;

    public void exec(final LayoutInstance layoutInstance, final Map<String, Closure> closureMap) {
        if (strategyName != null) {
            Closure closure = closureMap.get(strategyName);
            if (closure != null) {
                closure.setDelegate(layoutInstance);
                closure.setResolveStrategy(Closure.DELEGATE_ONLY);
                closure.call();
            }
        }
    }

    public String getLayoutCount() {
        return layoutCount;
    }

    public void setLayoutCount(String layoutCount) {
        this.layoutCount = layoutCount;
    }

    public String getItemGroupCount() {
        return itemGroupCount;
    }

    public void setItemGroupCount(String itemGroupCount) {
        this.itemGroupCount = itemGroupCount;
    }

    public boolean isParallel() {
        return parallel;
    }

    public void setParallel(boolean parallel) {
        this.parallel = parallel;
    }

    public List<ParallelArea> getParallelAreas() {
        return parallelAreas;
    }

    public void setParallelAreas(List<ParallelArea> parallelAreas) {
        this.parallelAreas = parallelAreas;
    }

    public String getStrategyName() {
        return strategyName;
    }

    public void setStrategyName(String strategyName) {
        this.strategyName = strategyName;
    }
}
