package com.github.nijian.jkeel.algorithms.serration.entity

import com.fasterxml.jackson.annotation.JsonIgnore
import com.github.nijian.jkeel.algorithms.AlgorithmContext
import com.github.nijian.jkeel.algorithms.Template
import com.github.nijian.jkeel.algorithms.serration.CalcCache
import com.github.nijian.jkeel.algorithms.serration.MixinFuncs


/**
 * DO NOT CHANGE THIS CLASS UNLESS YOU ARE CLEAR WHAT EXACT IMPACT FOR PERFORMANCE!!!
 *
 * One context instance per one time calculation
 *
 * @param < I >           type of input
 *
 * Created by johnson.ni
 */
final class Context<I> implements MixinFuncs {
    I input
    LayoutTemplate layoutTemplate
    @JsonIgnore
    Map<String, List<BigDecimal>> outputMap
    List<LayoutInstance> layoutInstances
    Map<String, ItemInstanceAnchor> itemLocationMap
    Map<String, LayoutOutputInstance> itemOutMap

    Context(I input, AlgorithmContext ac) {
        this.input = input
        this.layoutTemplate = ac.getTemplate()
        this.outputMap = new HashMap<String, List<BigDecimal>>()
        this.itemLocationMap = new HashMap<String, ItemInstanceAnchor>()
        this.itemOutMap = new HashMap<String, LayoutOutputInstance>()
        layoutInstances = new ArrayList<LayoutInstance>(layoutTemplate.layouts.size())
        layoutTemplate.layouts.each { layout ->
            LayoutInstance layoutInstance = new LayoutInstance(this, layoutTemplate.name, layout)
            layoutInstances.add(layoutInstance)
            layoutInstance.parallelAreaInstances = new ArrayList<ParallelAreaInstance>(layout.parallelAreas.size())
            layout.parallelAreas.each { parallelArea ->
                ParallelAreaInstance parallelAreaInstance = new ParallelAreaInstance(parallelArea)
                layoutInstance.parallelAreaInstances.add(parallelAreaInstance)
                parallelAreaInstance.itemInstanceAnchors = new ArrayList<ItemInstanceAnchor>(parallelArea.items.size())
                parallelArea.items.each { item ->
                    if (item.out) {
                        itemOutMap.put(item.name, layoutInstance.layoutOutputInstance)
                        layoutInstance.layoutOutputInstance.map.put(item.name, new ArrayList<BigDecimal>())
                    }
                    ItemInstanceAnchor itemInstanceAnchor = new ItemInstanceAnchor(item, layoutInstance.itemGroupCount)
                    itemLocationMap.put(item.name, itemInstanceAnchor)
                    parallelAreaInstance.itemInstanceAnchors.add(itemInstanceAnchor)
                    itemInstanceAnchor.itemInstances.eachWithIndex { ItemInstance itemInstance, int m ->
                        itemInstance.index = m
                    }
                }
            }
        }

        if (layoutTemplate.outputNames != null) {
            String[] outputNameArray = layoutTemplate.outputNames.split(',')
            outputNameArray.each { outputName -> outputMap.put(outputName, new ArrayList<BigDecimal>()) }
        }
    }

    void exec(String region) {
        if (region != null && layoutTemplate.strategyName != null) {
            Closure closure = CalcCache.get(region, layoutTemplate.strategyName)
            if (closure != null) {
                closure.setDelegate(this)
                closure.setResolveStrategy(Closure.DELEGATE_ONLY)
                closure.call(input)
            }
        }
    }

    void clear() {
        this.input = null
        this.layoutTemplate = null
        this.layoutInstances = null
        this.itemLocationMap = null
        this.itemOutMap = null
    }
}
