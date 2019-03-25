package com.github.nijian.jkeel.algorithms;

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
public final class Serration<I> extends Algorithm<I, Context<I>, Closure> {

    private static Logger logger = LoggerFactory.getLogger(Serration.class);

    @Override
    protected Map<String, ?> convertInput(I rawInput, Map<String, ?> var, AlgorithmContext<Closure> ac) {
        final Map<String, ?> input = new HashMap();
        Closure closure = ac.getCache().get(StringUtils.joinWith(":", ac.getTemplate().getName(), Const.CONVERT));
        closure.call(rawInput, var, input);
        return input;
    }

    @Override
    protected Context<I> calc(final Map<String, ?> input, final AlgorithmContext<Closure> ac) {
        logger.info("Serration algorithm context is initializing");
        Context<I> context = new Context(input, ac);
        logger.info("Serration algorithm context has been initialized");

        Template template = ac.getTemplate();
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
                                                    calc(ac.getCache(), context, input, template.getName(), layoutInstance, itemInstance);
                                                }));
                    } else {
                        parallelAreaInstance.getItemInstanceAnchors().stream().forEach(
                                itemInstanceAnchor ->
                                        itemInstanceAnchor.getItemInstances().stream().forEach(
                                                itemInstance ->
                                                {
                                                    calc(ac.getCache(), context, input, template.getName(), layoutInstance, itemInstance);
                                                }));
                    }
                    parallelAreaInstance.getParallelArea().exec(template.getName(), parallelAreaInstance, ac.getCache());
                }
                layout.exec(template.getName(), layoutInstance, ac.getCache());
                layoutIndex++;
                layoutInstance.setLayoutIndex(layoutIndex);
            }
        }

        logger.info("Serration algorithm output is processing");
        context.exec(template.getName(), ac.getCache());
        logger.info("Serration algorithm output is processed");

        return context;
    }


    private void calc(Cache<String, Closure> closureCache, Context<I> context, Map<String, ?> input, String layoutTemplateName, LayoutInstance layoutInstance, ItemInstance itemInstance) {
        Closure closure = closureCache.get(StringUtils.joinWith(":", layoutTemplateName, itemInstance.getItem().getName()));
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
