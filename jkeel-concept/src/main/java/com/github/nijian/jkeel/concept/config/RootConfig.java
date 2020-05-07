package com.github.nijian.jkeel.concept.config;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlRootElement(name = "root")
@XmlAccessorType(XmlAccessType.FIELD)
public class RootConfig {

    @XmlElement(name = "behaviors")
    @XmlJavaTypeAdapter(BehaviorsConfigAdapter.class)
    private BehaviorsConfig behaviorsConfig;

    public BehaviorsConfig getBehaviorsConfig() {
        return behaviorsConfig;
    }

    public void setBehaviorsConfig(BehaviorsConfig behaviorsConfig) {
        this.behaviorsConfig = behaviorsConfig;
    }
}
