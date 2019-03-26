package com.github.nijian.jkeel.algorithms.serration.entity

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

import javax.cache.Cache

/**
 * DO NOT CHANGE THIS CLASS UNLESS YOU ARE CLEAR WHAT EXACT IMPACT FOR PERFORMANCE!!!
 *
 * Created by johnson.ni
 */
@JsonIgnoreProperties(ignoreUnknown = true)
final class ParallelArea {
    String processorName
    List<Item> items

    void exec(String cid, ParallelAreaInstance parallelAreaInstance, Cache closureCache) {
        if (cid != null && processorName != null) {
            Closure closure = closureCache.get(processorName)
            if (closure != null) {
                closure.setDelegate(parallelAreaInstance)
                closure.setResolveStrategy(Closure.DELEGATE_ONLY)
                closure.call()
            }
        }
    }
}
