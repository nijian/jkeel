package com.github.nijian.jkeel.concept.config;

import com.github.nijian.jkeel.concept.BehaviorInput;
import com.github.nijian.jkeel.concept.Service;
import com.github.nijian.jkeel.concept.ServiceContext;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

@XmlType(name = "serviceRef")
@XmlAccessorType(XmlAccessType.FIELD)
public class ServiceReference extends BehaviorReference<ServiceConfig> {

    @XmlAttribute
    private String ref;

    private ServiceConfig behaviorConfig;

    public String getRef() {
        return ref;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }

    @Override
    public ServiceConfig getBehaviorConfig() {
        return behaviorConfig;
    }

    @Override
    public void setBehaviorConfig(ServiceConfig behaviorConfig) {
        this.behaviorConfig = behaviorConfig;
    }

    @Override
    public Object execute(ServiceContext ctx, Link link, Object realValue) {
        Service service = behaviorConfig.getBehavior();
        service.checkType(realValue, behaviorConfig.getIclass());
        BehaviorInput<Service, ServiceConfig> nextBehaviorInput = new BehaviorInput<>(ctx, behaviorConfig, realValue);
        return service.apply(nextBehaviorInput);
    }
}
