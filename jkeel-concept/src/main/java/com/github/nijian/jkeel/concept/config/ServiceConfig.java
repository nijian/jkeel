package com.github.nijian.jkeel.concept.config;

import com.github.nijian.jkeel.concept.ConfigItem;
import com.github.nijian.jkeel.concept.Service;

import javax.xml.bind.annotation.*;
import java.util.List;

@XmlType(name = "service")
@XmlAccessorType(XmlAccessType.FIELD)
public class ServiceConfig extends ConfigItem<Service> {

    @XmlAttribute
    private boolean transactionRequired;

    @XmlElement(name = "listener")
    private List<Listener> listenerList;

    public boolean isTransactionRequired() {
        return transactionRequired;
    }

    public void setTransactionRequired(boolean transactionRequired) {
        this.transactionRequired = transactionRequired;
    }

    public List<Listener> getListenerList() {
        return listenerList;
    }

    public void setListenerList(List<Listener> listenerList) {
        this.listenerList = listenerList;
    }

    @Override
    public Service getBehavior() {
        return Service.getInstance();
    }
}
