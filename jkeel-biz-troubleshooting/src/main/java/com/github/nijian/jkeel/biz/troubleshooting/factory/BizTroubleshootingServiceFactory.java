package com.github.nijian.jkeel.biz.troubleshooting.factory;

import com.github.nijian.jkeel.concept.Manager;
import com.github.nijian.jkeel.concept.Service;
import com.github.nijian.jkeel.concept.spi.ServiceFactory;

public class BizTroubleshootingServiceFactory implements ServiceFactory {

    @Override
    public Service<? extends Manager, ?, ?> getService(String serviceName) {
        return null;
    }
}