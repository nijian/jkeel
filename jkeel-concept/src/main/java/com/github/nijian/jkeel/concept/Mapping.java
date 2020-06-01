package com.github.nijian.jkeel.concept;

import com.github.nijian.jkeel.concept.config.MappingConfig;

public class Mapping extends Behavior<Mapping, MappingConfig> {

    @Override
    protected Object execute(BehaviorInput<Mapping, MappingConfig> behaviorInput) {
        return behaviorInput.getValue();
    }


}
