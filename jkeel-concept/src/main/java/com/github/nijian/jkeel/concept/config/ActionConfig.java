package com.github.nijian.jkeel.concept.config;

import com.github.nijian.jkeel.concept.Action;
import com.github.nijian.jkeel.concept.ConfigItem;

import javax.xml.bind.annotation.XmlType;

@XmlType(name = "action")
public class ActionConfig extends ConfigItem<Action<?>> {

    @Override
    public Action<?> getConcept() {
        return null;
    }

}
