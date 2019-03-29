package com.github.nijian.jkeel.algorithms;

import java.io.Serializable;

/**
 * AlgorithmContext stands for a concrete calculation implementation. It's better to have an AlgorithmContext
 * repository concept.
 *
 * @param <C> algorithm config type
 * @author nj
 * @since 0.0.1
 */
public class AlgorithmContext<C extends AlgorithmConfig> implements Serializable {

    /**
     * algorithm context global identifier
     */
    private String cid;

    /**
     * algorithm config
     */
    private C config;

    /**
     * Construction
     *
     * @param cid    algorithm context global identifier
     * @param config algorithm config
     */
    public AlgorithmContext(String cid, C config) {
        this.cid = cid;
        this.config = config;
    }

    /**
     * Get algorithm context global identifier.
     *
     * @return algorithm context global identifier
     */
    public String getCid() {
        return cid;
    }

    /**
     * Get algorithm config.
     *
     * @return algorithm config
     */
    public C getConfig() {
        return config;
    }
}
