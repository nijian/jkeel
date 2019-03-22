package com.github.nijian.jkeel.algorithms.serration.entity


import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.github.nijian.jkeel.algorithms.serration.CalcCache

/**
 * DO NOT CHANGE THIS CLASS UNLESS YOU ARE CLEAR WHAT EXACT IMPACT FOR PERFORMANCE!!!
 *
 * Created by johnson.ni
 */
@JsonIgnoreProperties(ignoreUnknown = true)
final class Layout {
    String layoutCount
    String itemGroupCount
    boolean parallel = false
    List<ParallelArea> parallelAreas
    String strategyName
    String outputNames

    void exec(String region, LayoutInstance layoutInstance) {
        if (region != null && strategyName != null) {
            Closure closure = CalcCache.get(region, strategyName)
            if (closure != null) {
                closure.setDelegate(layoutInstance)
                closure.setResolveStrategy(Closure.DELEGATE_ONLY)
                closure.call()
            }
        }
    }
}
