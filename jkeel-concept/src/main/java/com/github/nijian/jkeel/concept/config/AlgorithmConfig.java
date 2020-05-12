package com.github.nijian.jkeel.concept.config;

import com.github.nijian.jkeel.concept.Algorithm;
import com.github.nijian.jkeel.concept.ConfigItem;

import javax.xml.bind.annotation.XmlType;

@XmlType(name = "algorithm")
public class AlgorithmConfig extends ConfigItem<Algorithm> {

    @Override
    public Algorithm getBehavior() {
        return null;
    }
}
