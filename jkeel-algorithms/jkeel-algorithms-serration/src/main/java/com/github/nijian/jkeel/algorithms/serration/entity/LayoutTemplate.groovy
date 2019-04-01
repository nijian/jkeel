package com.github.nijian.jkeel.algorithms.serration.entity

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.github.nijian.jkeel.algorithms.AlgorithmTemplate
import com.github.nijian.jkeel.algorithms.serration.Const

import javax.cache.Cache

@JsonIgnoreProperties(ignoreUnknown = true)
class LayoutTemplate<I> implements AlgorithmTemplate {

    String cid
    String outputFields
    List<Layout> layouts

    void exec(I input, LayoutTemplateInstance layoutTemplateInstance, Cache<String, Closure> closureCache) {
        Closure closure = closureCache.get(Const.OUTPUT)
        if (closure != null) {
            closure.setDelegate(layoutTemplateInstance)
            closure.setResolveStrategy(Closure.DELEGATE_ONLY)
            closure.call(input)
        }
    }
}
