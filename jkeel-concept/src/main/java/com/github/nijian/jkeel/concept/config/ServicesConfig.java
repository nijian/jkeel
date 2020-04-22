package com.github.nijian.jkeel.concept.config;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.Map;

@XmlRootElement(name = "root")
public class ServicesConfig {

    private Map<String, ServiceConfig> serviceConfigMap;

    public Map<String, ServiceConfig> getServiceConfigMap() {
        return serviceConfigMap;
    }

    @XmlJavaTypeAdapter(ServicesConfigAdapter.class)
    @XmlElement(name = "services")
    public void setServiceConfigMap(Map<String, ServiceConfig> serviceConfigMap) {
        this.serviceConfigMap = serviceConfigMap;
    }
}
