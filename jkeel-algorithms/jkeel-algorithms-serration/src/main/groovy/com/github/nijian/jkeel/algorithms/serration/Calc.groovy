package com.github.nijian.jkeel.algorithms.serration

import com.github.nijian.jkeel.algorithms.serration.entity.Context

/**
 * Bridge Groovy formula and Java implementation
 *
 * @author nj
 * @since 0.0.1
 */
class Calc implements MixinFuncs {

    Context<?> context

    Calc(Context<?> context) {
        this.context = context
    }
}
