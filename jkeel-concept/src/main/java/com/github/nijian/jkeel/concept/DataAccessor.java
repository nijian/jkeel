package com.github.nijian.jkeel.concept;

public abstract class DataAccessor<R> extends Behavior {

    @Override
    protected abstract R execute(BehaviorInput behaviorInput) throws Exception;

    protected abstract void sync(ServiceContext<?> ctx);

    protected void syncData(ServiceContext<?> ctx) {
        if (ctx.isUseJPA() && ctx.isNeedSyncData()) {
            sync(ctx);
            ctx.setNeedSyncData(false);
        }
    }

}