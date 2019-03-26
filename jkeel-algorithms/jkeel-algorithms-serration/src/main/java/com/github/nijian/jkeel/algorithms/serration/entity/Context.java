package com.github.nijian.jkeel.algorithms.serration.entity;

import com.github.nijian.jkeel.algorithms.AlgorithmContext;
import com.github.nijian.jkeel.algorithms.serration.operands.BigDecimalOperand;
import groovy.lang.Closure;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Context<I> {

    private I input;
    private Map<String, List<BigDecimal>> outputMap;
    private Map<String, ItemInstanceAnchor> itemLocationMap;
    private Map<String, LayoutOutputInstance> itemOutMap;
    private LayoutTemplateInstance layoutTemplateInstance;

    public Context(I input, AlgorithmContext<?, ?, Closure> ac) {

        this.input = input;
        this.outputMap = new HashMap<>();
        this.itemLocationMap = new HashMap<>();
        this.itemOutMap = new HashMap<>();

        ContextHelper.populate(ac, this, itemOutMap, itemLocationMap, outputMap);
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

    public void setLayoutTemplateInstance(LayoutTemplateInstance layoutTemplateInstance) {
        this.layoutTemplateInstance = layoutTemplateInstance;
    }
}
