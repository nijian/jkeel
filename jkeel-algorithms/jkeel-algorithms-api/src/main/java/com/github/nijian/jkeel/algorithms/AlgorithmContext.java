package com.github.nijian.jkeel.algorithms;

import java.io.Serializable;

/**
 * Created by Johnson.Ni
 */
public class AlgorithmContext<C extends AlgorithmConfig> implements Serializable {

    private String cid;

    private C config;

    public AlgorithmContext(String cid, C config) {
        this.cid = cid;
        this.config = config;
    }

    public String getCid() {
        return cid;
    }

    public C getConfig() {
        return config;
    }
}
