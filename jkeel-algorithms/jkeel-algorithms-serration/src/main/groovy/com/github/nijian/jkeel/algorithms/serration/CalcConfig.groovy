package com.github.nijian.jkeel.algorithms.serration

import javax.cache.Cache

/**
 * Stands for Algorithm Config in Groovy.
 *
 * @author nj
 * @since 0.0.1
 */
class CalcConfig extends AbstractCalcConfig implements MixinFuncs {

    Cache<String, Closure> cache
    String cid
    boolean negToZero = false

    CalcConfig(Cache<String, Closure> cache) {
        this.cache = cache
    }

    def cid(String cid) {
        this.cid = cid
    }

    def negToZero(boolean isNegToZero) {
        this.negToZero = isNegToZero
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

    def itemCount(String name, Closure<Integer> closure) {
        cache.put(name, closure)
    }

    def loutCount(String name, Closure<Integer> closure) {
        cache.put(name, closure)
    }

    def formula(String name, Closure<BigDecimal> closure) {
        cache.put(name, closure)
    }

    @Override
    boolean isNegToZero() {
        return negToZero
    }
}
