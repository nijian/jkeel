package com.github.nijian.jkeel.algorithms;

import java.io.Serializable;

/**
 * Created by Johnson.Ni
 */
public class AlgorithmContext implements Serializable {

    private Layout layout;

    private Config config;

    public Layout getLayout() {
        return layout;
    }

    public void setLayout(Layout layout) {
        this.layout = layout;
    }

    public Config getConfig() {
        return config;
    }

    public void setConfig(Config config) {
        this.config = config;
    }
}
