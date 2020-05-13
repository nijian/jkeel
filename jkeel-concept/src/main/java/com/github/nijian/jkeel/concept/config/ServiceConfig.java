package com.github.nijian.jkeel.concept.config;

import com.github.nijian.jkeel.concept.ConfigItem;
import com.github.nijian.jkeel.concept.Service;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

@XmlType(name = "service")
@XmlAccessorType(XmlAccessType.FIELD)
public class ServiceConfig extends ConfigItem<Service> {

    @Override
    public Service getBehavior() {
        return Service.getInstance();
    }
}
