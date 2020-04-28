package com.github.nijian.jkeel.concept.json;


import com.github.nijian.jkeel.commons.JsonString;
import com.github.nijian.jkeel.commons.JsonUtil;
import com.github.nijian.jkeel.concept.*;
import com.github.nijian.jkeel.concept.config.ServiceConfig;

import java.util.HashMap;
import java.util.Map;

public final class JsonAppender<M extends Manager, T extends Org> {

    private final ServiceContext<M, T> serviceContext;

    private final Map<String, Object> localJsonMap;

    public JsonAppender(ServiceContext<M, T> serviceContext) {
        this.serviceContext = serviceContext;
        this.localJsonMap = new HashMap<>();
    }

    public void appendBy(String serviceEntryName, String inputValue) {

        Entry entry = Entry.parse(serviceEntryName);
        String serviceName = entry.getConceptName();
        String configName = entry.getConfigName();

        ServiceConfig serviceConfig = serviceContext.getServiceConfig(serviceEntryName);
        Service<String, JsonString> service = (Service<String, JsonString>) serviceConfig.getConcept();

        //prepare input
        ConceptInput<M, String> serviceInput = new ConceptInput<>(serviceContext, serviceConfig);
        //do mapping
        serviceInput.setValue(inputValue);

        JsonString jsonResult = service.apply(serviceInput);
        localJsonMap.put(configName == null ? serviceName : configName, jsonResult);
    }

    @Override
    public String toString() {
        return JsonUtil.map2Json(localJsonMap);
    }

}
