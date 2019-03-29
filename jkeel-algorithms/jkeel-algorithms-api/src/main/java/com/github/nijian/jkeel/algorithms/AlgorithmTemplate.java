package com.github.nijian.jkeel.algorithms;

import java.io.Serializable;

/**
 * AlgorithmTemplate
 *
 * @author nj
 * @since 0.0.1
 */
public interface AlgorithmTemplate extends Serializable {

    /**
     * Get algorithm context global identifier.
     *
     * @return algorithm context global identifier
     */
    String getCid();

}
