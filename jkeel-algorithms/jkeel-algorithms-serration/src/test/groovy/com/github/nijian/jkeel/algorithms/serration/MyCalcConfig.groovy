package com.github.nijian.jkeel.algorithms.serration

import javax.cache.Cache

class MyCalcConfig extends CalcConfig implements MyMixinFuncs {

    MyCalcConfig(Cache<String, Closure> cache) {
        super(cache)
    }
}
