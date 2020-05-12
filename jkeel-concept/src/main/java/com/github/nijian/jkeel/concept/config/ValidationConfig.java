package com.github.nijian.jkeel.concept.config;

import com.github.nijian.jkeel.concept.ConfigItem;
import com.github.nijian.jkeel.concept.Validation;

import javax.xml.bind.annotation.XmlType;

@XmlType
public class ValidationConfig extends ConfigItem<Validation> {
    @Override
    public Validation getBehavior() {
        return null;
    }
}
