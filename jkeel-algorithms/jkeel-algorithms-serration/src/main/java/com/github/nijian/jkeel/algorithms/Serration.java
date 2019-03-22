package com.github.nijian.jkeel.algorithms;


import com.github.nijian.jkeel.algorithms.serration.CalcCache;
import com.github.nijian.jkeel.algorithms.serration.Const;
import com.github.nijian.jkeel.algorithms.serration.entity.*;
import groovy.lang.Closure;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * DO NOT CHANGE THIS CLASS UNLESS YOU ARE CLEAR WHAT EXACT IMPACT FOR PERFORMANCE!!!
 * <p>
 * Created by johnson.ni
 */
public final class Serration<I> {

    Logger logger = LoggerFactory.getLogger(Serration.class);

    public Context<I> perform(I rawInput, LayoutTemplate layoutTemplate) {
        return perform(rawInput, null, layoutTemplate);
    }

    public Context<I> perform(I rawInput, Map<?, ?> var, LayoutTemplate layoutTemplate) {
        Map<String, ?> input = new HashMap();
        Closure closure = CalcCache.get(layoutTemplate.getName(), Const.CONVERT);
        logger.info("get convert closure...");
        closure.call(rawInput, var, input);

        Context<I> context = new Context(input, layoutTemplate);
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
                                                    calc(context, input, layoutTemplate.getName(), layoutInstance, itemInstance);
                                                }));
                    } else {
                        parallelAreaInstance.getItemInstanceAnchors().stream().forEach(
                                itemInstanceAnchor ->
                                        itemInstanceAnchor.getItemInstances().stream().forEach(
                                                itemInstance ->
                                                {
                                                    calc(context, input, layoutTemplate.getName(), layoutInstance, itemInstance);
                                                }));
                    }
                    parallelAreaInstance.getParallelArea().exec(layoutTemplate.getName(), parallelAreaInstance);
                }
                layout.exec(layoutTemplate.getName(), layoutInstance);
                layoutIndex++;
                layoutInstance.setLayoutIndex(layoutIndex);
            }
        }
        context.exec(layoutTemplate.getName());
//        context.clear();
        return context;
    }

    private void calc(Context<I> context, Map<String, ?> input, String layoutTemplateName, LayoutInstance layoutInstance, ItemInstance itemInstance) {
        Closure closure = CalcCache.get(layoutTemplateName, itemInstance.getItem().getName());
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
