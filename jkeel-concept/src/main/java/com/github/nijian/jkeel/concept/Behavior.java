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

        Link backLink = backFindLink(ctx);
        if (backLink != null) {
            Link link = backLink.getLink();
            if (link != null) {
                ctx.getLinkStack().push(link);
                ConfigItem<?> backBehaviorConfig = link.getBehaviorConfig();
                Behavior<?> backBehavior = backBehaviorConfig.getBehavior();
                BehaviorInput backBehaviorInput = new BehaviorInput(ctx, backBehaviorConfig, obj);
                R r = (R) backBehavior.apply(backBehaviorInput);
                if(link.isVar()){
                    ctx.getServiceVariables().put(link.getRef(), r);
                }
                return r;
            }
        }

        return (R) obj;
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
                Behavior<?> nextBehavior = nextBehaviorConfig.getBehavior();
                BehaviorInput nextBehaviorInput = new BehaviorInput(ctx, nextBehaviorConfig, convertedObject);
                Object nextBehaviorValue = nextBehavior.apply(nextBehaviorInput);
                if (nextLink.isVar()) {
                    ctx.getServiceVariables().put(nextLink.getRef(), nextBehaviorValue);
                }
                return nextBehaviorValue;
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

    protected R handleResult(ServiceContext<?> ctx, ConfigItem<?> currentBehaviorConfig, Object value) {
        MappingConfig outMappingConfig = currentBehaviorConfig.getOutMapping();
        if (outMappingConfig == null) {
            return (R) value; //default implementation
        }
        Mapping<?> outMapping = outMappingConfig.getBehavior();
        BehaviorInput behaviorInput = new BehaviorInput(ctx, outMappingConfig, value);
        return (R) outMapping.apply(behaviorInput);
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