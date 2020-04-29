package com.github.nijian.jkeel.mapping;

import com.github.nijian.jkeel.concept.BehaviorInput;
import com.github.nijian.jkeel.concept.Mapping;

public class JkeelMapping<T, R> extends Mapping<R> {

    @Override
    public R apply(BehaviorInput mappingConfigTBehaviorInput) {
        return (R) mappingConfigTBehaviorInput.getValue();
    }

}
