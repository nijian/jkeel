package com.github.nijian.jkeel.concept.config;


import com.github.nijian.jkeel.concept.ConfigItem;

import javax.xml.bind.annotation.*;
import java.util.List;

@XmlType
@XmlAccessorType(XmlAccessType.FIELD)
public class Link {

    @XmlAttribute
    private String id;

    @XmlAttribute(required = true)
    private BehaviorType type;

    @XmlAttribute
    private String ref;

    @XmlAttribute
    private boolean var;

    @XmlAttribute
    private boolean out;

    @XmlElement(name = "param")
    private List<Param> paramList;

    private ConfigItem<?> behaviorConfig;

    @XmlElement
    private Link link;

    private Decision decision;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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

    public boolean isVar() {
        return var;
    }

    public void setVar(boolean var) {
        this.var = var;
    }

    public boolean isOut() {
        return out;
    }

    public void setOut(boolean out) {
        this.out = out;
    }

    public List<Param> getParamList() {
        return paramList;
    }

    public void setParamList(List<Param> paramList) {
        this.paramList = paramList;
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
