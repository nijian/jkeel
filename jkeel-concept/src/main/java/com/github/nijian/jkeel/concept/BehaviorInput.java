package com.github.nijian.jkeel.concept;

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
        BehaviorInput behaviorInput = new BehaviorInput(ctx, configItem.getMapping(), value);
        Mapping<?> mapping = configItem.getMapping().getConcept();
        return mapping.apply(behaviorInput);
    }
}
