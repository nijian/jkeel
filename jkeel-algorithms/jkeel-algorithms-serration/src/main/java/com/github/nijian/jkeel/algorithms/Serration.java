package com.github.nijian.jkeel.algorithms;

import com.github.nijian.jkeel.algorithms.serration.CalcConfig;
import com.github.nijian.jkeel.algorithms.serration.Const;
import com.github.nijian.jkeel.algorithms.serration.Utils;
import com.github.nijian.jkeel.algorithms.serration.debug.Iterables;
import com.github.nijian.jkeel.algorithms.serration.debug.Table;
import com.github.nijian.jkeel.algorithms.serration.entity.*;
import com.github.nijian.jkeel.algorithms.serration.operands.BigDecimalOperand;
import groovy.lang.Closure;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.cache.Cache;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * DO NOT CHANGE THIS CLASS UNLESS YOU ARE CLEAR WHAT EXACT IMPACT FOR PERFORMANCE!!!
 * <p>
 * Created by johnson.ni
 */
public final class Serration<I> extends Algorithm<I, Context<I>, LayoutTemplate, CalcConfig> {

    private static Logger logger = LoggerFactory.getLogger(Serration.class);

    private static Serration instance;

    private Cache<String, Closure> cache;

    private Serration() {
    }

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

    private Cache<String, Closure> getCache(AlgorithmContext<LayoutTemplate, CalcConfig> ac) {
        if (cache == null) {
            cache = ac.getConfig().getCache();
        }
        return cache;
    }

    @Override
    protected Map<String, ?> convertInput(I rawInput, Map<String, ?> var, AlgorithmContext<LayoutTemplate, CalcConfig> ac) {
        final Map<String, ?> input = new HashMap();
        Closure closure = getCache(ac).get(Const.CONVERT);
        closure.call(rawInput, var, input);
        return input;
    }

    @Override
    protected Context<I> calc(final Map<String, ?> input, final AlgorithmContext<LayoutTemplate, CalcConfig> ac) {
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

    @Override
    protected String debug(Context<I> result) {
        Table table = new Table();
        List<Table.Row> rows = new ArrayList();
        table.setRows(rows);

        Map<String, LayoutOutputInstance> itemOutMap = result.getItemOutMap();
        itemOutMap.keySet().stream().forEach(key ->
        {
            LayoutOutputInstance loutI = itemOutMap.get(key);
            Map<String, List<BigDecimalOperand>> valueMap = loutI.getMap();
            valueMap.keySet().stream().forEach(key1 ->


            {
                List<BigDecimalOperand> valueList = valueMap.get(key1);
                for (int i = 0; i < valueList.size(); i++) {
                    Table.Row row;
                    if (rows.size() <= i) {
                        row = new Table.Row();
                        rows.add(row);

                        List<Table.Cell> cells = new ArrayList();
                        row.setCells(cells);
                    } else {
                        row = rows.get(i);
                    }
                    Table.Cell cell = new Table.Cell();
                    row.getCells().add(cell);
                    cell.setName(key1);
                    cell.setValue(valueList.get(i).toString());
                }

            });
        });

        try {
            String debugInfo = Utils.objectMapper.writeValueAsString(table);
            System.out.println(debugInfo);
            return debugInfo;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
