package com.github.nijian.jkeel.concept;


import com.github.nijian.jkeel.concept.config.Link;

public abstract class Service<R> extends Behavior<R> {

    @Override
    public R apply(BehaviorInput serviceInput) {

        Object convertedObject = serviceInput.convert();

        ConfigItem<?> startConceptConfig = getNextConceptConfig(serviceInput);
        if (startConceptConfig == null) {
            throw new RuntimeException("Service is not configured correctly");
        }

        ServiceContext<?> ctx = serviceInput.getContext();
        Behavior<?> startBehavior = startConceptConfig.getConcept();
        BehaviorInput startBehaviorInput = new BehaviorInput(ctx, startConceptConfig, convertedObject);

        return handleResult(execute0(startBehavior, startBehaviorInput));
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

    protected void checkInput(BehaviorInput serviceInput) {

    }

    protected abstract <F> R handleResult(F f);

    private ConfigItem<?> getNextConceptConfig(BehaviorInput input) {

        ConfigItem<?> currentConceptConfig = input.getConfigItem();
        Link currentConceptLink = currentConceptConfig.getLink();
        if (currentConceptLink == null) {
            return null;
        }
        ConfigItem<?> nextConceptConfig = currentConceptLink.getConceptConfig();
        return nextConceptConfig;
    }
}
