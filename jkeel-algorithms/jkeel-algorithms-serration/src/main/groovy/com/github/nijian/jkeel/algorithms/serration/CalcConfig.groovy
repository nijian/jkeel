package com.github.nijian.jkeel.algorithms.serration

import javax.cache.Cache

class CalcConfig implements MixinFuncs {

    Cache<String, Closure> cache
    String cid

    CalcConfig(Cache<String, Closure> cache){
        this.cache = cache
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
        cache.put(Const.CONVERT, closure)
    }

    def Output(Closure closure) {
        cache.put(Const.OUTPUT, closure)
    }

    def itemGroupCount(String name, Closure<Integer> closure) {
        cache.put(name, closure)
    }

    def layoutCount(String name, Closure<Integer> closure) {
        cache.put(name, closure)
    }

    def formula(String name, Closure<BigDecimal> closure) {
        cache.put(name, closure)
    }
}
