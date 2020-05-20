package com.github.nijian.jkeel.biz.troubleshooting.algorithm;

import com.github.nijian.jkeel.biz.troubleshooting.entity.ABC;
import com.github.nijian.jkeel.commons.entity.query.QueryResult;
import com.github.nijian.jkeel.commons.entity.query.sql.SqlQuery;
import com.github.nijian.jkeel.concept.Algorithm;
import com.github.nijian.jkeel.concept.BehaviorInput;
import com.github.nijian.jkeel.concept.BehaviorProxy;
import com.github.nijian.jkeel.concept.config.AlgorithmConfig;

public class FooAlgorithm extends Algorithm {

    @Override
    protected Object execute(BehaviorInput behaviorInput) throws Exception {

        AlgorithmConfig algorithmConfig = getConfigItem(behaviorInput, AlgorithmConfig.class);

        //Behavior is stateless function. in same cases, to simplify, use BehaviorState to execute
        BehaviorProxy behaviorProxy = use(behaviorInput, "Get Contracts Data@JPA_LOAD"); //data accessor
        ABC abc = behaviorProxy.apply(new SqlQuery(), ABC.class);

        return abc;
    }
}
