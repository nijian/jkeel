package com.github.nijian.jkeel.concept.spi;

import com.github.nijian.jkeel.concept.Config;

public interface ConfigFactory {

    Config getConfig(String configName);

}
