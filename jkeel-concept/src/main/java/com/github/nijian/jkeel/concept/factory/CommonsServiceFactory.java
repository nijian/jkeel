package com.github.nijian.jkeel.concept.factory;

import com.github.nijian.jkeel.concept.Service;
import com.github.nijian.jkeel.concept.json.service.JsonService;
import com.github.nijian.jkeel.concept.query.service.JsonQueryService;
import com.github.nijian.jkeel.concept.spi.ServiceFactory;

public final class CommonsServiceFactory implements ServiceFactory {


    @Override
    public Service<?, ?> getService(String serviceName) {
        if (serviceName.equals("QUERY")) {
            return new JsonQueryService();
        } else if (serviceName.equals("CODE")) {
            return new JsonQueryService();
        } else if (serviceName.equals("JSON")) {
            return new JsonService();
        }

        return null;
    }
}
