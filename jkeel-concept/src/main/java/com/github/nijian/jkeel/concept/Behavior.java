package com.github.nijian.jkeel.concept;

import com.github.nijian.jkeel.concept.config.Link;
import com.github.nijian.jkeel.concept.config.MappingConfig;
import com.github.nijian.jkeel.concept.config.Param;
import com.github.nijian.jkeel.concept.util.ClassUtilsEx;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Stack;
import java.util.function.Function;

public abstract class Behavior implements Function<BehaviorInput, Object> {

    private static Logger logger = LoggerFactory.getLogger(Behavior.class);

    @Override
    public final Object apply(BehaviorInput behaviorInput) {
        ServiceContext ctx = behaviorInput.getContext();
        ConfigItem<?> currentBehaviorConfig = behaviorInput.getConfigItem();

        ctx.onStart(currentBehaviorConfig);
        Object behaviorResult = perform(behaviorInput, currentBehaviorConfig);
        ctx.onEnd(currentBehaviorConfig);

        Link backwardLink = backwardLink(ctx);
        if (backwardLink != null) {
            storeValue(ctx, backwardLink, behaviorResult);
            Link link = backwardLink.getLink();
            if (link != null) {
                return executeLink(ctx, link, behaviorResult);
            }
        }
        return behaviorResult;
    }

    protected <T extends ConfigItem<?>> T getConfigItem(BehaviorInput behaviorInput, Class<T> requiredType) {
        return (T) behaviorInput.getConfigItem();
    }

    protected abstract Object execute(BehaviorInput behaviorInput);

    private Object perform(BehaviorInput behaviorInput, ConfigItem<?> currentBehaviorConfig) {
        if (!behaviorInput.verify()) {
            throw new BehaviorException("Validation failed");
        }
        Object convertedObject = behaviorInput.convert();

        ServiceContext ctx = behaviorInput.getContext();
        Link nextLink = nextLink(behaviorInput);
        if (nextLink != null) {
            return executeLink(ctx, nextLink, convertedObject);
        } else {
            Object result = execute(behaviorInput);
            checkType(result, currentBehaviorConfig.getRclass());
            MappingConfig outMappingConfig = currentBehaviorConfig.getOutMapping();
            if (outMappingConfig == null) {
                return result;
            }
            Mapping outMapping = outMappingConfig.getBehavior();
            BehaviorInput mappingBehaviorInput = new BehaviorInput(ctx, outMappingConfig, result);
            return outMapping.apply(mappingBehaviorInput);
        }
    }

    private void storeValue(ServiceContext ctx, Link link, Object value) {
        String linkId = link.getRef();
        if (link.isVar()) {
            ctx.getVars().put(linkId, value);
        }
        if (link.isOut()) {
            ctx.getOut().put(linkId, value);
        }
    }

    private Object executeLink(ServiceContext ctx, Link link, Object value) {
        ctx.getLinkStack().push(link);
        ConfigItem<?> nextBehaviorConfig = link.getBehaviorConfig();
        Behavior nextBehavior = nextBehaviorConfig.getBehavior();

        Object realValue = value;
        //TODO
        Param param = link.getParam();
        if (param != null) {
            if (param.getType().equals("original")) {
                realValue = ctx.getOriginalValue();
            } else {
                throw new RuntimeException("xxvc");
            }
        }
        checkType(realValue, nextBehaviorConfig.getIclass());

        BehaviorInput nextBehaviorInput = new BehaviorInput(ctx, nextBehaviorConfig, realValue);
        return nextBehavior.apply(nextBehaviorInput);
    }

    private Link nextLink(BehaviorInput behaviorInput) {
        return behaviorInput.getConfigItem().getLink();
    }

    private Link backwardLink(ServiceContext ctx) {
        Stack<Link> linkStack = ctx.getLinkStack();
        if (linkStack.size() > 0) {
            return linkStack.pop();
        }
        return null;
    }

    private void checkType(Object object, String toClassName) {
        try {
            if (!ClassUtilsEx.isAssignable(object, toClassName)) {
                logger.error("Expected type is ('{}') but found ('{}')", toClassName, object.getClass());
                throw new BehaviorException("Expected type is not matched with result type");
            }
        } catch (ClassNotFoundException e) {
            logger.error("Expected type '{}' is not found", toClassName);
            throw new BehaviorException("Expected type is not found");
        }
    }
}