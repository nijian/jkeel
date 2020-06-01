package com.github.nijian.jkeel.concept.config;

import com.github.nijian.jkeel.concept.BehaviorInput;
import com.github.nijian.jkeel.concept.Code;
import com.github.nijian.jkeel.concept.ServiceContext;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

@XmlType(name = "codeRef")
@XmlAccessorType(XmlAccessType.FIELD)
public class CodeReference extends BehaviorReference<CodeConfig> {

    @XmlAttribute
    private String ref;

    private CodeConfig behaviorConfig;

    public String getRef() {
        return ref;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }

    @Override
    public CodeConfig getBehaviorConfig() {
        return behaviorConfig;
    }

    @Override
    public void setBehaviorConfig(CodeConfig behaviorConfig) {
        this.behaviorConfig = behaviorConfig;
    }

    @Override
    public Object execute(ServiceContext ctx, Link link, Object realValue) {
        Code code = behaviorConfig.getBehavior();
        code.checkType(realValue, behaviorConfig.getIclass());
        BehaviorInput<Code, CodeConfig> nextBehaviorInput = new BehaviorInput<>(ctx, behaviorConfig, realValue);
        return code.apply(nextBehaviorInput);
    }
}
