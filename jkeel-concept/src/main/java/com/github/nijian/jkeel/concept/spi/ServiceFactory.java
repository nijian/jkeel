package com.github.nijian.jkeel.concept.spi;

import com.github.nijian.jkeel.concept.Manager;
import com.github.nijian.jkeel.concept.Service;

public interface ServiceFactory {

    Service<? extends Manager, ?, ?> getService(String serviceName);

}
