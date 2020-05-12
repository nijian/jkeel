package com.github.nijian.jkeel.concept.config;

import com.github.nijian.jkeel.concept.Action;
import com.github.nijian.jkeel.concept.ConfigItem;
import com.github.nijian.jkeel.concept.spi.ActionFactoryProvider;

import javax.xml.bind.annotation.XmlType;

@XmlType(name = "action")
public class ActionConfig extends ConfigItem<Action> {

    @Override
    public Action getBehavior() {
        return ActionFactoryProvider.getInstance().getAction(getName());
    }

}
