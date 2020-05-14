package com.github.nijian.jkeel.concept;

public abstract class DataAccessor<R> extends Behavior {

    @Override
    protected abstract R execute(BehaviorInput behaviorInput) throws Exception;

}
