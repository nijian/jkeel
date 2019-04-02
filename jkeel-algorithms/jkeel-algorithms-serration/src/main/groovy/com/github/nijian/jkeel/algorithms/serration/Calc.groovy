package com.github.nijian.jkeel.algorithms.serration

import com.github.nijian.jkeel.algorithms.serration.entity.Context

class Calc implements MixinFuncs {

    Context<?> context

    Calc(Context<?> context) {
        this.context = context
    }
}
