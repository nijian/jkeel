package com.github.nijian.jkeel.concept.config;

import com.github.nijian.jkeel.concept.Algorithm;
import com.github.nijian.jkeel.concept.ConfigItem;
import com.github.nijian.jkeel.concept.spi.AlgorithmFactoryProvider;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.List;

@XmlType(name = "algorithm")
@XmlAccessorType(XmlAccessType.FIELD)
public class AlgorithmConfig extends ConfigItem<Algorithm> {

    @XmlElement(name = "use")
    private List<Use> useList;

    public List<Use> getUseList() {
        return useList;
    }

    public void setUseList(List<Use> useList) {
        this.useList = useList;
    }

    @Override
    public Algorithm getBehavior() {
        return AlgorithmFactoryProvider.getInstance().getAlgorithm(getName());
    }
}
