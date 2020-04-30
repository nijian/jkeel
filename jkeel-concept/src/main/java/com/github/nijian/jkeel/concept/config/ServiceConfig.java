package com.github.nijian.jkeel.concept.config;

import com.github.nijian.jkeel.concept.ConfigItem;
import com.github.nijian.jkeel.concept.Service;
import com.github.nijian.jkeel.concept.spi.ServiceFactoryProvider;

import javax.xml.bind.annotation.XmlType;

@XmlType(name = "service")
public class ServiceConfig extends ConfigItem<Service<?>> {

    @Override
    public Service<?> getConcept() {
        return ServiceFactoryProvider.getInstance().getService(getName());
    }
}
