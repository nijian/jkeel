package com.github.nijian.jkeel.algorithms.serration

import com.github.nijian.jkeel.algorithms.serration.entity.Context

class MyCalc extends Calc implements MyMixinFuncs{

    MyCalc(Context<?> context) {
        super(context)
    }
}
