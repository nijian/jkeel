package com.github.nijian.jkeel.config.cfg4j;

import com.github.nijian.jkeel.concept.Config;
import com.github.nijian.jkeel.concept.spi.ConfigFactory;

public class Cfg4jConfigFactory implements ConfigFactory {

    private Cfg4jConfig config = Cfg4jConfig.getInstance();

    @Override
    public Config getConfig(String name) {

//        if (name.equals(config.getId())) {
//            return config;
//        }
        return null;
    }
}
