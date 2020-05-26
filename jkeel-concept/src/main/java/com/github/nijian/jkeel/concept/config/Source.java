package com.github.nijian.jkeel.concept.config;

import com.github.nijian.jkeel.concept.ConfigItem;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

@XmlType
@XmlAccessorType(XmlAccessType.FIELD)
public class Source {

    @XmlAttribute(required = true)
    private BehaviorType type;

    @XmlAttribute
    private String ref;

    private ConfigItem<?> behaviorConfig;

    public BehaviorType getType() {
        return type;
    }

    public void setType(BehaviorType type) {
        this.type = type;
    }

    public String getRef() {
        return ref;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }

    public ConfigItem<?> getBehaviorConfig() {
        return behaviorConfig;
    }

    public void setBehaviorConfig(ConfigItem<?> behaviorConfig) {
        this.behaviorConfig = behaviorConfig;
    }
}
