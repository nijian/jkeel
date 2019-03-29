package com.github.nijian.jkeel.algorithms.debug;

/**
 * OutputFactory is responsible for getting debug Output instance.
 *
 * @author nj
 * @since 0.0.1
 */
public interface OutputFactory {

    /**
     * Get debug Output instance
     *
     * @param outputName debug output name
     * @return debug output instance
     */
    Output getOutput(String outputName);
}
