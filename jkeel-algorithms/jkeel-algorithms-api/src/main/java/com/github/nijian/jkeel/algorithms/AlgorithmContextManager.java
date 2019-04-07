package com.github.nijian.jkeel.algorithms;

import java.net.URI;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * AlgorithmContextManager is responsible for creating AlgorithmContext with global identifier.
 *
 * @author nj
 * @since 0.0.1
 */
public final class AlgorithmContextManager {

    /**
     * Singleton instance
     */
    private static AlgorithmContextManager instance;

    /**
     * AlgorithmContext cache, will be change to repository later.
     */
    private final ConcurrentMap<String, AlgorithmContext> contexts = new ConcurrentHashMap();

    /**
     * private construction
     */
    private AlgorithmContextManager() {
    }

    /**
     * Get algorithm context manager singleton instance
     *
     * @return AlgorithmContextManager instance
     */
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

    /**
     * Create AlgorithmContext with global identifier
     *
     * @param cid       algorithm context global identifier
     * @param configUri uri of the algorithm config resource
     * @param clz       class instance of algorithm config
     * @param env       environment variables
     * @param <C>       class type of algorithm config
     * @return algorithm context
     */
    public <C extends AlgorithmConfig> AlgorithmContext createContext(String cid, URI configUri, Class<C> clz, Properties env) {

        AlgorithmContext context = contexts.get(cid);
        if (context == null) {
            synchronized (contexts) {
                context = contexts.get(cid);
                if (context == null) {
                    AlgorithmConfig aConfig;
                    try {
                        aConfig = clz.newInstance();
                        aConfig.init(cid, configUri, env);
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

    /**
     * Create TemplateAlgorithmContext with global identifier
     *
     * @param cid       algorithm context global identifier
     * @param template  algorithm template
     * @param configUri uri of the algorithm config resource
     * @param clz       class instance of algorithm config
     * @param env       environment variables
     * @param <C>       class type of algorithm config
     * @return template algorithm context
     */
    public <C extends AlgorithmConfig> TemplateAlgorithmContext createTemplateContext(final String cid,
                                                                                      final AlgorithmTemplate template,
                                                                                      final URI configUri,
                                                                                      final Class<C> clz,
                                                                                      final Properties env) {

        TemplateAlgorithmContext context = (TemplateAlgorithmContext) contexts.get(cid);
        if (context == null) {
            synchronized (contexts) {
                context = (TemplateAlgorithmContext) contexts.get(cid);
                if (context == null) {
                    AlgorithmConfig aConfig;
                    try {
                        aConfig = clz.getConstructor().newInstance();
                        aConfig.init(cid, configUri, env);
                    } catch (Exception e) {
                        throw new RuntimeException("Algorithm config can not be initialized", e);
                    }
                    context = new TemplateAlgorithmContext(cid, template, aConfig);
                    contexts.put(cid, context);
                }
            }
        }
        return context;
    }
}
