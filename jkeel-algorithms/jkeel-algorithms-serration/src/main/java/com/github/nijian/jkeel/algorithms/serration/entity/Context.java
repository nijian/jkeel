package com.github.nijian.jkeel.algorithms.serration.entity;

import com.github.nijian.jkeel.algorithms.AlgorithmConfig;
import com.github.nijian.jkeel.algorithms.TemplateAlgorithmContext;
import com.github.nijian.jkeel.algorithms.serration.operands.BigDecimalOperand;
import groovy.lang.Closure;

import java.math.BigDecimal;
import java.util.*;

/**
 * Each serration algorithm calculation has a specific Context.
 *
 * @param <I> final/converted input type
 * @author nj
 * @since 0.0.1
 */
public class Context<I> {

    /**
     * final/converted input
     */
    private I input;

    /**
     * final output data
     */
    private Map<String, List<BigDecimal>> outputMap;

    /**
     * item map in location
     */
    private Map<String, ItemInstanceAnchor> itemLocationMap;

    /**
     * item map in layout output instance
     */
    private Map<String, LayoutOutputInstance> itemOutMap;

    /**
     * layout template instance
     */
    private LayoutTemplateInstance layoutTemplateInstance;

    /**
     * Context Constructor. Generate layout template instance by template.
     *
     * @param input      final/converted input
     * @param ac         algorithm context
     * @param closureMap closure map
     * @param calcConfig extend config for specific algorithm implementation
     */
    public Context(I input, TemplateAlgorithmContext<LayoutTemplate, AlgorithmConfig> ac, Map<String, Closure> closureMap, Object calcConfig) {

        this.input = input;
        this.outputMap = new HashMap<>();
        this.itemLocationMap = new HashMap<>();
        this.itemOutMap = new HashMap<>();

        //build layout instance
        LayoutTemplate layoutTemplate = ac.getTemplate();
        layoutTemplateInstance = new LayoutTemplateInstance(layoutTemplate);
        List<LayoutInstance> layoutInstances = layoutTemplateInstance.getLayoutInstances();
        List<Layout> loutList = layoutTemplate.getLayouts();
        loutList.forEach(layout ->
                {
                    LayoutInstance layoutInstance = new LayoutInstance(this, layout, closureMap, calcConfig);
                    layoutInstances.add(layoutInstance);
                    LayoutOutputInstance layoutOutputInstance = layoutInstance.getLayoutOutputInstance();

                    List<ParallelAreaInstance> pAreaIList = new ArrayList<>();
                    layoutInstance.setParallelAreaInstances(pAreaIList);

                    layout.getParallelAreas().forEach(pArea ->
                            {
                                ParallelAreaInstance pAreaI = new ParallelAreaInstance(pArea);
                                pAreaIList.add(pAreaI);

                                List<ItemInstanceAnchor> itemIAList = new ArrayList<>();
                                pAreaI.setItemInstanceAnchors(itemIAList);

                                pArea.getItems().forEach(item ->
                                        {
                                            if (item.isOut()) {
                                                itemOutMap.put(item.getName(), layoutOutputInstance);
                                                layoutOutputInstance.getMap().put(item.getName(), new ArrayList<>());
                                            }

                                            ItemInstanceAnchor itemInstanceAnchor = new ItemInstanceAnchor(item, layoutInstance.getItemCount());
                                            itemLocationMap.put(item.getName(), itemInstanceAnchor);
                                            itemIAList.add(itemInstanceAnchor);


                                            for (int i = 0; i < itemInstanceAnchor.getItemInstances().size(); i++) {
                                                itemInstanceAnchor.getItemInstances().get(i).setIndex(i);
                                            }

                                        }
                                );
                            }
                    );
                }
        );

        if (layoutTemplate.getOutputFields() != null) {
            String[] outputNameArray = layoutTemplate.getOutputFields().split(",");
            Arrays.stream(outputNameArray).forEach(outputName ->
                    outputMap.put(outputName, new ArrayList<BigDecimal>())
            );
        }
    }

