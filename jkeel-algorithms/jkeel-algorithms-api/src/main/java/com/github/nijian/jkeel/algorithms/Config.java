package com.github.nijian.jkeel.algorithms;

import javax.cache.Cache;
import java.io.Serializable;

/**
 * Created by Johnson.Ni
 */
public abstract class Config implements Serializable {

    private String configId;

    //get default cache
    <K, V> Cache<K, V> getCache();

    <K, V> Cache<K, V> getCache(String name, Class<K> keyClz, Class<V> valueClz);

    public String getConfigId() {
        return configId;
    }

    public void setConfigId(String configId) {
        this.configId = configId;
    }
}
