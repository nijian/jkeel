package com.github.nijian.jkeel.concept.config;

import com.github.nijian.jkeel.concept.Algorithm;
import com.github.nijian.jkeel.concept.BehaviorInput;
import com.github.nijian.jkeel.concept.ServiceContext;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

@XmlType(name = "algorithmRef")
@XmlAccessorType(XmlAccessType.FIELD)
public class AlgorithmReference extends BehaviorReference<AlgorithmConfig> {

    @XmlAttribute
    private String ref;

    private AlgorithmConfig behaviorConfig;

    public String getRef() {
        return ref;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }

    @Override
    public AlgorithmConfig getBehaviorConfig() {
        return behaviorConfig;
    }

    @Override
    public void setBehaviorConfig(AlgorithmConfig behaviorConfig) {
        this.behaviorConfig = behaviorConfig;
    }

    @Override
    public Object execute(ServiceContext ctx, Link link, Object realValue) {
        Algorithm algorithm = behaviorConfig.getBehavior();
        algorithm.checkType(realValue, behaviorConfig.getIclass());
        BehaviorInput<Algorithm, AlgorithmConfig> nextBehaviorInput = new BehaviorInput<>(ctx, behaviorConfig, realValue);
        return algorithm.apply(nextBehaviorInput);
    }
}
