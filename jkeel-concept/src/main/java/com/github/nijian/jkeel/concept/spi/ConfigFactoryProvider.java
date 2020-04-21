package com.github.nijian.jkeel.concept.spi;

import com.github.nijian.jkeel.concept.Config;

import java.util.Iterator;
import java.util.ServiceLoader;

public class ConfigFactoryProvider {

    private static ConfigFactoryProvider provider;

    private ServiceLoader<ConfigFactory> loader;

    private ConfigFactoryProvider() {
        loader = ServiceLoader.load(ConfigFactory.class);
    }

    public static ConfigFactoryProvider getInstance() {
        if (provider != null) {
            return provider;
        }

        synchronized (ConfigFactoryProvider.class) {
            if (provider == null) {
                provider = new ConfigFactoryProvider();
            }
            return provider;
        }
    }

    public Config getConfig(String name) {

        Config config = null;
        Iterator<ConfigFactory> factories = loader.iterator();
        while (config == null && factories.hasNext()) {
            ConfigFactory factory = factories.next();
            config = factory.getConfig(name);
        }

        if (config == null) {
            throw new RuntimeException("config Not Found");
        }
        return config;
    }
}
