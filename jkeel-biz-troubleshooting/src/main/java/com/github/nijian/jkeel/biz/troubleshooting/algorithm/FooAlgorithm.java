package com.github.nijian.jkeel.biz.troubleshooting.algorithm;

import com.github.nijian.jkeel.concept.Algorithm;
import com.github.nijian.jkeel.concept.Behavior;
import com.github.nijian.jkeel.concept.BehaviorInput;
import com.github.nijian.jkeel.concept.config.AlgorithmConfig;

public class FooAlgorithm extends Algorithm {

    @Override
    protected Object execute(BehaviorInput behaviorInput) throws Exception {
        AlgorithmConfig algorithmConfig = (AlgorithmConfig) behaviorInput.getConfigItem();
        Behavior behavior = use(algorithmConfig, "Get Contracts Data@JPA_LOAD");
//        behavior.apply(xxx)
        return super.execute(behaviorInput);
    }
}
