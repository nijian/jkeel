package com.github.nijian.jkeel.concept;

import com.github.nijian.jkeel.concept.config.ServiceConfig;
import com.github.nijian.jkeel.concept.config.ServicesConfig;

public final class ServiceContext<M extends Manager, T extends Org> {

    private M manager;

    private User<T> user;

    public ServiceContext(M manager, User<T> user) {
        this.manager = manager;
        this.user = user;
    }

    public M getManager() {
        return manager;
    }

    public User<T> getUser() {
        return user;
    }

    public T getOrg() {
        return user.getOrg();
    }

    public ServicesConfig getServicesConfig() {
        return getOrg().getConfig().getServicesConfig();
    }

    public ServiceConfig getServiceConfig(String serviceEntryName) {
        ServiceConfig serviceConfig = getServicesConfig().getServiceConfigMap().get(serviceEntryName);
        return serviceConfig;
    }
}
