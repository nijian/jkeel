package com.github.nijian.jkeel.commons;

import com.github.nijian.jkeel.concept.ServiceContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class JsonReactor {

    private static Logger logger = LoggerFactory.getLogger(JsonReactor.class);

    private final List<ServiceContext> ctxList;

    private final List<ServiceContext> parallelCtxList;

    private final Map<String, Object> localJsonMap;

    public JsonReactor() {
        ctxList = new ArrayList<>();
        parallelCtxList = new ArrayList<>();
        localJsonMap = new HashMap<>();
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
            ctxList.stream().forEach(ctx -> putInLocalJsonMap(ctx, request));
        }
        if (parallelCtxList.size() > 0) {
            parallelCtxList.parallelStream().forEach(ctx -> putInLocalJsonMap(ctx, request));
        }
        logger.info("Service list has been performed successfully");
        return JsonUtil.map2Json(localJsonMap);
    }

    private void putInLocalJsonMap(ServiceContext ctx, final Object request) {
        String key = ctx.getServiceAlias() == null ? ctx.getServiceId() : ctx.getServiceAlias();
        synchronized (localJsonMap) {
            if (localJsonMap.containsKey(key)) {
                logger.warn("The key '{}' already exists in local json map. If necessary, use service alias", key);
            }
            localJsonMap.put(key, ctx.run(request));
        }
    }

}
