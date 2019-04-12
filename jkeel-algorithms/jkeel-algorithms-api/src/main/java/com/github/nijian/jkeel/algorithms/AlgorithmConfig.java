package com.github.nijian.jkeel.algorithms;

import java.io.Serializable;
import java.net.URI;
import java.util.Properties;

/**
 * Algorithm is always based on algorithm context to calculate values. AlgorithmConfig is an important part of the
 * context. The config usually includes raw input converter, formulas, etc.
 *
 * @author nj
 * @since 0.0.1
 */
public interface AlgorithmConfig extends Serializable {

    /**
     * Initialize the algorithm config.
     *
     * @param cid               algorithm context global identifier
     * @param configUri         uri of the algorithm config resource
     * @param delegateConfigClz delegate config class
     * @param env               environment variables
     */
    void init(String cid, URI configUri, Class<?> delegateConfigClz, Properties env);

    /**
     * Get algorithm context global identifier.
     *
     * @return algorithm context global identifier
     */
    String getCid();

    /**
     * Get environment variables
     *
     * @return environment variables
     */
    Properties getEnv();

    /**
     * Get config delegate
     *
     * @return delegate object
     */
    Object getDelegate();

}
