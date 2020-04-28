package com.github.nijian.jkeel.concept.json;


import com.github.nijian.jkeel.commons.JsonString;
import com.github.nijian.jkeel.commons.JsonUtil;
import com.github.nijian.jkeel.concept.ConceptInput;
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
        Service<String, JsonString> service = (Service<String, JsonString>) serviceConfig.getConcept();
        ConceptInput<?, String> serviceInput = new ConceptInput<>(ctx, serviceConfig, inputValue);
        JsonString jsonResult = service.apply(serviceInput);
        localJsonMap.put(serviceEntryName, jsonResult);
    }

    @Override
    public String toString() {
        return JsonUtil.map2Json(localJsonMap);
    }

}
