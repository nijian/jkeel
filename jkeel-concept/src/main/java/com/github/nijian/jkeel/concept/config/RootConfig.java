package com.github.nijian.jkeel.concept.config;

import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlRootElement(name = "root",namespace = "https://github.com/nijian/jkeel")
@XmlType(name = "rootConfig")
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
