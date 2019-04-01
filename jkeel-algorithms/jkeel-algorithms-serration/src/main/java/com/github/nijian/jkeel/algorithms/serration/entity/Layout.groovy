package com.github.nijian.jkeel.algorithms.serration.entity

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

import javax.cache.Cache

/**
 * Layout
 *
 * @author nj
 * @since 0.0.1
 */
@JsonIgnoreProperties(ignoreUnknown = true)
final class Layout {

    String layoutCount
    String itemGroupCount
    boolean parallel = false
    List<ParallelArea> parallelAreas
    String strategyName

    void exec(final LayoutInstance layoutInstance, final Cache<String, Closure> closureCache) {
        if (strategyName != null) {
            Closure<String, Closure> closure = closureCache.get(strategyName)
            if (closure != null) {
                closure.setDelegate(layoutInstance)
                closure.setResolveStrategy(Closure.DELEGATE_ONLY)
                closure.call()
            }
        }
    }
}
