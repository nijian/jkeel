package com.github.nijian.jkeel.concept.config;

import com.github.nijian.jkeel.concept.ConfigItem;
import com.github.nijian.jkeel.concept.Mapping;

import javax.xml.bind.annotation.XmlType;

@XmlType
public class MappingConfig extends ConfigItem<Mapping> {

    @Override
    public Mapping getConcept() {
        return null;
    }
}
