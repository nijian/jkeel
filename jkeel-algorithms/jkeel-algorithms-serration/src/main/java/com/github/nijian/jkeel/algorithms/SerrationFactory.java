package com.github.nijian.jkeel.algorithms;

/**
 * SerrationFactory is a AlgorithmFactory implementation used to get Serration Algorithm.
 *
 * @author nj
 * @since 0.0.1
 */
public final class SerrationFactory implements AlgorithmFactory {

    /**
     * Get serration algorithm
     *
     * @param algorithmName algorithm name
     * @return serration algorithm
     */
    @Override
    public Algorithm getAlgorithm(String algorithmName) {
        if (algorithmName.equals(Serration.class.getName())) {
            return Serration.getInstance();
        }
        return null;
    }
}
