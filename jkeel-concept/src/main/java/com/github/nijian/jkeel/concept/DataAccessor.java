package com.github.nijian.jkeel.concept;

import com.github.nijian.jkeel.concept.config.DataAccessorConfig;

public abstract class DataAccessor<R> extends Behavior<DataAccessor, DataAccessorConfig> {

    @Override
    protected abstract R execute(BehaviorInput<DataAccessor, DataAccessorConfig> behaviorInput);

    protected abstract void sync(ServiceContext ctx);

    protected void syncData(ServiceContext ctx) {
        if (ctx.isUseJPA() && ctx.isNeedSyncData()) {
            sync(ctx);
            ctx.setNeedSyncData(false);
        }
    }

}