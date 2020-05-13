package com.github.nijian.jkeel.concept.config;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.util.HashMap;
import java.util.Map;

public class AlgorithmsConfigAdapter extends XmlAdapter<AlgorithmConfigMapType, Map<String, AlgorithmConfig>> {

    @Override
    public Map<String, AlgorithmConfig> unmarshal(AlgorithmConfigMapType v) throws Exception {
        Map<String, AlgorithmConfig> map = new HashMap<>();
        for (AlgorithmConfig actionConfig : v.getAlgorithmConfigList()) {
            map.put(actionConfig.getId(), actionConfig);
        }
        return map;
    }

    @Override
    public AlgorithmConfigMapType marshal(Map<String, AlgorithmConfig> v) throws Exception {
        return null;
    }
}