    /**
     * Get final/converted input.
     *
     * @return final/converted input
     */
    public I getInput() {
        return input;
    }

    /**
     * Get final result
     *
     * @return final result
     */
    public Map<String, List<BigDecimal>> getOutputMap() {
        return outputMap;
    }

    /**
     * Get item location map
     *
     * @return item location map
     */
    public Map<String, ItemInstanceAnchor> getItemLocationMap() {
        return itemLocationMap;
    }

    /**
     * Get item out map
     *
     * @return item out map
     */
    public Map<String, LayoutOutputInstance> getItemOutMap() {
        return itemOutMap;
    }

    /**
     * Get layout template instance
     *
     * @return layout template instance
     */
    public LayoutTemplateInstance getLayoutTemplateInstance() {
        return layoutTemplateInstance;
    }

    ///////////////////////

    public void createGroupIndex(String paramName, int groupCount, String groupByParamName) {
        int len = itemLocationMap.get(groupByParamName).getItemInstances().size() / groupCount;
        for (int i = 1; i < len + 1; i++) {
            outputMap.get(paramName).add(new BigDecimal(i));
        }
    }

    public void createGroupIndexOnBase(String paramName, int groupCount, int base, String groupByParamName) {
        int len = itemLocationMap.get(groupByParamName).getItemInstances().size() / (groupCount);
        for (int i = 0; i < len; i++) {
            outputMap.get(paramName).add(new BigDecimal(i + base));
        }
    }

    public void putGroupSum(String paramName, int groupCount, String... sourceParamNames) {
        int len = itemLocationMap.get(sourceParamNames[0]).getItemInstances().size() / groupCount;
        for (int i = 0; i < len; i++) {
            BigDecimal value = BigDecimal.ZERO;
            for (int j = 0; j < groupCount; j++) {
                int index = i * groupCount + j;
                for (String sParamName : sourceParamNames) {
                    value = value.add(itemLocationMap.get(sParamName).getItemInstances().get(index).getValue().getValue());
                }
            }
            outputMap.get(paramName).add(value);
        }
    }

    public void putLGroupSum(String paramName, int groupCount, String... sourceParamNames) {
        int len = itemOutMap.get(sourceParamNames[0]).getMap().get(sourceParamNames[0]).size() / groupCount;
        for (int i = 0; i < len; i++) {
            BigDecimal value = BigDecimal.ZERO;
            for (int j = 0; j < groupCount; j++) {
                int index = i * groupCount + j;
                for (String sParamName : sourceParamNames) {
                    value = value.add(itemOutMap.get(sParamName).getMap().get(sParamName).get(index).getValue());
                }

            }
            outputMap.get(paramName).add(value);
        }
    }

    public void putLast(String paramName, String sourceParamName) {
        List<ItemInstance> itemIList = itemLocationMap.get(sourceParamName).getItemInstances();
        outputMap.get(paramName).add(itemIList.get(itemIList.size() - 1).getValue().getValue());
    }

    public void putLLast(String paramName, String sourceParamName) {
        List<BigDecimalOperand> list = itemOutMap.get(sourceParamName).getMap().get(sourceParamName);
        outputMap.get(paramName).add(list.get(list.size() - 1).getValue());
    }

    public void putLGroupFirst(String paramName, int groupCount, String sourceParamName) {
        int len = itemOutMap.get(sourceParamName).getMap().get(sourceParamName).size() / groupCount;
        for (int i = 0; i < len; i++) {
            outputMap.get(paramName).add(itemOutMap.get(sourceParamName).getMap().get(sourceParamName).get(i * groupCount).getValue());
        }
    }

    public void putLGroupLast(String paramName, int groupCount, String sourceParamName) {
        int len = itemOutMap.get(sourceParamName).getMap().get(sourceParamName).size() / groupCount;
        for (int i = 1; i < len + 1; i++) {
            outputMap.get(paramName).add(itemOutMap.get(sourceParamName).getMap().get(sourceParamName).get(i * groupCount - 1).getValue());
        }
    }

