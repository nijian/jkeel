package com.github.nijian.jkeel.algorithms;

import javax.cache.Cache;
import javax.cache.CacheManager;
import javax.cache.configuration.MutableConfiguration;
import java.io.Serializable;

/**
 * Created by Johnson.Ni
 */
public class AlgorithmContext<T extends Template, C extends Config, V> implements Serializable {

    private String cid;

    private T template;

    private C config;

    private Cache<String, V> cache;

    public AlgorithmContext(String cid, T template, C config, CacheManager cacheManager) {
        this.cid = cid;
        this.template = template;
        this.config = config;

        String templateCid = template.getCid();
        if (templateCid != null && !cid.equals(templateCid)) {
            throw new RuntimeException("Template cid is not equal to required cid");
        }

        cache = cacheManager.getCache(cid);
        if (cache == null) {
            MutableConfiguration<String, V> configuration = new MutableConfiguration<String, V>().setStoreByValue(false);
            cache = cacheManager.createCache(cid, configuration);
        }

        config.init(cache);
    }

    public String getCid() {
        return cid;
    }

    public T getTemplate() {
        return template;
    }

    public Cache<String, V> getCache() {
        return cache;
    }

}
