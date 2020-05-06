package com.github.nijian.jkeel.spring.data.sql;

import com.github.nijian.jkeel.commons.query.data.QueryForList;
import com.github.nijian.jkeel.commons.query.entity.QueryResult;
import com.github.nijian.jkeel.concept.ServiceContext;
import com.github.nijian.jkeel.spring.SpringManager;

import java.util.List;

public class SpringDataQueryForList extends QueryForList {


    @Override
    protected QueryResult execute0(ServiceContext<?> ctx, QueryResult queryResult, String queryDsl, Object[] args) {

        SpringManager manager = (SpringManager) ctx.getManager();
        List<?> valueList = manager.getJdbcTemplate().queryForList("select count(1) from abc");
        queryResult.setValueList(valueList);

        return queryResult;
    }

    @Override
    protected QueryResult appendCount(ServiceContext<?> ctx, QueryResult queryResult, String queryDsl, Object[] args) {
        SpringManager manager = (SpringManager) ctx.getManager();
        Long count = manager.getJdbcTemplate().queryForObject("select count(1) from abc", Long.class);
        queryResult.setCount(count);
        return queryResult;
    }
}
