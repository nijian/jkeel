package com.github.nijian.jkeel.concept.config;

import javax.xml.bind.annotation.*;

@XmlType(name = "listener")
@XmlAccessorType(XmlAccessType.FIELD)
public class Listener {

    @XmlElements(value = {
            @XmlElement(name = "actionRef",
                    type = ActionReference.class),
            @XmlElement(name = "dataAccessorRef",
                    type = DataAccessorReference.class),
            @XmlElement(name = "serviceRef",
                    type = ServiceReference.class),
            @XmlElement(name = "codeRef",
                    type = CodeReference.class),
    })
    private BehaviorReference behaviorReference;

    public BehaviorReference getBehaviorReference() {
        return behaviorReference;
    }

    public void setBehaviorReference(BehaviorReference behaviorReference) {
        this.behaviorReference = behaviorReference;
    }
}
