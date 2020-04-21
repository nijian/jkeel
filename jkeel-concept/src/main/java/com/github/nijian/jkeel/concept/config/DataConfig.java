package com.github.nijian.jkeel.concept.config;

import com.github.nijian.jkeel.concept.ConfigItem;
import com.github.nijian.jkeel.concept.Data;
import com.github.nijian.jkeel.concept.spi.DataFactoryProvider;

import javax.xml.bind.annotation.XmlType;

@XmlType(name = "data")
public class DataConfig extends ConfigItem<Data> {

    @Override
    public Data getConcept() {
        return DataFactoryProvider.getInstance().getData(getName());
    }
}