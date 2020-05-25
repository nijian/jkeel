package com.github.nijian.jkeel.query.data;

import com.github.nijian.jkeel.concept.BehaviorInput;
import com.github.nijian.jkeel.concept.DataAccessor;
import com.github.nijian.jkeel.concept.ServiceContext;
import com.github.nijian.jkeel.query.entity.EntityIdentifier;

public abstract class QueryForObject extends DataAccessor<Object> {

    @Override
    public final Object execute(BehaviorInput behaviorInput) {
        ServiceContext ctx = behaviorInput.getContext();
        preLoad(ctx);

        EntityIdentifier entityIdentifier = (EntityIdentifier) behaviorInput.getValue();
        Class<?> returnClass = behaviorInput.getConfigItem().getRclz();
        return doLoad(ctx, returnClass, entityIdentifier);
    }

    protected abstract Object doLoad(ServiceContext ctx, Class<?> returnClass, EntityIdentifier entityIdentifier);

    protected abstract void preLoad(ServiceContext ctx);

}
