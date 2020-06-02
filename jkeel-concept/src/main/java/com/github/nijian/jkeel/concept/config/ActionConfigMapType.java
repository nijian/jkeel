package com.github.nijian.jkeel.concept.config;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
public class ActionConfigMapType {

    @XmlElement(name = "action")
    private List<ActionConfig> actionConfigList;

    List<ActionConfig> getActionConfigList() {
        return actionConfigList;
    }

    public void setActionConfigList(List<ActionConfig> actionConfigList) {
        this.actionConfigList = actionConfigList;
    }
}
