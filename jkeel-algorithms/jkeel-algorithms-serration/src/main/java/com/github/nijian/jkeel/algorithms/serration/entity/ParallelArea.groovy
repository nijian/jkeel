package com.github.nijian.jkeel.algorithms.serration.entity


import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.github.nijian.jkeel.algorithms.serration.CalcCache

/**
 * DO NOT CHANGE THIS CLASS UNLESS YOU ARE CLEAR WHAT EXACT IMPACT FOR PERFORMANCE!!!
 *
 * Created by johnson.ni
 */
@JsonIgnoreProperties(ignoreUnknown = true)
final class ParallelArea {
    String processorName
    List<Item> items

    void exec(String region, ParallelAreaInstance parallelAreaInstance) {
        if (region != null && processorName != null) {
            Closure closure = CalcCache.get(region, processorName)
            if (closure != null) {
                closure.setDelegate(parallelAreaInstance)
                closure.setResolveStrategy(Closure.DELEGATE_ONLY)
                closure.call()
            }
        }
    }
}
