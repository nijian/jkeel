package com.github.nijian.jkeel.biz.troubleshooting;

import com.github.nijian.jkeel.concept.Bootstrap;
import com.github.nijian.jkeel.concept.Config;
import com.github.nijian.jkeel.concept.ConfigBuilder;
import com.github.nijian.jkeel.concept.spi.ConfigFactoryProvider;

public class BizTroubleshootingConfigBuilder implements ConfigBuilder {


    public BizTroubleshootingConfigBuilder() {

    }

    @Override
    public Config init() {

        String configImpl = Bootstrap.getInstance().getImpl();

        return ConfigFactoryProvider.getInstance().getConfig(configImpl);
    }

}
