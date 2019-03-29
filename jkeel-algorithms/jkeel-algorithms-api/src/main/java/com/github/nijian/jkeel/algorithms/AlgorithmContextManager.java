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

    public <C extends AlgorithmConfig> AlgorithmContext createContext(String cid, String aConfigUri, Class<C> clz, Properties env) {

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
                    context = new AlgorithmContext(cid, aConfig);
                    contexts.put(cid, context);
                }
            }
        }
        return context;
    }

    public <C extends AlgorithmConfig> TemplateAlgorithmContext createTemplateContext(String cid, AlgorithmTemplate aTemplate, String aConfigUri, Class<C> clz, Properties env) {

        TemplateAlgorithmContext context = (TemplateAlgorithmContext) contexts.get(cid);
        if (context == null) {
            synchronized (contexts) {
                context = (TemplateAlgorithmContext) contexts.get(cid);
                if (context == null) {
                    AlgorithmConfig aConfig;
                    try {
                        aConfig = clz.newInstance();
                        aConfig.init(cid, aConfigUri, env);
                    } catch (Exception e) {
                        throw new RuntimeException("Algorithm config can not be initialized", e);
                    }
                    context = new TemplateAlgorithmContext(cid, aTemplate, aConfig);
                    contexts.put(cid, context);
                }
            }
        }
        return context;
    }
}
