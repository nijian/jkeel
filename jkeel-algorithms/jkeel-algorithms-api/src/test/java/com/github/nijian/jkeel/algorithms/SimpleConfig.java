package com.github.nijian.jkeel.algorithms;

import java.net.URI;
import java.util.Properties;

public class SimpleConfig implements AlgorithmConfig {
    @Override
    public void init(String cid, URI configUri, Class<?> clz, Properties env) {

    }

    @Override
    public String getCid() {
        return null;
    }

    @Override
    public Properties getEnv() {
        return null;
    }

    @Override
    public Object getDelegate() {
        return null;
    }
}
