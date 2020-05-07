package com.github.nijian.jkeel.concept.config;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.util.HashMap;
import java.util.Map;

public class ActionsConfigAdapter extends XmlAdapter<ActionConfigMapType, Map<String, ActionConfig>> {

    @Override
    public Map<String, ActionConfig> unmarshal(ActionConfigMapType v) throws Exception {
        Map<String, ActionConfig> map = new HashMap<>();
        for (ActionConfig actionConfig : v.getActionConfigList()) {
            map.put(actionConfig.getId(), actionConfig);
        }
        return map;
    }

    @Override
    public ActionConfigMapType marshal(Map<String, ActionConfig> v) throws Exception {
        return null;
    }
}
