package com.github.nijian.jkeel.concept.config;

import com.github.nijian.jkeel.concept.ConfigItem;
import com.github.nijian.jkeel.concept.ServiceContext;

public abstract class BehaviorReference<T extends ConfigItem> {

    public abstract String getRef();

    public abstract T getBehaviorConfig();

    public abstract void setBehaviorConfig(T behaviorConfig);

    public abstract Object execute(ServiceContext ctx, Link link, Object realValue);

}
