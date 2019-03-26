package com.github.nijian.jkeel.algorithms.serration.entity

import com.github.nijian.jkeel.algorithms.serration.Const
import com.github.nijian.jkeel.algorithms.serration.MixinFuncs

import javax.cache.Cache

final class LayoutTemplateInstance<I> implements MixinFuncs {

    Context<I> context
    List<LayoutInstance> layoutInstances = new ArrayList<LayoutInstance>()

    LayoutTemplateInstance(Context<?> context) {
        this.context = context
    }

    void exec(Cache<String, Closure> closureCache) {
        Closure closure = closureCache.get(Const.OUTPUT)
        if (closure != null) {
            closure.setDelegate(this)
            closure.setResolveStrategy(Closure.DELEGATE_ONLY)
            closure.call(context.input)
        }
    }
}
