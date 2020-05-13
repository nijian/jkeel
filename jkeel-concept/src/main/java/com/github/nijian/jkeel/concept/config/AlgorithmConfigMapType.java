package com.github.nijian.jkeel.concept.config;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
public class AlgorithmConfigMapType {

    @XmlElement(name = "algorithm")
    private List<AlgorithmConfig> algorithmConfigList;

    public List<AlgorithmConfig> getAlgorithmConfigList() {
        return algorithmConfigList;
    }

    public void setAlgorithmConfigList(List<AlgorithmConfig> algorithmConfigList) {
        this.algorithmConfigList = algorithmConfigList;
    }
}
