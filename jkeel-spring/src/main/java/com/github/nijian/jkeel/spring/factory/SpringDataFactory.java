package com.github.nijian.jkeel.spring.factory;

import com.github.nijian.jkeel.concept.Data;
import com.github.nijian.jkeel.concept.spi.DataFactory;
import com.github.nijian.jkeel.spring.data.sql.SpringDataCount;
import com.github.nijian.jkeel.spring.data.sql.SpringDataQueryForList;

public class SpringDataFactory implements DataFactory {

    @Override
    public Data getData(String dataName) {

        if (dataName.equals("SPRING_DATA_COUNT")) {
            return new SpringDataCount();
        } else if (dataName.equals("SPRING_DATA_QUERY_LIST")) {
            return new SpringDataQueryForList();
        }

        return null;
    }
}
