package com.github.nijian.jkeel.algorithms.serration.entity

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

import javax.cache.Cache

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

    void exec(String cid, LayoutInstance layoutInstance, Cache closureCache) {
        if (cid != null && strategyName != null) {
            Closure closure = closureCache.get(strategyName)
            if (closure != null) {
                closure.setDelegate(layoutInstance)
                closure.setResolveStrategy(Closure.DELEGATE_ONLY)
                closure.call()
            }
        }
    }
}
