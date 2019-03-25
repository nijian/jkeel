package com.github.nijian.jkeel.algorithms.serration.entity;

import com.github.nijian.jkeel.algorithms.serration.operands.BigDecimalOperand;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LayoutOutputInstance {

    private final Map<String, List<BigDecimalOperand>> map = new HashMap<>();

    public Map<String, List<BigDecimalOperand>> getMap() {
        return map;
    }
    
}
