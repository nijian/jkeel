package com.github.nijian.jkeel.algorithms;

import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public final class AlgorithmContextManager {

    private static AlgorithmContextManager instance;

    //TODO change to ehcache
    private final ConcurrentMap<String, AlgorithmContext> contexts = new ConcurrentHashMap();

    private AlgorithmContextManager() {
    }

    public static AlgorithmContextManager getInstance() {
        if (instance == null) {
            synchronized (AlgorithmContextManager.class) {
                if (instance == null) {
                    instance = new AlgorithmContextManager();
                }
            }
        }
        return instance;
    }

    public <T extends AlgorithmConfig> AlgorithmContext createContext(String cid, AlgorithmTemplate aTemplate, String aConfigUri, Class<T> clz, Properties env) {

        AlgorithmContext context = contexts.get(cid);
        if (context == null) {
            synchronized (contexts) {
                context = contexts.get(cid);
                if (context == null) {
                    AlgorithmConfig aConfig;
                    try {
                        aConfig = clz.newInstance();
                        aConfig.init(cid, aConfigUri, env);
                    } catch (Exception e) {
                        throw new RuntimeException("Algorithm config can not be initialized", e);
                    }
                    context = new AlgorithmContext(cid, aTemplate, aConfig);
                    contexts.put(cid, context);
                }
            }
        }
        return context;
    }
}
