package com.github.nijian.jkeel.concept;

import com.github.nijian.jkeel.concept.config.MappingConfig;
import com.github.nijian.jkeel.concept.config.ValidationConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class BehaviorInput<T extends Behavior, C extends ConfigItem<T>> {

    private static Logger logger = LoggerFactory.getLogger(BehaviorInput.class);

    private final ServiceContext ctx;

    private final C configItem;

    private final Object value;

    public BehaviorInput(ServiceContext ctx, C configItem, Object value) {
        this.ctx = ctx;
        this.configItem = configItem;
        this.value = value;
    }

    public ServiceContext getContext() {
        return ctx;
    }

    public C getConfigItem() {
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
            logger.error("Validation('{}') is not found", validationConfig.getId());
            throw new BehaviorException("Validation is not found");
        }

        BehaviorInput<Validation, ValidationConfig> behaviorInput = new BehaviorInput<>(ctx, validationConfig, value);
        Boolean pass = (Boolean) validation.apply(behaviorInput);
        if (pass) {
            return true;
        }
        logger.error("Validation('{}') failed", validationConfig.getId());
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

        BehaviorInput<Mapping, MappingConfig> behaviorInput = new BehaviorInput<>(ctx, inMappingConfig, value);
        return inMapping.apply(behaviorInput);
    }
}
