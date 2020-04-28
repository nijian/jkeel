package com.github.nijian.jkeel.concept;


import com.github.nijian.jkeel.concept.config.Link;

public abstract class Service<T, R> extends Concept<T, R> {

    @Override
    public R apply(ConceptInput<? extends Manager, T> serviceInput) {

        // check input
        checkInput(serviceInput);

        ConfigItem<?> startConceptConfig = getNextConceptConfig(serviceInput);
        if (startConceptConfig == null) {
            throw new RuntimeException("Service is not configured correctly");
        }

        ServiceContext<?, ?> ctx = serviceInput.getContext();
        Concept<T, ?> startConcept = startConceptConfig.getConcept();
        T startConceptInputValue = serviceInput.getValue();

        ConceptInput<?, T> startConceptInput = new ConceptInput<>(ctx, startConceptConfig);
        startConceptInput.setValue(startConceptInputValue);

        return handleResult(execute0(startConcept, startConceptInput));

    }

    private <Vx, Rx> Rx execute0(Concept<Vx, Rx> concept, ConceptInput<?, Vx> input) {

        Rx rx = concept.apply(input);

        ConfigItem<?> nextConceptConfig = getNextConceptConfig(input);
        if(nextConceptConfig==null){
            return rx;
        }

        ServiceContext<?, ?> ctx = input.getContext();
        Concept<Rx, ?> nextConcept = nextConceptConfig.getConcept();

        ConceptInput<?, Rx> nextConceptInput = new ConceptInput<>(ctx, nextConceptConfig);
        nextConceptInput.setValue(rx);

        return (Rx) execute0(nextConcept, nextConceptInput);
    }

    protected void checkInput(ConceptInput<?, T> serviceInput) {

    }

    protected abstract <F> R handleResult(F f);

    private ConfigItem<?> getNextConceptConfig(ConceptInput<?, ?> input) {

        ConfigItem<?> currentConceptConfig = input.getConfigItem();
        Link currentConceptLink = currentConceptConfig.getLink();
        if (currentConceptLink == null) {
            return null;
        }
        ConfigItem<?> nextConceptConfig = currentConceptLink.getConceptConfig();
        return nextConceptConfig;
    }
}
