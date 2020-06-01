package com.github.nijian.jkeel.concept.config;

import com.github.nijian.jkeel.concept.BehaviorInput;
import com.github.nijian.jkeel.concept.Mapping;
import com.github.nijian.jkeel.concept.ServiceContext;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

@XmlType(name = "mappingRef")
@XmlAccessorType(XmlAccessType.FIELD)
public class MappingReference extends BehaviorReference<MappingConfig> {

    @XmlAttribute
    private String ref;

    private MappingConfig behaviorConfig;

    public String getRef() {
        return ref;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }

    @Override
    public MappingConfig getBehaviorConfig() {
        return behaviorConfig;
    }

    @Override
    public void setBehaviorConfig(MappingConfig behaviorConfig) {
        this.behaviorConfig = behaviorConfig;
    }

    @Override
    public Object execute(ServiceContext ctx, Link link, Object realValue) {
        Mapping mapping = behaviorConfig.getBehavior();
        mapping.checkType(realValue, behaviorConfig.getIclass());
        BehaviorInput<Mapping, MappingConfig> nextBehaviorInput = new BehaviorInput<>(ctx, behaviorConfig, realValue);
        return mapping.apply(nextBehaviorInput);
    }
}
