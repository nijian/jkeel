package com.github.nijian.jkeel.concept.config;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

@XmlType(name = "dataAccessorRef")
@XmlAccessorType(XmlAccessType.FIELD)
public class DataAccessorReference extends BehaviorReference {

    @XmlAttribute
    private String ref;

    public String getRef() {
        return ref;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }

}
