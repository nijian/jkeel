package com.github.nijian.jkeel.query.data;

import com.github.nijian.jkeel.concept.BehaviorInput;
import com.github.nijian.jkeel.concept.DataAccessor;
import com.github.nijian.jkeel.concept.ServiceContext;

public abstract class Persist extends DataAccessor<Object> {

    @Override
    public final Object execute(BehaviorInput behaviorInput) {
        ServiceContext ctx = behaviorInput.getContext();
        prePersist(ctx);
        return doPersist(ctx, behaviorInput.getValue());
    }

    protected abstract Object doPersist(ServiceContext ctx, Object entity);

    protected abstract void prePersist(ServiceContext ctx);
}
