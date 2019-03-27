package com.github.nijian.jkeel.algorithms;

import java.io.Serializable;

/**
 * Created by Johnson.Ni
 */
public class AlgorithmContext<T extends AlgorithmTemplate, C extends AlgorithmConfig> implements Serializable {

    private String cid;

    private T template;

    private C config;

    public AlgorithmContext(String cid, T template, C config) {
        this.cid = cid;
        this.template = template;
        this.config = config;
    }

    public String getCid() {
        return cid;
    }

    public T getTemplate() {
        return template;
    }

    public C getConfig() {
        return config;
    }
}
