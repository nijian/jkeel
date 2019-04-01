package com.github.nijian.jkeel.algorithms.serration.entity

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

import javax.cache.Cache

/**
 * ParallelArea
 *
 * @author nj
 * @since 0.0.1
 */
@JsonIgnoreProperties(ignoreUnknown = true)
final class ParallelArea {

    String processorName
    List<Item> items

    void exec(final ParallelAreaInstance parallelAreaInstance, final Cache<String, Closure> closureCache) {
        if (processorName != null) {
            Closure closure = closureCache.get(processorName)
            if (closure != null) {
                closure.setDelegate(parallelAreaInstance)
                closure.setResolveStrategy(Closure.DELEGATE_ONLY)
                closure.call()
            }
        }
    }
}
