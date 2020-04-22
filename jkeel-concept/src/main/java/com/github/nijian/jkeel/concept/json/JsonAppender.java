package com.github.nijian.jkeel.concept.json;


import com.github.nijian.jkeel.commons.JsonString;
import com.github.nijian.jkeel.commons.JsonUtil;
import com.github.nijian.jkeel.concept.*;
import com.github.nijian.jkeel.concept.config.ServiceConfig;
import com.github.nijian.jkeel.concept.config.ServicesConfig;
import com.github.nijian.jkeel.concept.query.entity.Query;

import javax.xml.bind.JAXBContext;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;

public final class JsonAppender<M extends Manager, C extends Context<M>> implements ContextAware {

    private final C context;

    private final Map<String, Object> jsonMap;

    public JsonAppender(C context) {
        this.context = context;
        this.jsonMap = new HashMap<>();
    }

    public <T> void appendBy(String serviceEntryName, T inputValue) {

        Entry entry = Entry.parse(serviceEntryName);

        String serviceName = entry.getConceptName();
        String configName = entry.getConfigName();

        ServicesConfig servicesConfig = null;

        try {
            JAXBContext context = JAXBContext.newInstance(ServicesConfig.class);
            servicesConfig = (ServicesConfig) context.createUnmarshaller()
                    .unmarshal(new FileReader("c:/temp/service-config.xml"));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        ServiceConfig serviceConfig = servicesConfig.getServiceConfigMap().get("@CODE");

        M manager = context.getManager();
        Service<M, T, JsonString> service = (Service<M, T, JsonString>)serviceConfig.getConcept();

        ConceptInput<M, T> serviceInput = new ConceptInput<>();
        serviceInput.setManager(manager);
        serviceInput.setConfigItem(serviceConfig);
        //do mapping
        Query query = new Query();
//        query.setSelect("");
        serviceInput.setValue(inputValue);

        JsonString jsonResult = service.apply(serviceInput);

        jsonMap.put(configName == null ? serviceName : configName, jsonResult);
    }

    @Override
    public String toString() {
        return JsonUtil.map2Json(jsonMap);
    }

}
