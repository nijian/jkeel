package com.github.nijian.jkeel.algorithms.serration.entity

import com.github.nijian.jkeel.algorithms.serration.MixinFuncs


/**
 * DO NOT CHANGE THIS CLASS UNLESS YOU ARE CLEAR WHAT EXACT IMPACT FOR PERFORMANCE!!!
 *
 * Created by johnson.ni
 */
final class ParallelAreaInstance implements MixinFuncs {
    ParallelArea parallelArea
    List<ItemInstanceAnchor> itemInstanceAnchors

    ParallelAreaInstance(ParallelArea parallelArea) {
        this.parallelArea = parallelArea
    }
}
