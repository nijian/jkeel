package com.github.nijian.jkeel.concept;

import com.github.nijian.jkeel.concept.config.ServiceConfig;

public final class Service extends Behavior<Service, ServiceConfig> {

    private static final Service instance = new Service();

    private Service() {
    }

    public static Service getInstance() {
        return instance;
    }

    @Override
    protected Object execute(BehaviorInput<Service, ServiceConfig> behaviorInput) {
        return behaviorInput.getValue();
    }

    //for config change
    public Object rollbackAndReapply(ServiceContext ctx) {
        return null;
        //new service context
//        ctx.destroy();

//        ServiceContext newCtx = new ServiceContext<>(ctx.getManager(), ctx.getUser(), ctx.getOriginalValue());
//        BehaviorInput newBehaviorInput = new BehaviorInput(newCtx, null, newCtx.getOriginalValue());
//        return apply(newBehaviorInput);
    }
}
