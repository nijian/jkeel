package com.github.nijian.jkeel.concept;

import com.github.nijian.jkeel.concept.config.Link;
import com.github.nijian.jkeel.concept.config.MappingConfig;

import java.util.function.Function;

public abstract class Behavior<R> implements Function<BehaviorInput, R> {

    @Override
    public final R apply(BehaviorInput behaviorInput) {
        ServiceContext<?> ctx = behaviorInput.getContext();
        ConfigItem<?> currentBehaviorConfig = behaviorInput.getConfigItem();
        ctx.onStart(currentBehaviorConfig);
        Object obj = internalExecute(behaviorInput, currentBehaviorConfig);
        ctx.onEnd(currentBehaviorConfig);
        return (R) obj;
    }

    private Object internalExecute(BehaviorInput behaviorInput, ConfigItem<?> currentBehaviorConfig) {
        try {
            //in mapping
            Object convertedObject = behaviorInput.convert();

            //validation

            ServiceContext<?> ctx = behaviorInput.getContext();
            ConfigItem<?> nextBehaviorConfig = getNextBehaviorConfig(behaviorInput);
            if (nextBehaviorConfig == null) {
                return handleResult(ctx, currentBehaviorConfig, execute(ctx, behaviorInput));
            }
            Behavior<?> nextBehavior = nextBehaviorConfig.getBehavior();
            BehaviorInput nextBehaviorInput = new BehaviorInput(ctx, nextBehaviorConfig, convertedObject);
            return nextBehavior.apply(nextBehaviorInput);
        } catch (Exception e) {
            throw new RuntimeException("xxx", e);
        }
    }

    protected Object execute(ServiceContext<?> ctx, BehaviorInput behaviorInput) throws Exception {
        //default impl
        return behaviorInput.getValue();
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
        ServiceContext<?> ctx = behaviorInput.getContext();
        ConfigItem<?> currentBehaviorConfig = behaviorInput.getConfigItem();
        Link currentBehaviorLink = currentBehaviorConfig.getLink();
        if (currentBehaviorLink == null) {
            Link nextLink = backFindLink(ctx);
            if (nextLink != null && nextLink.getLink() != null) {
                ConfigItem<?> nextBehaviorConfig = nextLink.getLink().getBehaviorConfig();
                return nextBehaviorConfig;
            }
            return null;
        } else {
            ctx.getLinkStack().push(currentBehaviorLink);
            ConfigItem<?> nextBehaviorConfig = currentBehaviorLink.getBehaviorConfig();
            return nextBehaviorConfig;
        }
    }

    private Link backFindLink(ServiceContext<?> ctx) {
        try {
            return ctx.getLinkStack().pop();
        } catch (Exception e) {
            return null;
        }
    }

}