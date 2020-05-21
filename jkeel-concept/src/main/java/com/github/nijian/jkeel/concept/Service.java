package com.github.nijian.jkeel.concept;

public final class Service extends Behavior {

    private static final Service instance = new Service();

    private Service() {
    }

    public static Service getInstance() {
        return instance;
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
