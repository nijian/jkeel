package com.github.nijian.jkeel.commons.factory;

import com.github.nijian.jkeel.commons.service.JsonService;
import com.github.nijian.jkeel.concept.Service;
import com.github.nijian.jkeel.concept.spi.ServiceFactory;

public final class CommonsServiceFactory implements ServiceFactory {

    @Override
    public Service getService(String serviceName) {
        if (serviceName.equals("JSON")) {
            return new JsonService();
        }

        return null;
    }
}
