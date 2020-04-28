package com.github.nijian.jkeel.concept;

public final class ConceptInput<M extends Manager, V> {

    private final ServiceContext<M> ctx;

    private final ConfigItem<?> configItem;

    private final V value;

    public ConceptInput(ServiceContext<M> ctx, ConfigItem<?> configItem, V value) {
        this.ctx = ctx;
        this.configItem = configItem;
        this.value = value;
    }

    public ServiceContext<M> getContext() {
        return ctx;
    }

    public ConfigItem<?> getConfigItem() {
        return configItem;
    }

    public V getValue() {
        return value;
    }
}
