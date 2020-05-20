package com.github.nijian.jkeel.concept;

public final class BehaviorProxy {

    private ServiceContext<?> ctx;

    private ConfigItem<?> configItem;

    private Behavior behavior;

    public BehaviorProxy(ServiceContext<?> ctx, ConfigItem<?> configItem, Behavior behavior) {
        this.ctx = ctx;
        this.configItem = configItem;
        this.behavior = behavior;
    }

    public ServiceContext<?> getCtx() {
        return ctx;
    }

    public ConfigItem<?> getConfigItem() {
        return configItem;
    }

    public Behavior getBehavior() {
        return behavior;
    }

    public <T> T apply(Object inputValue, Class<T> requiredType) {
        BehaviorInput behaviorInput = new BehaviorInput(ctx, configItem, inputValue);
        return (T) behavior.apply(behaviorInput);
    }
}
