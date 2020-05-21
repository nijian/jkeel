package com.github.nijian.jkeel.concept;

public class Mapping extends Behavior {

    @Override
    protected Object execute(BehaviorInput behaviorInput) {
        return behaviorInput.getValue();
    }
}
