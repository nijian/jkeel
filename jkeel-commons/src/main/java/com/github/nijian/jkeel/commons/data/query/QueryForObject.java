package com.github.nijian.jkeel.commons.data.query;

import com.github.nijian.jkeel.concept.BehaviorInput;
import com.github.nijian.jkeel.concept.DataAccessor;
import com.github.nijian.jkeel.concept.ServiceContext;

public abstract class QueryForObject<R> extends DataAccessor<R> {

    @Override
    protected abstract R execute(ServiceContext<?> ctx, BehaviorInput behaviorInput);

}
