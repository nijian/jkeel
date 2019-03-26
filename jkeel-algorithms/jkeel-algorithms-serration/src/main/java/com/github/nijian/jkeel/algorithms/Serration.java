package com.github.nijian.jkeel.algorithms;

import com.github.nijian.jkeel.algorithms.serration.Const;
import com.github.nijian.jkeel.algorithms.serration.entity.*;
import groovy.lang.Closure;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.cache.Cache;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * DO NOT CHANGE THIS CLASS UNLESS YOU ARE CLEAR WHAT EXACT IMPACT FOR PERFORMANCE!!!
 * <p>
 * Created by johnson.ni
 */
public final class Serration<I> extends Algorithm<I, Context<I>, Closure> {

    private static Logger logger = LoggerFactory.getLogger(Serration.class);

    @Override
    protected Map<String, ?> convertInput(I rawInput, Map<String, ?> var, AlgorithmContext<?, ?, Closure> ac) {
        final Map<String, ?> input = new HashMap();
        Closure closure = ac.getCache().get(Const.CONVERT);
        closure.call(rawInput, var, input);
        return input;
    }

    @Override
    protected Context<I> calc(final Map<String, ?> input, final AlgorithmContext<?, ?, Closure> ac) {
        logger.info("Serration algorithm context is initializing");
        Context<I> context = new Context(input, ac);
        logger.info("Serration algorithm context has been initialized");

        LayoutTemplateInstance loutTI = context.getLayoutTemplateInstance();
        Template template = ac.getTemplate();
        List<LayoutInstance> loutIs = loutTI.getLayoutInstances();
        for (LayoutInstance loutI : loutIs) {
            Layout lout = loutI.getLayout();
            int loutCount = loutI.getLayoutCount();
            int loutIndex = loutI.getLayoutIndex();
            while (loutIndex < loutCount) {
                for (ParallelAreaInstance pAreaI : loutI.getParallelAreaInstances()) {
                    if (lout.isParallel()) {
                        pAreaI.getItemInstanceAnchors().parallelStream().forEach(
                                itemIA ->
                                        itemIA.getItemInstances().parallelStream().forEach(
                                                itemI ->
                                                {
                                                    calc(ac.getCache(), context, input, loutI, itemI);
                                                }));
                    } else {
                        pAreaI.getItemInstanceAnchors().stream().forEach(
                                itemIA ->
                                        itemIA.getItemInstances().stream().forEach(
                                                itemI ->
                                                {
                                                    calc(ac.getCache(), context, input, loutI, itemI);
                                                }));
                    }
                    pAreaI.getParallelArea().exec(template.getCid(), pAreaI, ac.getCache());
                }
                lout.exec(template.getCid(), loutI, ac.getCache());
                loutIndex++;
                loutI.setLayoutIndex(loutIndex);
            }
        }

        logger.info("Serration algorithm output is processing");
        loutTI.exec(ac.getCache());
        logger.info("Serration algorithm output is processed");

        return context;
    }


    private void calc(Cache<String, Closure> closureCache, Context<I> context, Map<String, ?> input, LayoutInstance layoutInstance, ItemInstance itemInstance) {
        Item item = itemInstance.getItem();
        String itemName = item.getName();
        Closure closure = closureCache.get(itemName);
        if (closure != null) {
            closure.setDelegate(layoutInstance);
            closure.setResolveStrategy(Closure.DELEGATE_ONLY);
            if (item.isOut()) {
                item.calc(input, closure, itemInstance, itemInstance.getIndex(), context.getItemOutMap().get(itemName).getMap().get(itemName));
            } else {
                item.calc(input, closure, itemInstance, itemInstance.getIndex(), null);
            }
        }
    }
}
