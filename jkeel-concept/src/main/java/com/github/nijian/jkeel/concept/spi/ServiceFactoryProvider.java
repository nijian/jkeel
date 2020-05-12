package com.github.nijian.jkeel.concept.spi;

import com.github.nijian.jkeel.concept.Service;

import java.util.Iterator;
import java.util.ServiceLoader;

public class ServiceFactoryProvider {

    private static ServiceFactoryProvider provider;

    private ServiceLoader<ServiceFactory> loader;

    private ServiceFactoryProvider() {
        loader = ServiceLoader.load(ServiceFactory.class);
    }

    public static ServiceFactoryProvider getInstance() {
        if (provider != null) {
            return provider;
        }

        synchronized (ServiceFactoryProvider.class) {
            if (provider == null) {
                provider = new ServiceFactoryProvider();
            }
            return provider;
        }
    }


    public Service getService(String name) {

        Service service = null;
        Iterator<ServiceFactory> factories = loader.iterator();
        while (service == null && factories.hasNext()) {
            ServiceFactory factory = factories.next();
            service = factory.getService(name);
        }

        if (service == null) {
            throw new RuntimeException("Service Not Found");
        }
        return service;
    }
}
