package com.github.nijian.jkeel.commons.data.query;

import com.github.nijian.jkeel.concept.BehaviorInput;
import com.github.nijian.jkeel.concept.DataAccessor;
import com.github.nijian.jkeel.concept.ServiceContext;

public abstract class QueryForObject extends DataAccessor<Object> {

    @Override
    public final Object execute(BehaviorInput behaviorInput) throws Exception {
        ServiceContext<?> ctx = behaviorInput.getContext();
        Class<?> returnClass = behaviorInput.getConfigItem().getRclz();
        return doLoad(ctx, returnClass, behaviorInput.getValue());
    }

    protected abstract Object doLoad(ServiceContext<?> ctx, Class<?> returnClass, Object pk);

}
