package com.github.nijian.jkeel.algorithms;

/**
 * AlgorithmFactory is responsible for getting Algorithm instance.
 *
 * @author nj
 * @since 0.0.1
 */
public interface AlgorithmFactory {

    /**
     * Get Algorithm instance
     *
     * @param algorithmName algorithm name
     * @return algorithm instance
     */
    Algorithm getAlgorithm(String algorithmName);
}
