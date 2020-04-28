package com.github.nijian.jkeel.concept.config;


import com.github.nijian.jkeel.concept.ConfigItem;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlType;

@XmlType
public class Link {

    private ConfigItem<?> conceptConfig;

    public ConfigItem<?> getConceptConfig() {
        return conceptConfig;
    }

    @XmlElements({
            @XmlElement(name = "service", type = ServiceConfig.class),
            @XmlElement(name = "dataAccessor", type = DataAccessorConfig.class)})
    public void setConceptConfig(ConfigItem<?> conceptConfig) {
        this.conceptConfig = conceptConfig;
    }
}
