package com.github.nijian.jkeel.concept;

import com.github.nijian.jkeel.concept.config.MappingConfig;

public final class BehaviorInput {

    private final ServiceContext<?> ctx;

    private final ConfigItem<?> configItem;

    private final Object value;

    public BehaviorInput(ServiceContext<?> ctx, ConfigItem<?> configItem, Object value) {
        this.ctx = ctx;
        this.configItem = configItem;
        this.value = value;
    }

    public ServiceContext<?> getContext() {
        return ctx;
    }

    public ConfigItem<?> getConfigItem() {
        return configItem;
    }

    public Object getValue() {
        return value;
    }

    public Object convert() {
        MappingConfig inMappingConfig = configItem.getInMapping();
        Mapping<?> inMapping = inMappingConfig.getConcept();
        BehaviorInput behaviorInput = new BehaviorInput(ctx, inMappingConfig, value);
        return inMapping.apply(behaviorInput);
    }
}
