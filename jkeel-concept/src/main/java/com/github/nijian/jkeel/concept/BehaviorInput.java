package com.github.nijian.jkeel.concept;

import com.github.nijian.jkeel.concept.config.MappingConfig;
import com.github.nijian.jkeel.concept.runtime.RTO;

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

    public Object convert(RTO currentRTO) {
        MappingConfig inMappingConfig = configItem.getInMapping();
        if (inMappingConfig == null) {
            return value;
        }

        Mapping<?> inMapping = inMappingConfig.getBehavior();
        if (inMapping == null) {
            throw new RuntimeException("ffsa");
        }

        RTO inMappingRTO = new RTO();
        inMappingRTO.setId(inMappingConfig.getEntryName());
        currentRTO.setInMapping(inMappingRTO);
        ctx.setCurrentRTO(inMappingRTO);

        BehaviorInput behaviorInput = new BehaviorInput(ctx, inMappingConfig, value);
        return inMapping.apply(behaviorInput);
    }
}
