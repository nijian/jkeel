package com.github.nijian.jkeel.algorithms.serration.entity;

import com.github.nijian.jkeel.algorithms.serration.operands.BigDecimalOperand;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Layout output instance
 *
 * @author nj
 * @since 0.0.1
 */
public class LayoutOutputInstance {

    /**
     * value list map
     */
    private final Map<String, List<BigDecimalOperand>> map = new HashMap<>();

    /**
     * Get value list map
     *
     * @return value list map
     */
    public Map<String, List<BigDecimalOperand>> getMap() {
        return map;
    }

}
