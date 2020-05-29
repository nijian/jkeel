package com.github.nijian.jkeel.concept;

import com.github.nijian.jkeel.concept.config.Link;
import com.github.nijian.jkeel.concept.config.MappingConfig;
import com.github.nijian.jkeel.concept.config.Param;
import com.github.nijian.jkeel.concept.config.ParamType;
import com.github.nijian.jkeel.concept.util.ClassUtilsEx;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.Stack;
import java.util.function.Function;

public abstract class Behavior<T extends Behavior, C extends ConfigItem<T>> implements Function<BehaviorInput<T, C>, Object> {

    private static Logger logger = LoggerFactory.getLogger(Behavior.class);

    @Override
    public final Object apply(BehaviorInput<T, C> behaviorInput) {
        ServiceContext ctx = behaviorInput.getContext();
        C currentBehaviorConfig = behaviorInput.getConfigItem();

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

    protected abstract Object execute(BehaviorInput<T, C> behaviorInput);

    private Object perform(BehaviorInput<T, C> behaviorInput, ConfigItem<T> currentBehaviorConfig) {
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
            if (result == null) {
                logger.warn("Nothing is produced by the behavior('{}')", currentBehaviorConfig.getId());
                return null;
            }
            checkType(result, currentBehaviorConfig.getRclass());
            MappingConfig outMappingConfig = currentBehaviorConfig.getOutMapping();
            if (outMappingConfig == null) {
                return result;
            }
            Mapping outMapping = outMappingConfig.getBehavior();
            BehaviorInput<Mapping, MappingConfig> mappingBehaviorInput = new BehaviorInput<>(ctx, outMappingConfig, result);
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

    private Object executeLink(ServiceContext ctx, Link link, final Object value) {

        ctx.getLinkStack().push(link);

        Object realValue = value;
        Param param = link.getParam();
        if (param != null) {
            ParamType paramType = param.getType();
            if (paramType.equals(ParamType.ORIGINAL)) {
                realValue = ctx.getOriginalValue();
            } else if (paramType.equals(ParamType.REFERENCE_FIELD)) {
                try {
                    Method m = value.getClass().getMethod("get" + StringUtils.capitalize(param.getValue()));
                    realValue = m.invoke(value);
                } catch (Exception e) {
                    throw new BehaviorException("check value?");
                }
            } else if (paramType.equals(ParamType.CONST_LONG)) {
                try {
                    realValue = Long.parseLong(param.getValue());
                } catch (NumberFormatException e) {
                    throw new BehaviorException("check value?");
                }
            } else { //TODO, for REFERENCE, etc.
                throw new RuntimeException("xxvc");
            }
        }

        ConfigItem<?> nextBehaviorConfig = link.getBehaviorConfig();
        Behavior nextBehavior = nextBehaviorConfig.getBehavior();
        checkType(realValue, nextBehaviorConfig.getIclass());

        BehaviorInput<Behavior, ConfigItem<Behavior>> nextBehaviorInput = new BehaviorInput(ctx, nextBehaviorConfig, realValue);
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