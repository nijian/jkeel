package com.github.nijian.jkeel.concept.config;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.util.HashMap;
import java.util.Map;

public class CodesConfigAdapter extends XmlAdapter<CodeConfigMapType, Map<String, CodeConfig>> {

    @Override
    public Map<String, CodeConfig> unmarshal(CodeConfigMapType v) throws Exception {
        Map<String, CodeConfig> map = new HashMap<>();
        for (CodeConfig codeConfig : v.getCodeConfigList()) {
            map.put(codeConfig.getId(), codeConfig);
        }
        return map;
    }

    @Override
    public CodeConfigMapType marshal(Map<String, CodeConfig> v) throws Exception {
        return null;
    }
}
