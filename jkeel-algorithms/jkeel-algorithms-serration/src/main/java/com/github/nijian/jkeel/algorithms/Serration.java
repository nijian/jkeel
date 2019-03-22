package com.github.nijian.jkeel.algorithms;

import com.github.nijian.jkeel.algorithms.serration.CalcCache;
import com.github.nijian.jkeel.algorithms.serration.Const;
import com.github.nijian.jkeel.algorithms.serration.entity.*;
import groovy.lang.Closure;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.cache.Cache;
import java.util.HashMap;
import java.util.Map;

/**
 * DO NOT CHANGE THIS CLASS UNLESS YOU ARE CLEAR WHAT EXACT IMPACT FOR PERFORMANCE!!!
 * <p>
 * Created by johnson.ni
 */
public final class Serration<I> extends Algorithm<I, Context<I>> {

    private static Logger logger = LoggerFactory.getLogger(Serration.class);

    private Cache<String, Closure> closureCache;

    @Override
    public String getName() {
        return getClass().getName();
    }

    @Override
    public Context<I> calc(I rawInput, Map<String, ?> var, AlgorithmContext ac) {

        if (closureCache == null) {
            closureCache = ac.getCacheManager().getCache("closure-cache", String.class, Closure.class);
        }

        //convert raw input to required format
        final Map<String, ?> input = new HashMap();
        Closure closure = closureCache.get(StringUtils.join(ac.getTemplate().getName(), Const.CONVERT));
        closure.call(rawInput, var, input);

        Template template = ac.getTemplate();
        Context<I> context = new Context(input, ac);
        for (LayoutInstance layoutInstance : context.getLayoutInstances()) {
            Layout layout = layoutInstance.getLayout();
            int layoutCount = layoutInstance.getLayoutCount();
            int layoutIndex = layoutInstance.getLayoutIndex();
            while (layoutIndex < layoutCount) {
                for (ParallelAreaInstance parallelAreaInstance : layoutInstance.getParallelAreaInstances()) {
                    if (layout.isParallel()) {
                        parallelAreaInstance.getItemInstanceAnchors().parallelStream().forEach(
                                itemInstanceAnchor ->
                                        itemInstanceAnchor.getItemInstances().parallelStream().forEach(
                                                itemInstance ->
                                                {
                                                    calc(closureCache, context, input, template.getName(), layoutInstance, itemInstance);
                                                }));
                    } else {
                        parallelAreaInstance.getItemInstanceAnchors().stream().forEach(
                                itemInstanceAnchor ->
                                        itemInstanceAnchor.getItemInstances().stream().forEach(
                                                itemInstance ->
                                                {
                                                    calc(closureCache, context, input, template.getName(), layoutInstance, itemInstance);
                                                }));
                    }
                    parallelAreaInstance.getParallelArea().exec(template.getName(), parallelAreaInstance);
                }
                layout.exec(template.getName(), layoutInstance);
                layoutIndex++;
                layoutInstance.setLayoutIndex(layoutIndex);
            }
        }
        context.exec(template.getName());
//        context.clear();
        return context;
    }

    @Override
    protected Map<String, ?> convertInput(I rawInput, Map<String, ?> var) {
        return null;
    }

    private void calc(Cache<String, Closure> closureCache, Context<I> context, Map<String, ?> input, String layoutTemplateName, LayoutInstance layoutInstance, ItemInstance itemInstance) {
        Closure closure = closureCache.get(layoutTemplateName + itemInstance.getItem().getName());
        if (closure != null) {
            closure.setDelegate(layoutInstance);
            closure.setResolveStrategy(Closure.DELEGATE_ONLY);
            if (itemInstance.getItem().getOut()) {
                itemInstance.getItem().calc(input, closure, itemInstance, itemInstance.getIndex(), context.getItemOutMap().get(itemInstance.getItem().getName()).getMap().get(itemInstance.getItem().getName()));
            } else {
                itemInstance.getItem().calc(input, closure, itemInstance, itemInstance.getIndex(), null);
            }
        }
    }
}
