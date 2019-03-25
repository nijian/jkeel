package com.github.nijian.jkeel.algorithms.serration.entity

import com.github.nijian.jkeel.algorithms.AlgorithmContext
import com.github.nijian.jkeel.algorithms.serration.Const
import com.github.nijian.jkeel.algorithms.serration.MixinFuncs
import org.apache.commons.lang3.StringUtils

import javax.cache.Cache

/**
 *
 * One context instance per one time calculation
 *
 * Created by johnson.ni
 *
 * @param < I >       type of input
 */
final class Context<I> implements MixinFuncs {

    I input
    Map<String, List<BigDecimal>> outputMap
    List<LayoutInstance> layoutInstances
    Map<String, ItemInstanceAnchor> itemLocationMap
    Map<String, LayoutOutputInstance> itemOutMap

    Context(I input, AlgorithmContext<Closure> ac) {

        this.input = input
        this.outputMap = new HashMap<String, List<BigDecimal>>()
        this.itemLocationMap = new HashMap<String, ItemInstanceAnchor>()
        this.itemOutMap = new HashMap<String, LayoutOutputInstance>()
        this.layoutInstances = new ArrayList<LayoutInstance>()

        ContextHelper.populate(ac, this, layoutInstances, itemOutMap, itemLocationMap, outputMap)
    }

    void exec(String cid, Cache<String, Closure> closureCache) {
        Closure closure = closureCache.get(StringUtils.joinWith(':', cid, Const.OUTPUT))
        if (closure != null) {
            closure.setDelegate(this)
            closure.setResolveStrategy(Closure.DELEGATE_ONLY)
            closure.call(input)
        }
    }
}
