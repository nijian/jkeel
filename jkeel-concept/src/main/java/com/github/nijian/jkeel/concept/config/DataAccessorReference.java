package com.github.nijian.jkeel.concept.config;

import com.github.nijian.jkeel.concept.BehaviorInput;
import com.github.nijian.jkeel.concept.DataAccessor;
import com.github.nijian.jkeel.concept.ServiceContext;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

@XmlType(name = "dataAccessorRef")
@XmlAccessorType(XmlAccessType.FIELD)
public class DataAccessorReference extends BehaviorReference<DataAccessorConfig> {

    @XmlAttribute
    private String ref;

    private DataAccessorConfig behaviorConfig;

    public String getRef() {
        return ref;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }

    @Override
    public DataAccessorConfig getBehaviorConfig() {
        return behaviorConfig;
    }

    @Override
    public void setBehaviorConfig(DataAccessorConfig behaviorConfig) {
        this.behaviorConfig = behaviorConfig;
    }

    @Override
    public Object execute(ServiceContext ctx, Link link, Object realValue) {
        DataAccessor dataAccessor = behaviorConfig.getBehavior();
        dataAccessor.checkType(realValue, behaviorConfig.getIclass());
        BehaviorInput<DataAccessor, DataAccessorConfig> nextBehaviorInput = new BehaviorInput<>(ctx, behaviorConfig, realValue);
        return dataAccessor.apply(nextBehaviorInput);
    }
}
