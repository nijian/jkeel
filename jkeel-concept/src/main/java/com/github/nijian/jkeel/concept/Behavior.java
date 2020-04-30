package com.github.nijian.jkeel.concept;

import com.github.nijian.jkeel.concept.config.Link;
import com.github.nijian.jkeel.concept.config.MappingConfig;

import java.util.function.Function;

public abstract class Behavior<R> implements Function<BehaviorInput, R> {

    @Override
    public R apply(BehaviorInput behaviorInput) {

        ConfigItem<?> currentBehaviorConfig = behaviorInput.getConfigItem();

        //basic config check
        ConfigItem<?> startBehaviorConfig = getNextBehaviorConfig(behaviorInput);
        if (startBehaviorConfig == null) {
            throw new RuntimeException("xxxx");
        }

        Object convertedObject = behaviorInput.convert();

        //validation

        ServiceContext<?> ctx = behaviorInput.getContext();
        Behavior<?> startBehavior = startBehaviorConfig.getBehavior();
        BehaviorInput startBehaviorInput = new BehaviorInput(ctx, startBehaviorConfig, convertedObject);

        return handleResult(ctx, currentBehaviorConfig, execute0(startBehavior, startBehaviorInput));
    }

    protected R handleResult(ServiceContext<?> ctx, ConfigItem<?> currentBehaviorConfig, Object value) {
        MappingConfig outMappingConfig = currentBehaviorConfig.getOutMapping();
        if (outMappingConfig == null) {
            return (R) value; //default implementation
        }
        Mapping<?> outMapping = outMappingConfig.getBehavior();
        BehaviorInput behaviorInput = new BehaviorInput(ctx, outMappingConfig, value);
        return (R) outMapping.apply(behaviorInput);
    }

    private ConfigItem<?> getNextBehaviorConfig(BehaviorInput behaviorInput) {

        ConfigItem<?> currentBehaviorConfig = behaviorInput.getConfigItem();
        Link currentBehaviorLink = currentBehaviorConfig.getLink();
        if (currentBehaviorLink == null) {
            return null;
        }
        ConfigItem<?> nextBehaviorConfig = currentBehaviorLink.getBehaviorConfig();
        return nextBehaviorConfig;
    }

    private Object execute0(Behavior<?> behavior, BehaviorInput input) {

        Object behaviorOutput = behavior.apply(input);

        ConfigItem<?> nextBehaviorConfig = getNextBehaviorConfig(input);
        if (nextBehaviorConfig == null) {
            return behaviorOutput;
        }

        ServiceContext<?> ctx = input.getContext();
        Behavior<?> nextBehavior = nextBehaviorConfig.getBehavior();
        BehaviorInput nextBehaviorInput = new BehaviorInput(ctx, nextBehaviorConfig, behaviorOutput);

        return execute0(nextBehavior, nextBehaviorInput);
    }

}