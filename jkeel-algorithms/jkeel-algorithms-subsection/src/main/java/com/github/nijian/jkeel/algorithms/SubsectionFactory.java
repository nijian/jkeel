package com.github.nijian.jkeel.algorithms;

/**
 * SubsectionFactory is a AlgorithmFactory implementation used to get Subsection Algorithm.
 *
 * @author nj
 * @since 0.0.6
 */
public final class SubsectionFactory implements AlgorithmFactory {

    /**
     * Get subsection algorithm
     *
     * @param algorithmName algorithm name
     * @return subsection algorithm
     */
    @Override
    public Algorithm getAlgorithm(String algorithmName) {
        if (algorithmName.equals(Subsection.class.getName())) {
            return Subsection.getInstance();
        }
        return null;
    }
}
