package com.github.nijian.jkeel.concept;


import com.github.nijian.jkeel.concept.config.Link;

public abstract class Service<M extends Manager, T, R> extends ManagedConcept<M, T, R> {

    @Override
    public R apply(ConceptInput<M, T> serviceInput) {

        // check input
        checkInput(serviceInput);

        M manager = serviceInput.getManager();
        ConfigItem<?> serviceConfig = serviceInput.getConfigItem();

        Link link = serviceConfig.getLink();
        ConfigItem startConceptConfig = link.getConceptConfig();
        String startConceptEntryName = startConceptConfig.getEntryName();
        Entry startConceptEntry = Entry.parse(startConceptEntryName);

        T startConceptInputValue = serviceInput.getValue();

        ManagedConcept<M, T, ?> startConcept = startConceptConfig.getConcept();

        ConceptInput<M, T> startConceptInput = new ConceptInput<>();
        startConceptInput.setManager(manager);
        startConceptInput.setConfigItem(startConceptConfig);
        startConceptInput.setValue(startConceptInputValue);

        return handleResult(execute(startConcept, startConceptInput));

    }

    private <M extends Manager, E, F> F execute(ManagedConcept<M, E, F> concept, ConceptInput<M, E> input) {

        F f = concept.apply(input);

        ConfigItem currentConceptConfig = input.getConfigItem();
        Link currentConceptLink = currentConceptConfig.getLink();
        if (currentConceptLink == null) {
            return f;
        }
        ConfigItem nextConceptConfig = currentConceptLink.getConceptConfig();
        String nextConceptName = nextConceptConfig.getName();
        ConceptInput<M, F> nextConceptInput = new ConceptInput<>();
        nextConceptInput.setManager(input.getManager());
        nextConceptInput.setConfigItem(nextConceptConfig);
        nextConceptInput.setValue(f);

        ManagedConcept<M, F, ?> nextConcept = nextConceptConfig.getConcept();

        return (F) execute(nextConcept, nextConceptInput);
    }

    protected void checkInput(ConceptInput<M, T> serviceInput) {

    }

    protected abstract <F> R handleResult(F f);
}
