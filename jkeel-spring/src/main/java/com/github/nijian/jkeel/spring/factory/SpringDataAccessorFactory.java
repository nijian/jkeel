package com.github.nijian.jkeel.spring.factory;

import com.github.nijian.jkeel.concept.DataAccessor;
import com.github.nijian.jkeel.concept.spi.DataAccessorFactory;
import com.github.nijian.jkeel.spring.data.sql.SpringDataQueryForList;

public class SpringDataAccessorFactory implements DataAccessorFactory {

    @Override
    public DataAccessor getDataAccessor(String dataName) {

        if (dataName.equals("SPRING_DATA_COUNT")) {
            return null;//new SpringDataCount();
        } else if (dataName.equals("SPRING_DATA_QUERY_LIST")) {
            return new SpringDataQueryForList();
        }

        return null;
    }
}
