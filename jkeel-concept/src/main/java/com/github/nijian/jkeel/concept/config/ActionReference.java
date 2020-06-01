package com.github.nijian.jkeel.concept.config;

import com.github.nijian.jkeel.concept.Action;
import com.github.nijian.jkeel.concept.BehaviorInput;
import com.github.nijian.jkeel.concept.ServiceContext;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

@XmlType(name = "actionRef")
@XmlAccessorType(XmlAccessType.FIELD)
public final class ActionReference extends BehaviorReference<ActionConfig> {

    @XmlAttribute
    private String ref;

    private ActionConfig behaviorConfig;

    public String getRef() {
        return ref;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }

    @Override
    public ActionConfig getBehaviorConfig() {
        return behaviorConfig;
    }

    @Override
    public void setBehaviorConfig(ActionConfig behaviorConfig) {
        this.behaviorConfig = behaviorConfig;
    }

    @Override
    public Object execute(ServiceContext ctx, Link link, Object realValue) {
        Action action = behaviorConfig.getBehavior();
        action.checkType(realValue, behaviorConfig.getIclass());
        BehaviorInput<Action, ActionConfig> nextBehaviorInput = new BehaviorInput<>(ctx, behaviorConfig, realValue);
        return action.apply(nextBehaviorInput);
    }
}
