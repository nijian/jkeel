package com.github.nijian.jkeel.algorithms.serration.entity;

import groovy.lang.Closure;

import java.util.List;
import java.util.Map;

/**
 * Layout instance
 *
 * @author nj
 * @since 0.0.1
 */
public class LayoutInstance {

    private final Layout lout;
    private int itemCount = 1;
    private int loutCount = 1;
    private int index = 0;
    private final LayoutOutputInstance layoutOutputInstance = new LayoutOutputInstance();
    private List<ParallelAreaInstance> parallelAreaInstances;

    /**
     * Constructor
     *
     * @param context context
     * @param layout layout
     * @param closureMap closure map
     * @param calcConfig calc config
     */
    public LayoutInstance(Context<?> context, Layout layout, Map<String, Closure> closureMap, Object calcConfig) {
        this.lout = layout;
        String itemGroupName = layout.getItemCountName();
        String loutCountName = layout.getLoutCountName();
        if (itemGroupName != null) {
            Closure closure = closureMap.get(itemGroupName);
            if (closure != null) {
                closure.setDelegate(calcConfig);
                closure.setResolveStrategy(Closure.DELEGATE_ONLY);
                this.itemCount = (int) closure.call(context.getInput());
            }
        }

        if (loutCountName != null) {
            Closure closure = closureMap.get(loutCountName);
            if (closure != null) {
                closure.setDelegate(calcConfig);
                closure.setResolveStrategy(Closure.DELEGATE_ONLY);
                this.loutCount = (int) closure.call(context.getInput());
            }
        }
    }

    public Layout getLout() {
        return lout;
    }

    public int getItemCount() {
        return itemCount;
    }

    public int getLoutCount() {
        return loutCount;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public LayoutOutputInstance getLayoutOutputInstance() {
        return layoutOutputInstance;
    }

    public List<ParallelAreaInstance> getParallelAreaInstances() {
        return parallelAreaInstances;
    }

    public void setParallelAreaInstances(List<ParallelAreaInstance> parallelAreaInstances) {
        this.parallelAreaInstances = parallelAreaInstances;
    }
}
