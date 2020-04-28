package com.github.nijian.jkeel.mapping;

import com.github.nijian.jkeel.concept.ConceptInput;
import com.github.nijian.jkeel.concept.Mapping;

public class JkeelMapping<T, R> extends Mapping<T, R> {

    @Override
    public R apply(ConceptInput<?, T> mappingConfigTConceptInput) {
        return (R) mappingConfigTConceptInput.getValue();
    }

}
