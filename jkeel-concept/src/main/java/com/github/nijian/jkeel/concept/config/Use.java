package com.github.nijian.jkeel.concept.config;

import javax.xml.bind.annotation.*;

@XmlType
@XmlAccessorType(XmlAccessType.FIELD)
public class Use {

    @XmlElements(value = {
            @XmlElement(name = "actionRef",
                    type = ActionReference.class),
            @XmlElement(name = "dataAccessorRef",
                    type = DataAccessorReference.class),
            @XmlElement(name = "serviceRef",
                    type = ServiceReference.class),
    })
    private BehaviorReference behaviorReference;

    public BehaviorReference getBehaviorReference() {
        return behaviorReference;
    }

    public void setBehaviorReference(BehaviorReference behaviorReference) {
        this.behaviorReference = behaviorReference;
    }
}
