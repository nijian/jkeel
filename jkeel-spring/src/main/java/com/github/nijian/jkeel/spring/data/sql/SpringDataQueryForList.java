package com.github.nijian.jkeel.spring.data.sql;

import com.github.nijian.jkeel.concept.ServiceContext;
import com.github.nijian.jkeel.concept.query.data.QueryForList;
import com.github.nijian.jkeel.concept.query.entity.QueryResult;
import com.github.nijian.jkeel.spring.SpringManager;

import java.util.List;

public class SpringDataQueryForList extends QueryForList {

    @Override
    protected QueryResult execute(ServiceContext<?> serviceContext, QueryResult queryResult,
                                  String queryDsl, Object[] args) {

        SpringManager manager = (SpringManager) serviceContext.getManager();
        List<?> valueList = manager.getJdbcTemplate().queryForList("select count(1) from abc");
        queryResult.setValueList(valueList);

        return queryResult;
    }

    @Override
    protected QueryResult appendCount(ServiceContext<?> serviceContext, QueryResult queryResult,
                                      String queryDsl, Object[] args) {
        SpringManager manager = (SpringManager) serviceContext.getManager();
        Long count = manager.getJdbcTemplate().queryForObject("select count(1) from abc", Long.class);
        queryResult.setCount(count);
        return queryResult;
    }
}
