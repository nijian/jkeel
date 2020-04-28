package com.github.nijian.jkeel.concept;

public class ConceptInput<M extends Manager, V> {

    private ServiceContext<M, ?> ctx;

    private ConfigItem<?> configItem;

    private V value;

    public ConceptInput(ServiceContext<M, ?> ctx) {
        this.ctx = ctx;
    }

    public ServiceContext<M, ?> getContext() {
        return ctx;
    }

    public ConfigItem<?> getConfigItem() {
        return configItem;
    }

    public void setConfigItem(ConfigItem<?> configItem) {
        this.configItem = configItem;
    }

    public V getValue() {
        return value;
    }

    public void setValue(V value) {
        this.value = value;
    }
}