    public BigDecimalOperand get(String paramName) {
        return itemLocationMap.get(paramName).getItemInstances().get(0).getValue();
    }

    public BigDecimalOperand getx(int index, String paramName) {
        return itemLocationMap.get(paramName).getItemInstances().get(index).getValue();
    }

    public BigDecimalOperand getL(String paramName) {
        return itemOutMap.get(paramName).getMap().get(paramName).get(0);
    }

    public BigDecimalOperand getLx(int index, String paramName) {
        if (index < 0) {
            return new BigDecimalOperand(0);
        }
        return itemOutMap.get(paramName).getMap().get(paramName).get(index);
    }

    public BigDecimalOperand sumProduct(Context<?> context, int index, int offset, String[] arrayNames) {

        BigDecimal retValue = BigDecimal.ZERO;

        int from;
        int to;
        if (offset < 0) {
            from = index + offset;
            to = index;
        } else {
            from = index;
            to = index + offset;
        }

        List<BigDecimalOperand> valuesList = new ArrayList<>();

        for (int i = 0; i < arrayNames.length; i++) {
            if (context.getItemOutMap().containsKey(arrayNames[i])) {
                valuesList.addAll(context.getItemOutMap().get(arrayNames[i]).getMap().get(arrayNames[i]).subList(from, to + 1));
            } else {
                List<ItemInstance> itemList = context.getItemLocationMap().get(arrayNames[i]).getItemInstances().subList(from, to + 1);
                for (int j = 0; j < itemList.size(); j++) {
                    valuesList.add(itemList.get(j).getValue());
                }
            }
        }

        int len = to - from + 1;
        for (int m = 0; m < len; m++) {
            BigDecimal baseValue = BigDecimal.ONE;
            for (int n = 0; n < arrayNames.length; n++) {
                baseValue = baseValue.multiply(valuesList.get(n * len + m).getValue());
            }
            retValue = retValue.add(baseValue);
        }

        return new BigDecimalOperand(retValue);
    }

    public BigDecimalOperand sum(Context<?> context, int index, int offset, String paramName) {

        BigDecimal retValue = BigDecimal.ZERO;

        int from;
        int to;
        if (offset < 0) {
            from = index + offset;
            to = index;
        } else {
            from = index;
            to = index + offset;
        }

        List<BigDecimalOperand> valuesList = new ArrayList<>();
        if (context.getItemOutMap().containsKey(paramName)) {
            valuesList.addAll(context.getItemOutMap().get(paramName).getMap().get(paramName).subList(from, to));
        } else {
            List<ItemInstance> itemList = context.getItemLocationMap().get(paramName).getItemInstances().subList(from, to);
            for (int j = 0; j < itemList.size(); j++) {
                valuesList.add(itemList.get(j).getValue());
            }
        }

        for (int i = 0; i < valuesList.size(); i++) {
            retValue = retValue.add(valuesList.get(i).getValue());
        }

        return new BigDecimalOperand(retValue);
    }

    public BigDecimal layerSum(BigDecimal[] rateTable, BigDecimal amount) {
        BigDecimal retValue = BigDecimal.ZERO;
        BigDecimal deductedAmount = amount;
        for (int layer = 0; layer * 3 < rateTable.length; layer++) {
            BigDecimal startValue = rateTable[layer * 3];
            BigDecimal endValue = rateTable[layer * 3 + 1];
            BigDecimal rate = rateTable[layer * 3 + 2];
            BigDecimal rangeValue = endValue.subtract(startValue);
            if (endValue.compareTo(BigDecimal.ZERO) < 0 || deductedAmount.compareTo(rangeValue) < 0) {
                return retValue.add(deductedAmount.multiply(rate));
            } else {
                retValue = retValue.add(rangeValue.multiply(rate));
                deductedAmount = deductedAmount.subtract(rangeValue);
            }
        }
        return BigDecimal.ZERO;
    }

}
