package com.github.nijian.jkeel.concept.config;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.util.HashMap;
import java.util.Map;

public class ServicesConfigAdapter extends XmlAdapter<ServiceConfigMapType, Map<String, ServiceConfig>> {

    @Override
    public Map<String, ServiceConfig> unmarshal(ServiceConfigMapType v) throws Exception {
        System.out.println("u: " + v.getServiceConfigList().size());
        Map<String, ServiceConfig> map = new HashMap<>();

        for (ServiceConfig serviceConfig : v.getServiceConfigList()) {
            System.out.println(serviceConfig);
            map.put(serviceConfig.getEntryName(), serviceConfig);
        }
        return map;
    }

    @Override
    public ServiceConfigMapType marshal(Map<String, ServiceConfig> v) throws Exception {
        return null;
    }
}
