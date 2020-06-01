package com.github.nijian.jkeel.concept.config;

import com.github.nijian.jkeel.concept.BehaviorInput;
import com.github.nijian.jkeel.concept.ServiceContext;
import com.github.nijian.jkeel.concept.Validation;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

@XmlType(name = "validationRef")
@XmlAccessorType(XmlAccessType.FIELD)
public class ValidationReference extends BehaviorReference<ValidationConfig> {

    @XmlAttribute
    private String ref;

    private ValidationConfig behaviorConfig;

    public String getRef() {
        return ref;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }

    @Override
    public ValidationConfig getBehaviorConfig() {
        return behaviorConfig;
    }

    @Override
    public void setBehaviorConfig(ValidationConfig behaviorConfig) {
        this.behaviorConfig = behaviorConfig;
    }

    @Override
    public Object execute(ServiceContext ctx, Link link, Object realValue) {
        Validation validation = behaviorConfig.getBehavior();
        validation.checkType(realValue, behaviorConfig.getIclass());
        BehaviorInput<Validation, ValidationConfig> nextBehaviorInput = new BehaviorInput<>(ctx, behaviorConfig, realValue);
        return validation.apply(nextBehaviorInput);
    }
}
