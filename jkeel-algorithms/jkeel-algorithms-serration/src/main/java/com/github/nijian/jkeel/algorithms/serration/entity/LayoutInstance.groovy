package com.github.nijian.jkeel.algorithms.serration.entity

import com.ebao.insuremo.algorithms.serrationparallel.CalcCache
import com.github.nijian.jkeel.algorithms.serration.CalcCache
import com.github.nijian.jkeel.algorithms.serration.MixinFuncs


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
    LayoutOutputInstance layoutOutputInstance
    List<ParallelAreaInstance> parallelAreaInstances

    LayoutInstance(Context<?> context, String region, Layout layout) {
        this.context = context
        this.layout = layout
        this.layoutOutputInstance = new LayoutOutputInstance()

        if (layout.itemGroupCount != null) {
            Closure closure = CalcCache.get(region, layout.itemGroupCount)
            if (closure != null) {
                closure.setDelegate(this)
                closure.setResolveStrategy(Closure.DELEGATE_ONLY)
                this.itemGroupCount = closure.call(context.input)
            }
        }

        if (layout.layoutCount != null) {
            Closure closure = CalcCache.get(region, layout.layoutCount)
            if (closure != null) {
                closure.setDelegate(this)
                closure.setResolveStrategy(Closure.DELEGATE_ONLY)
                this.layoutCount = closure.call(context.input)
            }
        }
    }
}