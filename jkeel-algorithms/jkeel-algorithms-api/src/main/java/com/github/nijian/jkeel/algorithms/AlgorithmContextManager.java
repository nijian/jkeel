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
    private final static ConcurrentMap<String, AlgorithmContext> contexts = new ConcurrentHashMap();

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
                        aConfig.init(cid, configUri, null, env);
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
        return createTemplateContext(cid, template, configUri, clz, null, env);
    }

    /**
     * Create TemplateAlgorithmContext with global identifier
     *
     * @param cid         algorithm context global identifier
     * @param template    algorithm template
     * @param configUri   uri of the algorithm config resource
     * @param clz         class instance of algorithm config
     * @param delegateClz class instance of delegate config
     * @param env         environment variables
     * @param <C>         class type of algorithm config
     * @param <T>         class type of delegate config
     * @return template algorithm context
     * @since 0.0.2
     */
    public <C extends AlgorithmConfig, T> TemplateAlgorithmContext createTemplateContext(final String cid,
                                                                                         final AlgorithmTemplate template,
                                                                                         final URI configUri,
                                                                                         final Class<C> clz,
                                                                                         final Class<T> delegateClz,
                                                                                         final Properties env) {

        TemplateAlgorithmContext context = (TemplateAlgorithmContext) contexts.get(cid);
        if (context == null) {
            synchronized (contexts) {
                context = (TemplateAlgorithmContext) contexts.get(cid);
                if (context == null) {
                    AlgorithmConfig aConfig;
                    try {
                        aConfig = clz.getConstructor().newInstance();
                        aConfig.init(cid, configUri, delegateClz, env);
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

    /**
     * Contains the algorithm context or not?
     *
     * @param cid algorithm context global identifier
     * @return true or false
     * @since 0.0.2
     */
    public boolean contains(String cid) {
        return contexts.containsKey(cid);
    }

    /**
     * Get algorithm context
     *
     * @param cid algorithm context global identifier
     * @return AlgorithmContext
     * @since 0.0.3
     */
    public AlgorithmContext getAlgorithmContext(String cid) {
        return contexts.get(cid);
    }
}
