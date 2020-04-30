package com.github.nijian.jkeel.concept;

import com.github.nijian.jkeel.concept.config.Link;
import com.github.nijian.jkeel.concept.config.MappingConfig;

import java.util.function.Function;

public abstract class Behavior<R> implements Function<BehaviorInput, R> {

    @Override
    public R apply(BehaviorInput behaviorInput) {

        ConfigItem<?> currentBehaviorConfig = behaviorInput.getConfigItem();

        //basic config check
        ConfigItem<?> startConceptConfig = getNextConceptConfig(behaviorInput);
        if (startConceptConfig == null) {
            throw new RuntimeException("xxxx");
        }

        Object convertedObject = behaviorInput.convert();

        //validation

        ServiceContext<?> ctx = behaviorInput.getContext();
        Behavior<?> startBehavior = startConceptConfig.getConcept();
        BehaviorInput startBehaviorInput = new BehaviorInput(ctx, startConceptConfig, convertedObject);

        return handleResult(ctx, currentBehaviorConfig, execute0(startBehavior, startBehaviorInput));
    }

    protected R handleResult(ServiceContext<?> ctx, ConfigItem<?> currentBehaviorConfig, Object value) {
        MappingConfig outMappingConfig = currentBehaviorConfig.getOutMapping();
        if (outMappingConfig == null) {
            return (R) value; //default implementation
        }
        Mapping<?> outMapping = outMappingConfig.getConcept();
        BehaviorInput behaviorInput = new BehaviorInput(ctx, outMappingConfig, value);
        return (R) outMapping.apply(behaviorInput);
    }

    private ConfigItem<?> getNextConceptConfig(BehaviorInput behaviorInput) {

        ConfigItem<?> currentConceptConfig = behaviorInput.getConfigItem();
        Link currentConceptLink = currentConceptConfig.getLink();
        if (currentConceptLink == null) {
            return null;
        }
        ConfigItem<?> nextConceptConfig = currentConceptLink.getConceptConfig();
        return nextConceptConfig;
    }

    private Object execute0(Behavior<?> behavior, BehaviorInput input) {

        Object conceptOutput = behavior.apply(input);

        ConfigItem<?> nextConceptConfig = getNextConceptConfig(input);
        if (nextConceptConfig == null) {
            return conceptOutput;
        }

        ServiceContext<?> ctx = input.getContext();
        Behavior<?> nextBehavior = nextConceptConfig.getConcept();
        BehaviorInput nextBehaviorInput = new BehaviorInput(ctx, nextConceptConfig, conceptOutput);

        return execute0(nextBehavior, nextBehaviorInput);
    }

}