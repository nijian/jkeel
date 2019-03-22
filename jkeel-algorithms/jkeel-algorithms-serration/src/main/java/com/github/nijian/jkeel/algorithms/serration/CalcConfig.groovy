package com.github.nijian.jkeel.algorithms.serration


class CalcConfig implements MixinFuncs {

    String cid

    def cid(String cid) {
        this.cid = cid
    }

    def call(Closure closure) {
        closure.setDelegate(this)
        closure.setResolveStrategy(Closure.DELEGATE_ONLY)
        closure.call()
    }

    def Convert(Closure closure) {
        CalcCache.put(cid, Const.CONVERT, closure)
    }

    def ItemGroupCount(Closure closure) {
        CalcCache.put(cid, Const.ITEM_GROUP_COUNT, closure)
    }

    def LayoutCount(Closure closure) {
        CalcCache.put(cid, Const.LAYOUT_COUNT, closure)
    }

    def strategy(String name, Closure closure) {
        CalcCache.put(cid, name, closure)
    }

    def formula(String name, Closure closure) {
        CalcCache.put(cid, name, closure)
    }

}
