package com.github.nijian.jkeel.concept;

import com.github.nijian.jkeel.concept.config.Link;
import com.github.nijian.jkeel.concept.config.MappingConfig;

import java.util.function.Function;

public abstract class Behavior implements Function<BehaviorInput, Object> {

    @Override
    public final Object apply(BehaviorInput behaviorInput) {
        ServiceContext<?> ctx = behaviorInput.getContext();
        ConfigItem<?> currentBehaviorConfig = behaviorInput.getConfigItem();
        ctx.onStart(currentBehaviorConfig);
        Object obj = internalExecute(behaviorInput, currentBehaviorConfig);
        ctx.onEnd(currentBehaviorConfig);

        Link backLink = backFindLink(ctx);
        if (backLink != null) {

            if (backLink.isVar()) {
                ctx.getServiceVariables().put(backLink.getRef(), obj);
            }

            Link link = backLink.getLink();
            if (link != null) {
                ctx.getLinkStack().push(link);
                ConfigItem<?> backBehaviorConfig = link.getBehaviorConfig();
                Behavior backBehavior = backBehaviorConfig.getBehavior();
                BehaviorInput backBehaviorInput = new BehaviorInput(ctx, backBehaviorConfig, obj);
                return backBehavior.apply(backBehaviorInput);
            }
        }
        return obj;
    }

    private Object internalExecute(BehaviorInput behaviorInput, ConfigItem<?> currentBehaviorConfig) {
        try {
            //in mapping
            Object convertedObject = behaviorInput.convert();

            //validation

            ServiceContext<?> ctx = behaviorInput.getContext();

            Link nextLink = getNextLink(behaviorInput);
            if (nextLink != null) {
                ctx.getLinkStack().push(nextLink);
                ConfigItem<?> nextBehaviorConfig = nextLink.getBehaviorConfig();
                Behavior nextBehavior = nextBehaviorConfig.getBehavior();
                BehaviorInput nextBehaviorInput = new BehaviorInput(ctx, nextBehaviorConfig, convertedObject);
                return nextBehavior.apply(nextBehaviorInput);
            } else {
                return handleResult(ctx, currentBehaviorConfig, execute(ctx, behaviorInput));
            }
        } catch (Exception e) {
            throw new RuntimeException("xxx", e);
        }
    }

    protected Object execute(ServiceContext<?> ctx, BehaviorInput behaviorInput) throws Exception {
        //default impl
        return behaviorInput.getValue();
    }

    protected Object handleResult(ServiceContext<?> ctx, ConfigItem<?> currentBehaviorConfig, Object value) {
        MappingConfig outMappingConfig = currentBehaviorConfig.getOutMapping();
        if (outMappingConfig == null) {
            return value; //default implementation
        }
        Mapping outMapping = outMappingConfig.getBehavior();
        BehaviorInput behaviorInput = new BehaviorInput(ctx, outMappingConfig, value);
        return outMapping.apply(behaviorInput);
    }

    private Link getNextLink(BehaviorInput behaviorInput) {
        ConfigItem<?> currentBehaviorConfig = behaviorInput.getConfigItem();
        return currentBehaviorConfig.getLink();
    }

    private Link backFindLink(ServiceContext<?> ctx) {
        try {
            return ctx.getLinkStack().pop();
        } catch (Exception e) {
            return null;
        }
    }

}