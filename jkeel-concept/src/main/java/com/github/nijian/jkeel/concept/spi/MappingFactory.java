package com.github.nijian.jkeel.concept.spi;

import com.github.nijian.jkeel.concept.Mapping;

public interface MappingFactory {

    Mapping getMapping(String name);

}
