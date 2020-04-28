package com.github.nijian.jkeel.mapping.factory;

import com.github.nijian.jkeel.concept.Mapping;
import com.github.nijian.jkeel.concept.spi.MappingFactory;
import com.github.nijian.jkeel.mapping.JkeelMapping;

public class JkeelMappingFactory implements MappingFactory {

    @Override
    public Mapping getMapping(String name) {

        //build by mapping engine
        return new JkeelMapping();
    }
}
