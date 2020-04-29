package com.github.nijian.jkeel.concept.spi;

import com.github.nijian.jkeel.concept.Service;

public interface ServiceFactory {

    Service<?> getService(String serviceName);

}
