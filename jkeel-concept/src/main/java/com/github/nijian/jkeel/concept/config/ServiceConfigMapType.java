package com.github.nijian.jkeel.concept.config;

import javax.xml.bind.annotation.XmlElement;
import java.util.List;

public class ServiceConfigMapType {

    private List<ServiceConfig> serviceConfigList;

    public List<ServiceConfig> getServiceConfigList() {
        return serviceConfigList;
    }

    @XmlElement(name="service")
    public void setServiceConfigList(List<ServiceConfig> serviceConfigList) {
        this.serviceConfigList = serviceConfigList;
    }
}
