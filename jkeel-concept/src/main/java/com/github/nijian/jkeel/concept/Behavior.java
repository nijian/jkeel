package com.github.nijian.jkeel.concept;

import com.github.nijian.jkeel.concept.config.Link;
import com.github.nijian.jkeel.concept.config.MappingConfig;
import com.github.nijian.jkeel.concept.config.Param;

import java.util.List;
import java.util.Stack;
import java.util.function.Function;

public abstract class Behavior implements Function<BehaviorInput, Object> {

    @Override
    public final Object apply(BehaviorInput behaviorInput) {
        ServiceContext<?> ctx = behaviorInput.getContext();
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

    protected Object execute(BehaviorInput behaviorInput) throws Exception {
        //default impl
        return behaviorInput.getValue();
    }

    private Object perform(BehaviorInput behaviorInput, ConfigItem<?> currentBehaviorConfig) {
        try {

            //validation
            if (!behaviorInput.verify()) {
                throw new BehaviorException();
            }

            //in mapping
            Object convertedObject = behaviorInput.convert();

            ServiceContext<?> ctx = behaviorInput.getContext();

            Link nextLink = nextLink(behaviorInput);
            if (nextLink != null) {
                return executeLink(ctx, nextLink, convertedObject);
            } else {
                Object result = execute(behaviorInput);

                //check return class type
                String rClassName = currentBehaviorConfig.getRclass();
                Class<?> rClz = Class.forName(rClassName);
                if (!rClz.isAssignableFrom(result.getClass())) {
                    throw new BehaviorException("please check rclass of this config item");
                }
                MappingConfig outMappingConfig = currentBehaviorConfig.getOutMapping();
                if (outMappingConfig == null) {
                    return result;
                }
                Mapping outMapping = outMappingConfig.getBehavior();
                BehaviorInput mappingBehaviorInput = new BehaviorInput(ctx, outMappingConfig, result);
                return outMapping.apply(mappingBehaviorInput);
            }
        } catch (Exception e) {
            throw new RuntimeException("xxx", e);
        }
    }

    private void storeValue(ServiceContext<?> ctx, Link link, Object value) {
        String linkId = link.getRef();
        if (link.isVar()) {
            ctx.getVars().put(linkId, value);
        }
        if (link.isOut()) {
            ctx.getOut().put(linkId, value);
        }
    }

    private Object executeLink(ServiceContext<?> ctx, Link link, Object value) {
        ctx.getLinkStack().push(link);
        ConfigItem<?> nextBehaviorConfig = link.getBehaviorConfig();
        Behavior nextBehavior = nextBehaviorConfig.getBehavior();

        //default to use value chain, if param exists, use param
        Object realValue = value;
        List<Param> paramList = link.getParamList();
        if (paramList != null && paramList.size() > 0) {
            Param param = paramList.get(0);
            if (param.getType().equals("original")) {
                realValue = ctx.getOriginalValue();
            } else {
                throw new RuntimeException("xxvc");
            }
        }
        //check input class type
        String iClassName = nextBehaviorConfig.getIclass();
        Class<?> iClz;
        try {
            iClz = Class.forName(iClassName);
        } catch (Exception e) {
            throw new BehaviorException("iclass is not exist");
        }
        if (!iClz.isAssignableFrom(realValue.getClass())) {
            throw new BehaviorException("please check iclass of this config item");
        }

        BehaviorInput nextBehaviorInput = new BehaviorInput(ctx, nextBehaviorConfig, realValue);
        return nextBehavior.apply(nextBehaviorInput);
    }

    private Link nextLink(BehaviorInput behaviorInput) {
        return behaviorInput.getConfigItem().getLink();
    }

    private Link backwardLink(ServiceContext<?> ctx) {
        Stack<Link> linkStack = ctx.getLinkStack();
        if (linkStack.size() > 0) {
            return linkStack.pop();
        }
        return null;
    }

}