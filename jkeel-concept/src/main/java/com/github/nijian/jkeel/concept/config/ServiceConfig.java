package com.github.nijian.jkeel.concept.config;

import com.github.nijian.jkeel.concept.ConfigItem;
import com.github.nijian.jkeel.concept.Service;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

@XmlType(name = "service")
@XmlAccessorType(XmlAccessType.FIELD)
public class ServiceConfig extends ConfigItem<Service> {

    @XmlAttribute
    private boolean transactionRequired;

    public boolean isTransactionRequired() {
        return transactionRequired;
    }

    public void setTransactionRequired(boolean transactionRequired) {
        this.transactionRequired = transactionRequired;
    }

    @Override
    public Service getBehavior() {
        return Service.getInstance();
    }
}
