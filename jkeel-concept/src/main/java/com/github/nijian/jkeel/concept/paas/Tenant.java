package com.github.nijian.jkeel.concept.paas;

import com.github.nijian.jkeel.concept.UserGroup;
import com.github.nijian.jkeel.concept.config.ServicesConfig;

public class Tenant extends UserGroup {

    @Override
    public ServicesConfig getServicesConfig() {
        return null;
    }
}
