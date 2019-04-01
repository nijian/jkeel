package com.github.nijian.jkeel.algorithms.serration.debug;

import com.github.nijian.jkeel.algorithms.debug.Output;
import com.github.nijian.jkeel.algorithms.debug.OutputFactory;

/**
 * Serration algorithm debug output factory
 *
 * @author nj
 * @since 0.0.1
 */
public class SerrationOutputFactory implements OutputFactory {

    /**
     * Get debug output
     *
     * @param name output name
     * @return debug output
     */
    @Override
    public Output getOutput(String name) {
        if (name.equals(CSVOutput.class.getName())) {
            return new CSVOutput();
        }
        return null;
    }
}
