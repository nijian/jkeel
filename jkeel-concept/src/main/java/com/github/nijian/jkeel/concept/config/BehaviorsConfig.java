package com.github.nijian.jkeel.concept.config;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.Map;

@XmlAccessorType(XmlAccessType.FIELD)
public class BehaviorsConfig {

    @XmlElement(name = "services")
    @XmlJavaTypeAdapter(ServicesConfigAdapter.class)
    private Map<String, ServiceConfig> serviceConfigMap;

    @XmlElement(name = "actions")
    @XmlJavaTypeAdapter(ActionsConfigAdapter.class)
    private Map<String, ActionConfig> actionConfigMap;

    @XmlElement(name = "algorithms")
    @XmlJavaTypeAdapter(AlgorithmsConfigAdapter.class)
    private Map<String, AlgorithmConfig> algorithmConfigMap;

    @XmlElement(name = "dataAccessors")
    @XmlJavaTypeAdapter(DataAccessorsConfigAdapter.class)
    private Map<String, DataAccessorConfig> dataAccessorConfigMap;

    public Map<String, ServiceConfig> getServiceConfigMap() {
        return serviceConfigMap;
    }

    public Map<String, ActionConfig> getActionConfigMap() {
        return actionConfigMap;
    }

    public Map<String, AlgorithmConfig> getAlgorithmConfigMap() {
        return algorithmConfigMap;
    }

    public void setAlgorithmConfigMap(Map<String, AlgorithmConfig> algorithmConfigMap) {
        this.algorithmConfigMap = algorithmConfigMap;
    }

    public Map<String, DataAccessorConfig> getDataAccessorConfigMap() {
        return dataAccessorConfigMap;
    }

    public void setServiceConfigMap(Map<String, ServiceConfig> serviceConfigMap) {
        this.serviceConfigMap = serviceConfigMap;
    }

    public void setActionConfigMap(Map<String, ActionConfig> actionConfigMap) {
        this.actionConfigMap = actionConfigMap;
    }

    public void setDataAccessorConfigMap(Map<String, DataAccessorConfig> dataAccessorConfigMap) {
        this.dataAccessorConfigMap = dataAccessorConfigMap;
    }
}
