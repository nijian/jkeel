package com.github.nijian.jkeel.concept.config;


import com.github.nijian.jkeel.concept.ConfigItem;

import javax.xml.bind.annotation.*;

@XmlType
@XmlAccessorType(XmlAccessType.FIELD)
public class Link {

    @XmlAttribute
    private String type;

    @XmlAttribute
    private String refid;

    private ConfigItem<?> behaviorConfig;

    @XmlElement
    private Link link;

    private Decision decision;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getRefid() {
        return refid;
    }

    public void setRefid(String refid) {
        this.refid = refid;
    }

    public ConfigItem<?> getBehaviorConfig() {
        return behaviorConfig;
    }

    public void setBehaviorConfig(ConfigItem<?> behaviorConfig) {
        this.behaviorConfig = behaviorConfig;
    }

    public Link getLink() {
        return link;
    }

    public void setLink(Link link) {
        this.link = link;
    }

    public Decision getDecision() {
        return decision;
    }

    public void setDecision(Decision decision) {
        this.decision = decision;
    }
}
