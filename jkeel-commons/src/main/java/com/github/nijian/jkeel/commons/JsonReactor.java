package com.github.nijian.jkeel.commons;

import com.github.nijian.jkeel.concept.ServiceContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public final class JsonReactor {

    private static Logger logger = LoggerFactory.getLogger(JsonReactor.class);

    private final List<ServiceContext> ctxList;

    private final List<ServiceContext> parallelCtxList;

    private final Map<String, Object> localJsonMap;

    public JsonReactor() {
        ctxList = new ArrayList<>();
        parallelCtxList = new ArrayList<>();
        localJsonMap = new ConcurrentHashMap<>();
    }

    public void appendContext(ServiceContext ctx) {
        if (ctx.getServiceConfig().isTransactionRequired()) {
            ctxList.add(ctx);
        } else {
            parallelCtxList.add(ctx);
        }
    }

    public String responseTo(final Object request) {
        logger.info("Service list size is:{} and parallel list size is:{}", ctxList.size(), parallelCtxList.size());
        if (ctxList.size() > 0) {
            ctxList.stream().forEach(ctx ->
                    localJsonMap.put(ctx.getServiceAlias() == null ? ctx.getServiceId() : ctx.getServiceAlias(), ctx.run(request)));
        }
        if (parallelCtxList.size() > 0) {
            parallelCtxList.parallelStream().forEach(ctx ->
                    localJsonMap.put(ctx.getServiceAlias() == null ? ctx.getServiceId() : ctx.getServiceAlias(), ctx.run(request)));
        }
        logger.info("Service list has been performed successfully");
        return JsonUtil.map2Json(localJsonMap);
    }

}
