package com.github.nijian.jkeel.biz.troubleshooting.factory;

import com.github.nijian.jkeel.biz.troubleshooting.BizTroubleshootingConfigBuilder;
import com.github.nijian.jkeel.concept.ConfigBuilder;
import com.github.nijian.jkeel.concept.spi.ConfigBuilderFactory;

public class BizTroubleshootingConfigBuilderFactory implements ConfigBuilderFactory {

    @Override
    public ConfigBuilder getConfigBuilder(String name) {

        if (name.equals(ns())) {
            return new BizTroubleshootingConfigBuilder();
        }
        return null;
    }
}
