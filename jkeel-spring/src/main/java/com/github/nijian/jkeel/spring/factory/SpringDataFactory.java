package com.github.nijian.jkeel.spring.factory;

import com.github.nijian.jkeel.concept.DataAccessor;
import com.github.nijian.jkeel.concept.spi.DataFactory;
import com.github.nijian.jkeel.spring.data.sql.SpringDataCount;
import com.github.nijian.jkeel.spring.data.sql.SpringDataQueryForList;

public class SpringDataFactory implements DataFactory {

    @Override
    public DataAccessor getData(String dataName) {

        if (dataName.equals("SPRING_DATA_COUNT")) {
            return null;//new SpringDataCount();
        } else if (dataName.equals("SPRING_DATA_QUERY_LIST")) {
            return new SpringDataQueryForList();
        }

        return null;
    }
}
