package com.github.nijian.jkeel.algorithms;

import com.github.nijian.jkeel.algorithms.debug.OutputFactoryProvider;
import com.github.nijian.jkeel.algorithms.serration.Const;
import com.github.nijian.jkeel.algorithms.serration.SerrationConfig;
import com.github.nijian.jkeel.algorithms.serration.debug.CSVOutput;
import com.github.nijian.jkeel.algorithms.serration.entity.*;
import com.github.nijian.jkeel.algorithms.serration.operands.BigDecimalOperand;
import groovy.lang.Closure;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.cache.Cache;
import java.io.FileWriter;
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

    /**
     * Convert raw input to Map
     *
     * @param rawInput raw input
     * @param var      variables collection
     * @param ac       algorithm context
     * @return converted input
     */
    @Override
    protected Map<String, ?> convertInput(I rawInput, Map<String, ?> var, TemplateAlgorithmContext ac) {
        SerrationConfig serrationConfig = (SerrationConfig) ac.getConfig();
        Closure closure = serrationConfig.getCache().get(Const.CONVERT);
        if (closure != null) {
            final Map<String, Object> input = new HashMap<>();
            ((Closure) closure.clone()).call(rawInput, var, input);
            input.keySet().forEach(key -> {
                Object inputValue = input.get(key);
                if (inputValue instanceof Number) {
                    Object inputValueObj = new BigDecimalOperand((Number) inputValue, 10);
                    input.put(key, inputValueObj);
                }
            });
            return input;
        }
        return null;
    }

    /**
     * Serration algorithm core
     *
     * @param input converted input
     * @param ac    algorithm context
     * @param <T>   raw input type
     * @return calculation result
     */
    @Override
    protected <T> Context<I> calc(final T input, final TemplateAlgorithmContext ac) {
        SerrationConfig serrationConfig = (SerrationConfig) ac.getConfig();

        //get associated properties
        final boolean negToZero = serrationConfig.isNegToZero();

        logger.info("Serration algorithm is preparing closure map");
        Map<String, Closure> closureMap = new HashMap<>();
        serrationConfig.getCache().forEach(item ->
                {
                    String key = item.getKey();
                    Closure closure = (Closure) item.getValue().clone();
                    closureMap.put(key, closure);
                }
        );
        logger.info("Serration algorithm closure map is ready");

        logger.info("Serration algorithm context is initializing");
        Context<I> context = new Context(input, ac, closureMap, ac.getConfig().getDelegate());
        logger.info("Serration algorithm context has been initialized");

        Properties env = ac.getConfig().getEnv();
        String calcClzName = env == null ? null : env.getProperty(Const.CALC_CLASS_NAME_KEY);
        if (calcClzName == null) {
            calcClzName = Const.CALC_CLASS_NAME;
        }
        logger.info("Calc class name:{}", calcClzName);
        Object calc;
        try {
            Class calcClz = Class.forName(calcClzName);
            calc = calcClz.getDeclaredConstructor(Context.class).newInstance(context);
        } catch (Exception e) {
            logger.error("Failed to load Calc", e);
            throw new RuntimeException("Failed to load Calc", e);
        }

        LayoutTemplateInstance loutTI = context.getLayoutTemplateInstance();
        List<LayoutInstance> loutIs = loutTI.getLayoutInstances();
        for (LayoutInstance loutI : loutIs) {
            Layout lout = loutI.getLout();
            int loutCount = loutI.getLoutCount();
            boolean isLidx = loutCount > 1 ? true : false;
            int loutIndex = loutI.getIndex();
            while (loutIndex < loutCount) {
                for (ParallelAreaInstance pAreaI : loutI.getParallelAreaInstances()) {
                    if (lout.isParallel()) {
                        pAreaI.getItemInstanceAnchors().parallelStream().forEach(
                                itemIA ->
                                        itemIA.getItemInstances().parallelStream().forEach(
                                                itemI ->
                                                {
                                                    calc(closureMap, context, input, loutI, itemI, isLidx, calc, negToZero);
                                                }));
                    } else {
                        pAreaI.getItemInstanceAnchors().forEach(
                                itemIA ->
                                        itemIA.getItemInstances().forEach(
                                                itemI ->
                                                {
                                                    calc(closureMap, context, input, loutI, itemI, isLidx, calc, negToZero);
                                                }));
                    }
                    pAreaI.getParallelArea().exec(pAreaI, closureMap, calc);
                }
                lout.exec(loutI, closureMap, calc);
                loutIndex++;
                loutI.setIndex(loutIndex);
            }
        }

        logger.info("Serration algorithm output is processing");
        loutTI.getLayoutTemplate().exec(input, calc, closureMap);
        logger.info("Serration algorithm output is processed");

        return context;
    }

    /**
     * Calculation support.
     *
     * @param closureMap     cache
     * @param context        calculation result reference
     * @param input          final input
     * @param layoutInstance layout instance
     * @param itemInstance   item instance
     * @param isLidx         is layout index or not
     * @param calc           calculation object
     * @param negToZero      negative to zero or not
     * @param <T>            final input type
     */
    private <T> void calc(Map<String, Closure> closureMap, Context<I> context, T input, LayoutInstance layoutInstance,
                          ItemInstance itemInstance, boolean isLidx, Object calc, boolean negToZero) {
        Item item = itemInstance.getItem();
        String itemName = item.getName();
        Closure closure = closureMap.get(itemName);
        if (closure != null) {
            closure.setDelegate(calc);
            closure.setResolveStrategy(Closure.DELEGATE_ONLY);
            if (item.isOut()) {
                item.calc(input, itemInstance, layoutInstance, isLidx, closure,
                        context.getItemOutMap().get(itemName).getMap().get(itemName), negToZero);
            } else {
                item.calc(input, itemInstance, layoutInstance, isLidx, closure, negToZero);
            }
        }
    }

    /**
     * Debug
     *
     * @param result calculation result
     */
    @Override
    protected void debug(Context<I> result) {
        List<String> headers = new ArrayList<>();
        List<List<String>> dataTable = new ArrayList<>();
        List<String> dataRows = new ArrayList<>();
        dataTable.add(dataRows);

        int maxRowNum = 0;
        List<LayoutInstance> layoutInstances = result.getLayoutTemplateInstance().getLayoutInstances();
        for (LayoutInstance layoutInstance : layoutInstances) {
            int itemGC = layoutInstance.getItemCount();
            int loutC = layoutInstance.getLoutCount();
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
                    row = new ArrayList<>();
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
                    try {
                        row.add(itemIList.get(i).getValue().getValue().toString());
                    } catch (Exception e) {
                        row.add("UNDEFINED!");
                    }
                } else {
                    row.add("");
                }
            }
        }

        try {
            FileWriter fileWriter = new FileWriter("a.csv");
            OutputFactoryProvider.getInstance().getOutput(CSVOutput.class.getName()).write(dataTable, headers, fileWriter);
        } catch (Exception e) {
            logger.warn("Failed to write debug output", e);
        }

    }
}
