package com.github.nijian.jkeel.spring.factory;

import com.github.nijian.jkeel.concept.DataAccessor;
import com.github.nijian.jkeel.concept.spi.DataAccessorFactory;
import com.github.nijian.jkeel.spring.data.jpa.JPAPersist;
import com.github.nijian.jkeel.spring.data.jpa.JPAQueryForObject;
import com.github.nijian.jkeel.spring.data.sql.SpringDataQueryForList;

public class SpringDataAccessorFactory implements DataAccessorFactory {

    @Override
    public DataAccessor getDataAccessor(String dataName) {

        if (dataName.equals("JPA_PERSIST")) {
            return new JPAPersist();
        } else if (dataName.equals("SPRING_DATA_QUERY_LIST")) {
            return new SpringDataQueryForList();
        } else if (dataName.equals("JPA_LOAD")) {
            return new JPAQueryForObject();
        }

        return null;
    }
}
