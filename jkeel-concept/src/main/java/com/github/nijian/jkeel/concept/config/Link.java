package com.github.nijian.jkeel.concept.config;


import com.github.nijian.jkeel.concept.ConfigItem;

import javax.xml.bind.annotation.*;

@XmlType
@XmlAccessorType(XmlAccessType.FIELD)
public class Link {

    @XmlAttribute
    private String type;

    @XmlAttribute
    private String ref;

    private boolean var;

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

    public String getRef() {
        return ref;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }

    public boolean isVar() {
        return var;
    }

    public void setVar(boolean var) {
        this.var = var;
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
