package com.github.nijian.jkeel.algorithms.serration

import javax.cache.Cache

class MyCalcConfig extends CalcConfig{

    String test

    MyCalcConfig(Cache<String, Closure> cache) {
        super(cache)
    }

    def test(String test) {
        this.test = test
        System.out.println(test)
    }
}
