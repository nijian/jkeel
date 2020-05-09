package com.github.nijian.jkeel.concept;

import com.github.nijian.jkeel.concept.config.Link;
import com.github.nijian.jkeel.concept.config.MappingConfig;
import com.github.nijian.jkeel.concept.runtime.RTO;

import java.util.function.Function;

public abstract class Behavior<R> implements Function<BehaviorInput, R> {

    @Override
    public final R apply(BehaviorInput behaviorInput) {

        try {
            long startTime = System.currentTimeMillis();

            ServiceContext<?> ctx = behaviorInput.getContext();
            RTO currentRTO = ctx.getCurrentRTO();

            ConfigItem<?> currentBehaviorConfig = behaviorInput.getConfigItem();
            currentRTO.setId(currentBehaviorConfig.getId());

            //in mapping
            Object convertedObject = behaviorInput.convert(currentRTO);
            ctx.setCurrentRTO(currentRTO);

            //validation

            // for service, action, do nothing
            R r = handleResult(ctx, currentBehaviorConfig, execute(ctx, behaviorInput));

            //next config check
            ConfigItem<?> nextBehaviorConfig = getNextBehaviorConfig(behaviorInput);
            if (nextBehaviorConfig == null) {
                currentRTO.setExecutionTime(System.currentTimeMillis() - startTime);
                return r;
            }

            Behavior<?> nextBehavior = nextBehaviorConfig.getBehavior();
            BehaviorInput nextBehaviorInput = new BehaviorInput(ctx, nextBehaviorConfig, convertedObject);

            //runtime record
            RTO childRTO = new RTO();
            childRTO.setId(nextBehaviorConfig.getId());
            currentRTO.setChild(childRTO);
            ctx.setCurrentRTO(childRTO);

            Object obj = nextBehavior.apply(nextBehaviorInput);

            ctx.setCurrentRTO(currentRTO);
            currentRTO.setExecutionTime(System.currentTimeMillis() - startTime);
            return (R) obj;
        } catch (Exception e) {
            throw new RuntimeException("va");
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
            if (nextLink != null) {
                return nextLink.getBehaviorConfig();
            }
            return null;
        } else {
            ctx.getLinkStack().push(currentBehaviorLink);
        }
        ConfigItem<?> nextBehaviorConfig = currentBehaviorLink.getBehaviorConfig();
        return nextBehaviorConfig;
    }

    private Link backFindLink(ServiceContext<?> ctx) {
        try {
            return ctx.getLinkStack().pop();
        } catch (Exception e) {
            return null;
        }
    }

}