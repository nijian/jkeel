package com.github.nijian.jkeel.concept.config;

import com.github.nijian.jkeel.concept.ConfigItem;

public abstract class BehaviorReference {

    private ConfigItem<?> behaviorConfig;

    public abstract String getRef();

    public ConfigItem<?> getBehaviorConfig() {
        return behaviorConfig;
    }

    public void setBehaviorConfig(ConfigItem<?> behaviorConfig) {
        this.behaviorConfig = behaviorConfig;
    }
}
