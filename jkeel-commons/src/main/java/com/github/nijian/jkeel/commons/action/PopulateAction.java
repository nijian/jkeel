package com.github.nijian.jkeel.commons.action;

import com.github.nijian.jkeel.concept.Action;
import com.github.nijian.jkeel.concept.BehaviorInput;

public class PopulateAction extends Action {

    @Override
    protected Object execute(BehaviorInput behaviorInput) {
        return behaviorInput.getValue();
    }
}
