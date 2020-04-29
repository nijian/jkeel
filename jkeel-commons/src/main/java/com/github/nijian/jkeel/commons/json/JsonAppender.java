package com.github.nijian.jkeel.commons.json;


import com.github.nijian.jkeel.commons.JsonUtil;
import com.github.nijian.jkeel.concept.BehaviorInput;
import com.github.nijian.jkeel.concept.Service;
import com.github.nijian.jkeel.concept.ServiceContext;
import com.github.nijian.jkeel.concept.config.ServiceConfig;

import java.util.HashMap;
import java.util.Map;

public final class JsonAppender {

    private final ServiceContext<?> ctx;

    private final Map<String, Object> localJsonMap;

    public JsonAppender(ServiceContext<?> serviceContext) {
        this.ctx = serviceContext;
        this.localJsonMap = new HashMap<>();
    }

    public void appendBy(String serviceEntryName, String inputValue) {
        ServiceConfig serviceConfig = ctx.getServiceConfig(serviceEntryName);
        Service<?> service = serviceConfig.getConcept();
        BehaviorInput serviceInput = new BehaviorInput(ctx, serviceConfig, inputValue);
        localJsonMap.put(serviceEntryName, service.apply(serviceInput));
    }

    @Override
    public String toString() {
        return JsonUtil.map2Json(localJsonMap);
    }

}
