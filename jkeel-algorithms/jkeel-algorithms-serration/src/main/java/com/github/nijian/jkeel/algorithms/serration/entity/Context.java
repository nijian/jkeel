package com.github.nijian.jkeel.algorithms.serration.entity;

import com.github.nijian.jkeel.algorithms.AlgorithmContext;
import com.github.nijian.jkeel.algorithms.serration.CalcConfig;
import com.github.nijian.jkeel.algorithms.serration.operands.BigDecimalOperand;
import groovy.lang.Closure;

import javax.cache.Cache;
import java.math.BigDecimal;
import java.util.*;

public class Context<I> {

    private I input;
    private Map<String, List<BigDecimal>> outputMap;
    private Map<String, ItemInstanceAnchor> itemLocationMap;
    private Map<String, LayoutOutputInstance> itemOutMap;
    private LayoutTemplateInstance layoutTemplateInstance;

    public Context(I input, AlgorithmContext<LayoutTemplate, CalcConfig> ac, Cache<String, Closure> cache) {

        this.input = input;
        this.outputMap = new HashMap<>();
        this.itemLocationMap = new HashMap<>();
        this.itemOutMap = new HashMap<>();

        layoutTemplateInstance = new LayoutTemplateInstance(this);
        List<LayoutInstance> layoutInstances = layoutTemplateInstance.getLayoutInstances();
        LayoutTemplate layoutTemplate = ac.getTemplate();
        layoutTemplate.getLayouts().stream().forEach(layout ->
                {
                    LayoutInstance layoutInstance = new LayoutInstance(this, layout, cache);
                    layoutInstances.add(layoutInstance);
                    LayoutOutputInstance layoutOutputInstance = layoutInstance.getLayoutOutputInstance();

                    List<ParallelAreaInstance> pAreaIList = new ArrayList<>();
                    layoutInstance.setParallelAreaInstances(pAreaIList);

                    layout.getParallelAreas().stream().forEach(pArea ->
                            {
                                ParallelAreaInstance pAreaI = new ParallelAreaInstance(pArea);
                                pAreaIList.add(pAreaI);

                                List<ItemInstanceAnchor> itemIAList = new ArrayList<>();
                                pAreaI.setItemInstanceAnchors(itemIAList);

                                pArea.getItems().stream().forEach(item ->
                                        {
                                            if (item.isOut()) {
                                                itemOutMap.put(item.getName(), layoutOutputInstance);
                                                layoutOutputInstance.getMap().put(item.getName(), new ArrayList<>());
                                            }

                                            ItemInstanceAnchor itemInstanceAnchor = new ItemInstanceAnchor(item, layoutInstance.getItemGroupCount());
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

    public void createGroupIndex(String paramName, int groupCount, String groupByParamName) {
        int len = itemLocationMap.get(groupByParamName).getItemInstances().size() / groupCount;
        for (int i = 1; i < len + 1; i++) {
            outputMap.get(paramName).add(new BigDecimal(i));
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

    public I getInput() {
        return input;
    }

    public Map<String, List<BigDecimal>> getOutputMap() {
        return outputMap;
    }

    public Map<String, ItemInstanceAnchor> getItemLocationMap() {
        return itemLocationMap;
    }

    public Map<String, LayoutOutputInstance> getItemOutMap() {
        return itemOutMap;
    }

    public LayoutTemplateInstance getLayoutTemplateInstance() {
        return layoutTemplateInstance;
    }

}
