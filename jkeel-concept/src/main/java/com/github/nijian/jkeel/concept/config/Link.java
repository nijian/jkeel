package com.github.nijian.jkeel.concept.config;


import com.github.nijian.jkeel.concept.ConfigItem;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlType;

@XmlType
public class Link {

    private ConfigItem<?> behaviorConfig;

    public ConfigItem<?> getBehaviorConfig() {
        return behaviorConfig;
    }

    @XmlElements({
            @XmlElement(name = "service", type = ServiceConfig.class),
            @XmlElement(name = "dataAccessor", type = DataAccessorConfig.class)})
    public void setBehaviorConfig(ConfigItem<?> behaviorConfig) {
        this.behaviorConfig = behaviorConfig;
    }
}
