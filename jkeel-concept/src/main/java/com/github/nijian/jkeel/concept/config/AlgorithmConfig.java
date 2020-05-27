package com.github.nijian.jkeel.concept.config;

import com.github.nijian.jkeel.concept.Algorithm;
import com.github.nijian.jkeel.concept.ConfigItem;
import com.github.nijian.jkeel.concept.spi.AlgorithmFactoryProvider;

import javax.xml.bind.annotation.*;
import java.util.List;

@XmlType(name = "algorithm")
@XmlAccessorType(XmlAccessType.FIELD)
public class AlgorithmConfig extends ConfigItem<Algorithm> {

    @XmlID
    @XmlAttribute
    private String id;

    @XmlElement(name = "use")
    private List<Use> useList;

    @Override
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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
