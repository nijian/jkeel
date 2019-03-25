package com.github.nijian.jkeel.algorithms;

import javax.cache.Cache;
import java.io.Serializable;

/**
 * Created by Johnson.Ni
 */
public class AlgorithmContext<T> implements Serializable {

    private Template template;

    private Cache<String, T> cache;

    public AlgorithmContext(Template template, Cache<String, T> cache) {
        this.template = template;
        this.cache = cache;
    }

    public Template getTemplate() {
        return template;
    }

    public void setTemplate(Template template) {
        this.template = template;
    }

    public Cache<String, T> getCache() {
        return cache;
    }

    public void setCache(Cache<String, T> cache) {
        this.cache = cache;
    }
}
