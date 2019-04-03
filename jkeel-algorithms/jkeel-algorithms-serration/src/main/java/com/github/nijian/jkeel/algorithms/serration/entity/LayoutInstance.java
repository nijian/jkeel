package com.github.nijian.jkeel.algorithms.serration.entity;

import groovy.lang.Closure;

import javax.cache.Cache;
import java.util.List;
import java.util.Map;

public class LayoutInstance {

    private Layout layout;
    private int itemGroupCount = 1;
    private int layoutCount = 1;
    private int LayoutIndex = 0;
    private LayoutOutputInstance layoutOutputInstance = new LayoutOutputInstance();
    private List<ParallelAreaInstance> parallelAreaInstances;

    public LayoutInstance(Context<?> context, Layout layout, Map<String, Closure> closureMap, Object calcConfig) {
        this.layout = layout;
        String itemGroupName = layout.getItemGroupCount();
        String loutCountName = layout.getLayoutCount();
        if (itemGroupName != null) {
            Closure closure = closureMap.get(itemGroupName);
            if (closure != null) {
                closure.setDelegate(calcConfig);
                closure.setResolveStrategy(Closure.DELEGATE_ONLY);
                this.itemGroupCount = (int) closure.call(context.getInput());
            }
        }

        if (loutCountName != null) {
            Closure closure = closureMap.get(loutCountName);
            if (closure != null) {
                closure.setDelegate(calcConfig);
                closure.setResolveStrategy(Closure.DELEGATE_ONLY);
                this.layoutCount = (int) closure.call(context.getInput());
            }
        }
    }

    public Layout getLayout() {
        return layout;
    }

    public void setLayout(Layout layout) {
        this.layout = layout;
    }

    public int getItemGroupCount() {
        return itemGroupCount;
    }

    public void setItemGroupCount(int itemGroupCount) {
        this.itemGroupCount = itemGroupCount;
    }

    public int getLayoutCount() {
        return layoutCount;
    }

    public void setLayoutCount(int layoutCount) {
        this.layoutCount = layoutCount;
    }

    public int getLayoutIndex() {
        return LayoutIndex;
    }

    public void setLayoutIndex(int layoutIndex) {
        LayoutIndex = layoutIndex;
    }

    public LayoutOutputInstance getLayoutOutputInstance() {
        return layoutOutputInstance;
    }

    public void setLayoutOutputInstance(LayoutOutputInstance layoutOutputInstance) {
        this.layoutOutputInstance = layoutOutputInstance;
    }

    public List<ParallelAreaInstance> getParallelAreaInstances() {
        return parallelAreaInstances;
    }

    public void setParallelAreaInstances(List<ParallelAreaInstance> parallelAreaInstances) {
        this.parallelAreaInstances = parallelAreaInstances;
    }
}
