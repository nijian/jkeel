package com.github.nijian.jkeel.algorithms.serration

import com.github.nijian.jkeel.algorithms.Config
import org.apache.commons.lang3.StringUtils

import javax.cache.Cache

class CalcConfig implements Config<Closure>, MixinFuncs {

    Cache<String, Closure> closureCache
    String cid

    @Override
    void init(Cache cache) {
        closureCache = cache
    }

    def cid(String cid) {
        this.cid = cid
    }

    def call(Closure closure) {
        closure.setDelegate(this)
        closure.setResolveStrategy(Closure.DELEGATE_ONLY)
        closure.call()
    }

    def Convert(Closure closure) {
        closureCache.put(StringUtils.joinWith(":", cid, Const.CONVERT), closure)
    }

    def ItemGroupCount(Closure closure) {
        closureCache.put(StringUtils.joinWith(":", cid, Const.ITEM_GROUP_COUNT), closure)
    }

    def LayoutCount(Closure closure) {
        closureCache.put(StringUtils.joinWith(":", cid, Const.LAYOUT_COUNT), closure)
    }

    def Output(Closure closure) {
        closureCache.put(StringUtils.joinWith(":", cid, Const.OUTPUT), closure)
    }

    def formula(String name, Closure closure) {
        closureCache.put(StringUtils.joinWith(":", cid, name), closure)
    }
}
