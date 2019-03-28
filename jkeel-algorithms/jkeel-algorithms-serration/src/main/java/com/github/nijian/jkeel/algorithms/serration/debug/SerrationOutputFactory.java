package com.github.nijian.jkeel.algorithms.serration.debug;

import com.github.nijian.jkeel.algorithms.debug.Output;
import com.github.nijian.jkeel.algorithms.debug.OutputFactory;

public class SerrationOutputFactory implements OutputFactory {

    @Override
    public Output getOutput(String name) {
        if (name.equals(CSVOutput.class.getName())) {
            return new CSVOutput();
        }
        return null;
    }
}
