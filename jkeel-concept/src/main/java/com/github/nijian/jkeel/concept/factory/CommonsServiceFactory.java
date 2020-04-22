package com.github.nijian.jkeel.concept.factory;

import com.github.nijian.jkeel.concept.Manager;
import com.github.nijian.jkeel.concept.Service;
import com.github.nijian.jkeel.concept.query.service.JsonQueryService;
import com.github.nijian.jkeel.concept.spi.ServiceFactory;

public final class CommonsServiceFactory implements ServiceFactory {


    @Override
    public Service<? extends Manager, ?, ?> getService(String serviceName) {
        if (serviceName.equals("QUERY")) {
            return new JsonQueryService();
        } else if (serviceName.equals("CODE")) {
            return new JsonQueryService();
        }

        return null;
    }


}
