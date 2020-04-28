package com.github.nijian.jkeel.concept;

public final class ConceptInput<M extends Manager, V> {

    private final ServiceContext<M, ?> ctx;

    private final ConfigItem<?> configItem;

    private V value;

    public ConceptInput(ServiceContext<M, ?> ctx, ConfigItem<?> configItem) {
        this.ctx = ctx;
        this.configItem = configItem;
    }

    public ServiceContext<M, ?> getContext() {
        return ctx;
    }

    public ConfigItem<?> getConfigItem() {
        return configItem;
    }

    public V getValue() {
        return value;
    }

    public void setValue(V value) {
        //check value, validation
        this.value = value;
    }
}
