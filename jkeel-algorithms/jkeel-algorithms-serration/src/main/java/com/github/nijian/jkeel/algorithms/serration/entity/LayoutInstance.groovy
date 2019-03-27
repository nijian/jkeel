package com.github.nijian.jkeel.algorithms.serration.entity

import com.github.nijian.jkeel.algorithms.serration.MixinFuncs
import org.apache.commons.lang3.StringUtils

import javax.cache.Cache

/**
 * DO NOT CHANGE THIS CLASS UNLESS YOU ARE CLEAR WHAT EXACT IMPACT FOR PERFORMANCE!!!
 *
 * Created by johnson.ni
 */
final class LayoutInstance implements MixinFuncs {
    Context<?> context
    Layout layout
    int itemGroupCount = 1
    int layoutCount = 1
    int LayoutIndex = 0
    LayoutOutputInstance layoutOutputInstance = new LayoutOutputInstance()
    List<ParallelAreaInstance> parallelAreaInstances

    LayoutInstance(Context<?> context, Layout layout, Cache closureCache) {
        this.context = context
        this.layout = layout

        if (layout.itemGroupCount != null) {
            Closure closure = closureCache.get(layout.itemGroupCount)
            if (closure != null) {
                closure.setDelegate(this)
                closure.setResolveStrategy(Closure.DELEGATE_ONLY)
                this.itemGroupCount = closure.call(context.input)
            }
        }

        if (layout.layoutCount != null) {
            Closure closure = closureCache.get(layout.layoutCount)
            if (closure != null) {
                closure.setDelegate(this)
                closure.setResolveStrategy(Closure.DELEGATE_ONLY)
                this.layoutCount = closure.call(context.input)
            }
        }
    }
}