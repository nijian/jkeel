package com.github.nijian.jkeel.concept.spi;

import com.github.nijian.jkeel.concept.DataAccessor;

public interface DataFactory {

    DataAccessor getData(String daoName);

}
