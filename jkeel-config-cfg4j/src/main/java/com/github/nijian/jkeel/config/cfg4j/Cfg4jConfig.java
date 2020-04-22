package com.github.nijian.jkeel.config.cfg4j;

import com.github.nijian.jkeel.concept.Config;

public class Cfg4jConfig extends Config {

    /**
     * singleton instance
     */
    private static Cfg4jConfig instance;

    /**
     * private construction
     */
    private Cfg4jConfig() {
    }

    /**
     * Get instance
     *
     * @return instance
     */
    public static Cfg4jConfig getInstance() {
        if (instance == null) {
            synchronized (Cfg4jConfig.class) {
                if (instance == null) {
                    instance = new Cfg4jConfig();
                }
            }
        }
        return instance;
    }

    @Override
    public String getId() {
        return "ACC2";
    }

    @Override
    public String get(String term) {
        return null;
    }
}
