package com.github.nijian.jkeel.biz.troubleshooting.algorithm;

import com.github.nijian.jkeel.biz.troubleshooting.entity.ABC;
import com.github.nijian.jkeel.concept.Algorithm;
import com.github.nijian.jkeel.concept.BehaviorInput;
import com.github.nijian.jkeel.concept.BehaviorProxy;
import com.github.nijian.jkeel.concept.config.AlgorithmConfig;

public class FooAlgorithm extends Algorithm {

    @Override
    protected Object execute(BehaviorInput<Algorithm, AlgorithmConfig> behaviorInput) {

        AlgorithmConfig algorithmConfig = behaviorInput.getConfigItem();

        ABC abc = new ABC();
        abc.setName("xxx");

        //Behavior is stateless function. in same cases, to simplify, use BehaviorState to execute
        BehaviorProxy behaviorProxy = use(behaviorInput, "save contract info@JPA_PERSIST"); //data accessor
        ABC ok = behaviorProxy.apply(abc, ABC.class);

        return ok;
    }
}
