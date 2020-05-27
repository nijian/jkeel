package com.github.nijian.jkeel.concept.config;

import com.github.nijian.jkeel.concept.ConfigItem;
import com.github.nijian.jkeel.concept.Mapping;
import com.github.nijian.jkeel.concept.spi.MappingFactoryProvider;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlType;

@XmlType
public class MappingConfig extends ConfigItem<Mapping> {

    @XmlID
    @XmlAttribute
    private String id;

    @Override
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Mapping getBehavior() {
        return MappingFactoryProvider.getInstance().getMapping(getName());
    }

}
