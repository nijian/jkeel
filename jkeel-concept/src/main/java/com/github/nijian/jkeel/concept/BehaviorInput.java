package com.github.nijian.jkeel.concept;

import com.github.nijian.jkeel.concept.config.MappingConfig;
import com.github.nijian.jkeel.concept.config.ValidationConfig;

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

    public boolean verify() throws BehaviorException {

        ValidationConfig validationConfig = configItem.getValidation();
        if (validationConfig == null) {
            return true;
        }

        Validation validation = validationConfig.getBehavior();
        if (validation == null) {
            throw new RuntimeException("ffsa");
        }

        BehaviorInput behaviorInput = new BehaviorInput(ctx, validationConfig, value);
        Boolean pass = (Boolean) validation.apply(behaviorInput);
        if (pass) {
            return true;
        }

        return false;
    }

    public Object convert() {
        MappingConfig inMappingConfig = configItem.getInMapping();
        if (inMappingConfig == null) {
            return value;
        }

        Mapping inMapping = inMappingConfig.getBehavior();
        if (inMapping == null) {
            throw new RuntimeException("ffsa");
        }

        BehaviorInput behaviorInput = new BehaviorInput(ctx, inMappingConfig, value);
        return inMapping.apply(behaviorInput);
    }
}
