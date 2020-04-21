package com.github.nijian.jkeel.concept.spi;

import com.github.nijian.jkeel.concept.ConfigBuilder;
import com.github.nijian.jkeel.concept.NamespaceAware;

public interface ConfigBuilderFactory extends NamespaceAware {

    ConfigBuilder getConfigBuilder(String name);
}
