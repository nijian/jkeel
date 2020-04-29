package com.github.nijian.jkeel.concept.config;

import com.github.nijian.jkeel.concept.ConfigItem;
import com.github.nijian.jkeel.concept.Mapping;
import com.github.nijian.jkeel.concept.spi.MappingFactoryProvider;

import javax.xml.bind.annotation.XmlType;

@XmlType
public class MappingConfig extends ConfigItem<Mapping<?>> {

    public Mapping<?> getConcept() {
        return MappingFactoryProvider.getInstance().getMapping(getName());
    }

}
