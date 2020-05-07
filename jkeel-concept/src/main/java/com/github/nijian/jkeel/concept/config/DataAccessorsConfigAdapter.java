package com.github.nijian.jkeel.concept.config;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.util.HashMap;
import java.util.Map;

public class DataAccessorsConfigAdapter extends XmlAdapter<DataAccessorConfigMapType, Map<String, DataAccessorConfig>> {

    @Override
    public Map<String, DataAccessorConfig> unmarshal(DataAccessorConfigMapType v) throws Exception {
        Map<String, DataAccessorConfig> map = new HashMap<>();
        for (DataAccessorConfig dataAccessorConfig : v.getDataAccessorConfigList()) {
            map.put(dataAccessorConfig.getId(), dataAccessorConfig);
        }
        return map;
    }

    @Override
    public DataAccessorConfigMapType marshal(Map<String, DataAccessorConfig> v) throws Exception {
        return null;
    }
}
