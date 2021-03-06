package com.github.nijian.jkeel.concept.spi;

import com.github.nijian.jkeel.concept.ConfigBuilder;

import java.util.ServiceLoader;

public class ConfigBuilderFactoryProvider {

    private static ConfigBuilderFactoryProvider provider;

    private ServiceLoader<ConfigBuilderFactory> loader;

    private ConfigBuilderFactoryProvider() {
        loader = ServiceLoader.load(ConfigBuilderFactory.class);
    }

    public static ConfigBuilderFactoryProvider getInstance() {
        if (provider != null) {
            return provider;
        }

        synchronized (ConfigBuilderFactoryProvider.class) {
            if (provider == null) {
                provider = new ConfigBuilderFactoryProvider();
            }
            return provider;
        }
    }

    public ConfigBuilder getConfigBuilder(String name) {

//        ServiceContext ctx = ServiceContext.getInstance();
//        ConfigBuilder configBuilder = ctx.getConfigBuilder(name);
//
//        if (configBuilder == null) {
//            Iterator<ConfigBuilderFactory> factories = loader.iterator();
//            while (configBuilder == null && factories.hasNext()) {
//                ConfigBuilderFactory factory = factories.next();
//                configBuilder = factory.getConfigBuilder(name);
//            }
//
//            if (configBuilder == null) {
//                throw new RuntimeException("config builder Not Found");
//            }
//
//            //register config builder
//            ServiceContext.getInstance().putConfigBuilder(name, configBuilder);
//        }

        return null;
    }
}
