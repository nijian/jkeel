package com.github.nijian.jkeel.algorithms;

import com.github.nijian.jkeel.algorithms.debug.OutputFactoryProvider;
import com.github.nijian.jkeel.algorithms.serration.CalcConfig;
import com.github.nijian.jkeel.algorithms.serration.Const;
import com.github.nijian.jkeel.algorithms.serration.debug.CSVOutput;
import com.github.nijian.jkeel.algorithms.serration.entity.*;
import com.github.nijian.jkeel.algorithms.serration.operands.BigDecimalOperand;
import groovy.lang.Closure;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.cache.Cache;
import java.util.*;


/**
 * Serration is a kind of template algorithm.
 *
 * @param <I> raw input type
 * @author nj
 * @since 0.0.1
 */
public final class Serration<I> extends Algorithm<I, Context<I>, TemplateAlgorithmContext> {

    private static Logger logger = LoggerFactory.getLogger(Serration.class);

    /**
     * Serration singleton instance
     */
    private static Serration instance;

    /**
     * Serration algorithm is based on Groovy closure.
     * IMPORTANT!!!
     * Closure is thread safe since Groovy 2.4.13
     */
    private Cache<String, Closure> cache;

    /**
     * private construction
     */
    private Serration() {
    }

    /**
     * Get serration instance
     *
     * @return serration instance
     */
    public static Serration getInstance() {
        if (instance == null) {
            synchronized (Serration.class) {
                if (instance == null) {
                    instance = new Serration();
                }
            }
        }
        return instance;
    }

    private Cache<String, Closure> getCache(TemplateAlgorithmContext ac) {
        if (cache == null) {
            cache = ((CalcConfig) ac.getConfig()).getCache();
        }
        return cache;
    }

    @Override
    protected Map<String, ?> convertInput(I rawInput, Map<String, ?> var, TemplateAlgorithmContext ac) {
        Closure closure = getCache(ac).get(Const.CONVERT);
        if (closure != null) {
            final Map<String, ?> input = new HashMap();
            closure.call(rawInput, var, input);
            return input;
        }
        return null;
    }

    @Override
    protected <T> Context<I> calc(final T input, final TemplateAlgorithmContext ac) {
        logger.info("Serration algorithm context is initializing");
        Context<I> context = new Context(input, ac, getCache(ac));
        logger.info("Serration algorithm context has been initialized");

        LayoutTemplateInstance loutTI = context.getLayoutTemplateInstance();
        AlgorithmTemplate algorithmTemplate = ac.getTemplate();
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
                                                    calc(getCache(ac), context, input, loutI, itemI);
                                                }));
                    } else {
                        pAreaI.getItemInstanceAnchors().stream().forEach(
                                itemIA ->
                                        itemIA.getItemInstances().stream().forEach(
                                                itemI ->
                                                {
                                                    calc(getCache(ac), context, input, loutI, itemI);
                                                }));
                    }
                    pAreaI.getParallelArea().exec(algorithmTemplate.getCid(), pAreaI, getCache(ac));
                }
                lout.exec(algorithmTemplate.getCid(), loutI, getCache(ac));
                loutIndex++;
                loutI.setLayoutIndex(loutIndex);
            }
        }

        logger.info("Serration algorithm output is processing");
        loutTI.exec(getCache(ac));
        logger.info("Serration algorithm output is processed");

        return context;
    }

    private <T> void calc(Cache<String, Closure> closureCache, Context<I> context, T input, LayoutInstance layoutInstance, ItemInstance itemInstance) {
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

    @Override
    protected void debug(Context<I> result) {

        List<String> headers = new ArrayList();
        List<List<String>> dataTable = new ArrayList();
        List<String> dataRows = new ArrayList();
        dataTable.add(dataRows);

        int maxRowNum = 0;
        List<LayoutInstance> layoutInstances = result.getLayoutTemplateInstance().getLayoutInstances();
        for (LayoutInstance layoutInstance : layoutInstances) {
            int itemGC = layoutInstance.getItemGroupCount();
            int loutC = layoutInstance.getLayoutCount();
            if (itemGC > maxRowNum) {
                maxRowNum = itemGC;
            }
            if (loutC > maxRowNum) {
                maxRowNum = loutC;
            }
        }

        Map<String, LayoutOutputInstance> itemOutMap = result.getItemOutMap();
        Iterator<String> keyIter = itemOutMap.keySet().iterator();
        while (keyIter.hasNext()) {
            String key = keyIter.next();
            headers.add(key);
            LayoutOutputInstance loutI = itemOutMap.get(key);
            List<BigDecimalOperand> valueList = loutI.getMap().get(key);
            for (int i = 0; i < maxRowNum; i++) {

                List<String> row;
                if (dataRows.size() <= i) {
                    row = new ArrayList();
                    dataTable.add(row);
                }
                row = dataTable.get(i);
                row.add(valueList.get(i).getValue().toString());
            }
        }

        Map<String, ItemInstanceAnchor> itemLocationMap = result.getItemLocationMap();
        keyIter = itemLocationMap.keySet().iterator();
        while (keyIter.hasNext()) {
            String key = keyIter.next();
            if (itemOutMap.containsKey(key)) {
                continue;
            }
            headers.add(key);
            ItemInstanceAnchor itemIA = itemLocationMap.get(key);
            List<ItemInstance> itemIList = itemIA.getItemInstances();
            for (int i = 0; i < maxRowNum; i++) {
                List<String> row = dataTable.get(i);
                if (itemIList.size() > i) {
                    row.add(itemIList.get(i).getValue().getValue().toString());
                } else {
                    row.add("");
                }
            }
        }

        OutputFactoryProvider.getInstance().getOutput(CSVOutput.class.getName()).write(dataTable, headers);

    }
}
