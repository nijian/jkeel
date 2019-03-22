package com.github.nijian.jkeel.algorithms;

import javax.cache.CacheManager;
import java.io.Serializable;

/**
 * Created by Johnson.Ni
 */
public class AlgorithmContext implements Serializable {

    private Template template;

    private Config config;

    private CacheManager cacheManager;

    public AlgorithmContext(Template template, Config config, CacheManager cacheManager) {
        this.template = template;
        this.config = config;
        this.cacheManager = cacheManager;
    }

    public Template getTemplate() {
        return template;
    }

    public void setTemplate(Template template) {
        this.template = template;
    }

    public Config getConfig() {
        return config;
    }

    public void setConfig(Config config) {
        this.config = config;
    }

    public CacheManager getCacheManager() {
        return cacheManager;
    }

    public void setCacheManager(CacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }
}
